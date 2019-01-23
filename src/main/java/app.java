import com.beust.jcommander.Parameters;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.RandomStringUtils;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Repository;
import specs.BambooNuanceSpec;
import specs.BambooSpecConfiguration;
import specs.NetVersion;
import specs.ProjectSpec;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.function.Consumer;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import java.util.ArrayList;
import java.util.List;

class InitCommand {
    @Parameter
    public List<String> parameters = new ArrayList<String>();
    @Parameter(names = { "-projectDirectory" }, description = "Inicia o gerador")
    public String projectDirectory;
    @Parameter(names = { "-target" }, description = "Versão do .Net (Core ou 4x")
    public String version;
    @Parameter(names = { "-name" }, description = "Nome do projeto")
    public String name;
    @Parameter(names = { "-repository" }, description = "Nome do repositorio")
    public String repository;
}
@Parameters(commandDescription = "Publish plan to BambooServer")
class PublishCommand {
    @Parameter(names = { "-h" }, description = "ajuda")
    public String help;
}
public class app {


    public  static void main(String... args) throws Exception {
        args = new String[]{"publish"};

        BambooNuanceSpec nuanceSpec = new BambooNuanceSpec();

        System.out.println("Bamboo Spec Generator  started.");

        InitCommand initCommand = new InitCommand();
        JCommander jc = JCommander.newBuilder()
                .addObject(initCommand)
                .addCommand("init",initCommand)
                .addCommand("publish", new PublishCommand())
                .programName("bamboospec")
                .build();
        jc.parse(args);
        String parsedCommand = jc.getParsedCommand() ;
        System.out.println("Command " + parsedCommand);
        switch (parsedCommand){
            case "init":
                init(initCommand);
                break;
            case "publish":
                publish();
                break;
        }
    }
    public static  void publish() throws Exception {
        BambooNuanceSpec.ConfigureBambooPlan();
    }
    public static  void init(InitCommand command) throws Exception {
        BambooNuanceSpec nuanceSpec = new BambooNuanceSpec();
        System.out.printf("init command args %s%n", nuanceSpec.SerializeObject(command));

        String planName = createPlan(command.name);
        System.out.printf("Plan name %s%n",planName);
        String projKey = createKey(command.name);
        String planKey = createKey(planName);
        ProjectSpec spec = BambooSpecConfiguration.DefaultConfiguratioin(command.name,projKey,planName,command.projectDirectory,
                planKey,"",createJob(command.name),NetVersion.valueOf(command.version));

        RepositoryHelper gitHelper = new  RepositoryHelper();

        if(gitHelper.exists()){
            String url = gitHelper.get_repositoryUrl();
            System.out.printf("found git repository url %s%n", url);
            System.out.printf("found git repository name %s%n", gitHelper.getRepositoryName());
            System.out.printf("Setting repository configurations on project spec");
            spec.RepositoryUrl = gitHelper.get_repositoryUrl();
            spec.Repository = gitHelper.getRepositoryName();
        }
        else
        {
            System.out.printf("Git Repository values not found. Run 'bspec config repo --name $repositoryName ' to set this values");
        }
        String json = nuanceSpec.SerializeObject(spec);
        System.out.printf("Bamboo Spec generated with success. Run 'bspec publish' to sync with Bamboo Server  %s%n",json);
        System.out.printf("project spec %s%n",json);

        FileWriter config = new FileWriter("config.spec");
        config.write(json);
        config.flush();
        config.close();

    }
    private static int askUserForNumberInput(Scanner scanner, String prompt, int maxValue) {
        System.out.println("Nuance Bamboo Specs");
        System.out.println(prompt);
        int value = scanner.nextInt();
        while (value < 1 || value > maxValue) {
            System.out.println("invalid menu item, please try again");
            // java.util.InputMismatchException should also be caught
            // to intercept non-numeric input
            value = scanner.nextInt();
        }
        return value;
    }

    static void Menu() throws IOException {
        Clean();
        try (Scanner scanner = new Scanner(System.in)) {
            final int mainMenuSelection = askUserForNumberInput(scanner, "1-Criar Projeto de Build Default, 2- Criar Porjeto de Build Personalizado", 2);
            switch (mainMenuSelection) {
                case 1:
                    System.out.println("Informe o diretorio do Projeto");
                    String projectPath = scanner.next();
                    System.out.println("Informe a versão do .NET");
                    System.out.println("1 - Core");
                    System.out.println("2 - 4.x");
                    int nVersion = scanner.nextInt();
                    NetVersion version = nVersion == 1 ? NetVersion.Core : NetVersion.Framework;
                    System.out.println("Informe o nome do Projeto ");
                    String projectName = scanner.next();
                   // System.out.println("Informe o nome do Plano de Build ");
                    String planName = createPlan(projectName);
                    String projKey = createKey(projectName);
                    String planKey = createKey(planName);
                    ProjectSpec spec = BambooSpecConfiguration.DefaultConfiguratioin(projectName,projKey,planName,projectPath,
                            planKey,"",createJob(projectName),version);
                    BambooNuanceSpec nuanceSpec = new BambooNuanceSpec();
                    String json = nuanceSpec.SerializeObject(spec);
                    FileWriter config = new FileWriter("config.spec");
                    config.write(json);
                    config.flush();
                    config.close();
            }
        }
    }
    static  void Clean() throws IOException {
        Runtime.getRuntime().exec("cmd.exe cls");
    }
    static String createKey(String name){
        return String.format("KY%s%s",name.substring(0,4),RandomStringUtils.randomNumeric(3).toUpperCase()).toUpperCase();
    }
    static String createPlan(String name){
        return String.format("PL%s%s",name.substring(0,4),RandomStringUtils.randomNumeric(3).toUpperCase()).toUpperCase();
    }
    static String createJob(String name){
        return String.format("JB%3s%s",name.substring(0,4),RandomStringUtils.randomNumeric(3)).toUpperCase();
    }
}
class Menu {

    private final String action;
    private final Consumer<String> consumer;

    Menu(String action, Consumer<String> consumer) {
        this.action = action;
        this.consumer = consumer;
    }

    void execute() {
        this.consumer.accept(action);
    }
}