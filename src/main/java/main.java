import com.atlassian.bamboo.specs.api.builders.plan.Job;
import commands.*;
import org.apache.commons.lang3.RandomStringUtils;
import specs.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;
import com.beust.jcommander.JCommander;
import tasks.builders.NugetBuilder;

public class main {


    public  static void main(String... args) throws Exception {
         //args = new String[]{"init","-projectDirectory","andre", "-target","Framework"};
         //args = new String[]{"publish"};
        // args = new String[]{"config","repository","--repo-name","andre@git.com"};
        //args = new String[]{"task","Nuget","-p","src/ConsoleApp","-s","${bamboo.NUGET_TEST_SERVER}","-t","${bamboo.NUGET_API_TOKEN_PASSWORD}","-v","${bamboo.GitVersion.AssemblySemVer}"};
        try {
            BambooNuanceSpec nuanceSpec = new BambooNuanceSpec();


            System.out.println("Bamboo Spec Generator  started.");

            InitCommand initCommand = new InitCommand();

            PublishCommand publishCommand = new PublishCommand();
            MainCommand main = new MainCommand();
            ConfigCommand configCommand = new ConfigCommand();
            TaskCommand taskCommand = new TaskCommand();
            JCommander jc;

            JCommander.Builder builder = JCommander.newBuilder()
                    .addObject(main)
                    .addCommand(main)
                    .addCommand(initCommand)
                    .addCommand(configCommand)
                    .addCommand(taskCommand)
                    .addCommand(publishCommand);
                   // .build();
            jc = builder.build();
            //JCommander configSubCommands = jc.getCommands().get("config");
            //configSubCommands.addCommand("repository",configCommand.repositoryCommand);
            //jc.addCommand(configSubCommands);
           // builder.addCommand((configSubCommands));
            //jc = builder.build();
            jc.parse(args);
            String parsedCommand = jc.getParsedCommand();
            System.out.println("Command started " + parsedCommand);
            switch (parsedCommand) {
                case "init":
                    init(initCommand);
                    break;
                case "config":
                    config(configCommand);
                    break;
                case "publish":
                    publish();
                    break;
                case "task":
                    addTask(taskCommand);
                    break;
                case "-h":
                    jc.usage();
                    break;

            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public static  void addTask(TaskCommand taskCommand) throws Exception {
        switch (TaskType.valueOf(taskCommand.type)){

            case NetCore:
                break;
            case NetFramework:
                break;
            case ZIP:
                break;
            case Nuget:
                addNugetTask(taskCommand.nugetAddTaskCommand);
                break;
            case NexusDeploy:
                break;
            case GitVersion:
                break;
            case RepositoryCheckout:
                break;
        }
    }
    public static void addNugetTask(NugetAddTaskCommand command) throws IOException {
        ProjectSpec spec  = BambooNuanceSpec.getProjectConfig();
        Map<String,String> props = NugetBuilder.SetProperties(command.projectDirectory,command.version,command.server,command.token,command.output);
        TaskSpec nugetTask = new TaskSpec(TaskType.Nuget, props,4);
        StageSpec stage = spec.Plan.Stages.get(0);
        JobSpec jobSpec = stage.Jobs.get(0);
        jobSpec.addTask(nugetTask);
        WriteConfigFile(spec);
    }
    public static  void publish() throws Exception {
        BambooNuanceSpec.ConfigureBambooPlan();
    }
    public static  void config(ConfigCommand configCommand) throws IOException {
        switch (ConfigCommand.ConfigType.valueOf(configCommand.configurationType)){

            case repository:
                configRepository(configCommand.repositoryCommand);
                break;
            case task:
                break;
        }

    }
    public static  void configRepository(RepositoryCommand repositoryCommand) throws IOException {
        ProjectSpec spec  = BambooNuanceSpec.getProjectConfig();
        spec.Repository = repositoryCommand.name;
        spec.RepositoryUrl = repositoryCommand.url;
        String json = BambooNuanceSpec.SerializeObject(spec);
        WriteConfigFile(json);
        System.out.printf("Repository configuration values updated");
    }
    public static  void init(InitCommand command) throws Exception {
        BambooNuanceSpec nuanceSpec = new BambooNuanceSpec();
        System.out.printf("init command args %s%n", nuanceSpec.SerializeObject(command));

        String planName = createPlan(command.name);
        System.out.printf("Plan name %s%n",planName);
        String projKey = createKey(command.name);
        SpecKey key = new SpecKey(projKey);
        String planKey = createKey(planName);
        ProjectSpec spec = BambooSpecConfiguration.DefaultConfiguratioin(command.name,key.getKey(),key.getOID(),planName,command.projectDirectory,
                planKey,"",createJob(command.name),command.version);

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
        System.out.printf("Bamboo Spec generated with success. Run 'bspec publish' to sync with Bamboo Server  %s \n",json);
        //System.out.printf("project spec %s \n",json);

        WriteConfigFile(json);

    }
    private static void WriteConfigFile(String jsonContent) throws IOException {

        FileWriter config = new FileWriter("config.spec");
        System.out.printf("Writing config.spec file %s \n",jsonContent);
        config.write(jsonContent);
        config.flush();
        config.close();

    }
    private static void WriteConfigFile(ProjectSpec spec) throws IOException {

        String jsonContent = BambooNuanceSpec.SerializeObject(spec);
        FileWriter config = new FileWriter("config.spec");
        System.out.printf("Writing config.spec file %s \n",jsonContent);
        config.write(jsonContent);
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
