package specs;

import com.atlassian.bamboo.specs.api.BambooSpec;
import com.atlassian.bamboo.specs.api.builders.applink.ApplicationLink;
import com.atlassian.bamboo.specs.api.builders.plan.Job;
import com.atlassian.bamboo.specs.api.builders.plan.Plan;
import com.atlassian.bamboo.specs.api.builders.plan.PlanIdentifier;
import com.atlassian.bamboo.specs.api.builders.plan.Stage;
import com.atlassian.bamboo.specs.api.builders.plan.branches.BranchCleanup;
import com.atlassian.bamboo.specs.api.builders.plan.branches.PlanBranchManagement;
import com.atlassian.bamboo.specs.api.builders.project.Project;
import com.atlassian.bamboo.specs.api.builders.repository.VcsChangeDetection;
import com.atlassian.bamboo.specs.api.builders.repository.VcsRepository;
import com.atlassian.bamboo.specs.api.builders.task.AnyTask;
import com.atlassian.bamboo.specs.api.builders.task.Task;
import com.atlassian.bamboo.specs.api.builders.trigger.RepositoryBasedTrigger;
import com.atlassian.bamboo.specs.api.builders.trigger.Trigger;
import com.atlassian.bamboo.specs.api.exceptions.BambooSpecsPublishingException;
import com.atlassian.bamboo.specs.api.model.repository.VcsRepositoryProperties;
import com.atlassian.bamboo.specs.api.model.task.TaskProperties;
import com.atlassian.bamboo.specs.api.model.trigger.RepositoryBasedTriggerProperties;
import com.atlassian.bamboo.specs.builders.repository.bitbucket.server.BitbucketServerRepository;
import com.atlassian.bamboo.specs.builders.repository.git.GitRepository;
import com.atlassian.bamboo.specs.builders.repository.git.UserPasswordAuthentication;
import com.atlassian.bamboo.specs.builders.repository.viewer.BitbucketServerRepositoryViewer;
import com.atlassian.bamboo.specs.builders.task.CheckoutItem;
import com.atlassian.bamboo.specs.builders.task.VcsCheckoutTask;
import com.atlassian.bamboo.specs.builders.trigger.BitbucketServerTrigger;
import com.atlassian.bamboo.specs.builders.trigger.RepositoryPollingTrigger;
import com.atlassian.bamboo.specs.util.BambooServer;
import com.atlassian.bamboo.specs.api.builders.permission.Permissions;
import com.atlassian.bamboo.specs.api.builders.permission.PermissionType;
import com.atlassian.bamboo.specs.api.builders.permission.PlanPermissions;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import tasks.builders.*;

import javax.xml.bind.ValidationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
@BambooSpec
public class BambooNuanceSpec {


    public static  void main(final String[] args) throws  Exception{
        ConfigureBambooPlan();
    }
    public static void main2(final String[] args) throws Exception {
        //By default credentials are read from the '.credentials' file.
        try {
            ProjectSpec projectSpec = execute();


            BambooServer bambooServer = new BambooServer("http://10.0.4.175:8085");
            BambooNuanceSpec spec = new BambooNuanceSpec();
            String projectSpecJSON = spec.SerializeObject(projectSpec);
            FileWriter config = new FileWriter("config.json");
            config.write(projectSpecJSON);
            config.flush();
            config.close();


            SpecKey projectKey = new SpecKey("BmbKey12");
            Project project = spec.createPoject("BambooSpecs", projectKey);
            Plan plan = spec.createPlan(project, "buildPlan", new SpecKey("bdplan1"), "Test plan");
            Stage stage = spec.createStage("build tester");
            Job job = spec.createJob(plan, "Core Job", new SpecKey("jb1"));
            String repository = "bamboocispec", repositoryUrl = "https://github.com/andrepesi/bamboo_ci.git";
            plan.planRepositories(new GitRepository()
                    .name(repository)
                    .authentication(new UserPasswordAuthentication("andrepesi").password("!Aiolia1308"))
                    .url(repositoryUrl)
                    .branch("master"))
                    .planBranchManagement(new PlanBranchManagement()
                            .createForVcsBranchMatching("(release)/.+")
                            //.createForVcsBranch()
                            .delete(new BranchCleanup()
                                    .whenRemovedFromRepositoryAfterDays(30)));

            plan.triggers(new BitbucketServerTrigger());

           // job.tasks(new VcsCheckoutTask().cleanCheckout(false).checkoutItems(new CheckoutItem().defaultRepository()));
           // job.tasks(new GitVersionBuilder());
            Object task = new NetCoreBuilder();
            job.tasks(new NetCoreBuilder());
            stage.jobs(job);
            plan.stages(stage);
            plan.enabled(true);

           /* FileWriter config2 = new FileWriter("config2.json");

            String jsonConfigString = spec.SerializeObject(net);
            config2.write((jsonConfigString));
            config2.flush();config2.close();*/
            //bambooServer.publish(plan);
            PlanPermissions planPermission = new BambooNuanceSpec().createPlanPermission(plan.getIdentifier());
            // bambooServer.publish(planPermission);
        }
        catch (BambooSpecsPublishingException pex){
            throw pex;
        }
        catch (Exception ex){
            throw  ex;
        }
    }
    public static ProjectSpec execute() throws Exception {
        ProjectSpec projectSpec = new ProjectSpec("BambooSpecgenerator","bbspec1");
        PlanSpec planSpec = new PlanSpec("buildPlan","bdplan1","Test plan");
        StageSpec stageSpec = new StageSpec("Build");
        JobSpec job = new JobSpec("NuanceJob","jb02");
        TaskSpec net = new TaskSpec();
        net.TaskType = TaskType.NetFramework;
        net.Properties = new NetCoreBuilder().Properties;
        job.addTask(net);
        stageSpec.addJob(job);
        planSpec.addStage(stageSpec);
        projectSpec.addPlan(planSpec);
        return projectSpec;
    }
    public static void ConfigureBambooPlan() throws Exception {
        BambooServer bambooServer = new BambooServer(" http://10.0.4.175:8085");
        ProjectSpec projectConfig = getProjectConfig();
        BambooNuanceSpec spec = new BambooNuanceSpec();
        Project project = spec.createPoject(projectConfig.Name, new SpecKey(projectConfig.Key));
        Plan plan = spec.createPlan(project, projectConfig.Plan.Name,  new SpecKey(projectConfig.Plan.Key), projectConfig.Plan.Description);
        for (StageSpec stg : projectConfig.Plan.Stages){
            Stage stage = spec.createStage("build tester");
            for (JobSpec jb : stg.Jobs){
                Job job = spec.createJob(plan, jb.Name, new SpecKey(jb.Key));
                for (TaskSpec tsk : jb.Tasks){
                    createTask(tsk);
                    job.tasks();
                }
                stage.jobs(job);
            }
            plan.stages(stage);
        }

        plan.planRepositories(createRepository(projectConfig)).planBranchManagement((createPlanBranchManagement(projectConfig)));
        //plan.linkedRepositories(projectConfig.Repository).planRepositories(createRepository(projectConfig));
        plan.triggers(createTrigger());
        plan.enabled(true);
        bambooServer.publish(plan);
        PlanPermissions planPermission = new BambooNuanceSpec().createPlanPermission(plan.getIdentifier());
        bambooServer.publish(planPermission);

    }

    private static Trigger<?,?> createTrigger() {
        return new BitbucketServerTrigger();
        //return new RepositoryPollingTrigger().pollEvery(2,TimeUnit.MINUTES);
    }

    /*public static  BitbucketServerTrigger createTrigger(){
        return new BitbucketServerTrigger();
       // return new RepositoryPollingTrigger().pollEvery(2,TimeUnit.MINUTES);
    }*/
    public static Task<? extends Task<?,?>, ? extends TaskProperties> createTask(TaskSpec taskSpec){

        Task<? extends Task<?,?>, ? extends TaskProperties> task;
        switch (taskSpec.TaskType) {
            case NetCore:
              return new NetCoreBuilder(taskSpec.Properties);
            case NetFramework:
               return  new NetFrameworkBuilder(taskSpec.Properties);
            case ZIP:
                return  new ZipBuilder(taskSpec.Properties);
            case NexusDeploy:
                return  new NexusBuilder(taskSpec.Properties);
            case Nuget:
                return  new NugetBuilder(taskSpec.Properties);
            case GitVersion:
                return  new GitVersionBuilder(taskSpec.Properties);
            case RepositoryCheckout:
                return new RepositoryCheckoutBuilder(taskSpec.Properties);
            default:  return null;

        }
    }

    public static  VcsRepository<? extends VcsRepository<?, ?>, ? extends VcsRepositoryProperties> createRepository(ProjectSpec projectSpec){

        return new GitRepository()
                .name(projectSpec.Repository)
                .authentication(new UserPasswordAuthentication("andrepesi").password("!Aiolia1308"))
                .url(projectSpec.RepositoryUrl)
                .branch(projectSpec.DefaultBranch);

       /* return new BitbucketServerRepository()
                .name(projectSpec.Repository)
                .repositorySlug(projectSpec.Repository);*/
       /* return new BitbucketServerRepository()
                .name(projectSpec.Repository)
                .repositoryViewer(new BitbucketServerRepositoryViewer())
                .server(new ApplicationLink()
                            .name("Bitbucket")
                            .id("b591b47b-f206-3934-a4ab-b58e18ace5cf")
                ).projectKey("ENG").repositorySlug(projectSpec.Repository)
                .sshPublicKey("ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQDBKPtsJiIE2SuHnaL49S1wEs60Kz93AUNYotAftRlKdzyFmOXR05hsTE1ni82A/fsLeERTXX7/CtbBnRK0fe/eXNAkg5Ry4c/y9bSUR3vMaP8ysCkDnfINAvL8I2nHWeT3aYKEDKES9/TR3BsG5HRFVQ3yVBT0Zr0kGHXsmJaew7+YApo0b8CtirQQbb6c56/bZEmjD8gV74QREimMtkGruNNZx2/z0nvRbTyHG+itJis4Gi29cGlsE4qiuQtEKD3F5W9ENh0gn9Sp1vAy/j0p+QFCLZCqQjiRJwkT3nHqL5VVuUTF52yQt0junACLA3nyRSzhhAk/Nukh6GLSqFB5 https://bamboo.srs.nuance.com")
                .sshPrivateKey("BAMSCRT@0@0@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")
                .branch(projectSpec.DefaultBranch).changeDetection(new VcsChangeDetection());*/


    }
    public static PlanBranchManagement createPlanBranchManagement(ProjectSpec projectSpec){
        return new PlanBranchManagement()
                .createForVcsBranchMatching("(release)/.+")
                .delete(new BranchCleanup()
                        .whenRemovedFromRepositoryAfterDays(30));
    }
    public static ProjectSpec getProjectConfig() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .configure(SerializationFeature.INDENT_OUTPUT,true)
                .configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT,true);

        ProjectSpec config = mapper.readValue(new File("config.spec"),ProjectSpec.class);
        return config;
    }

    PlanPermissions createPlanPermission(PlanIdentifier planIdentifier) {
        Permissions permission = new Permissions()
                .userPermissions("andre_silva", PermissionType.ADMIN, PermissionType.CLONE, PermissionType.EDIT)
                .groupPermissions("bamboo-admin", PermissionType.ADMIN)
                .loggedInUserPermissions(PermissionType.VIEW)
                .anonymousUserPermissionView();
        return new PlanPermissions(planIdentifier.getProjectKey(), planIdentifier.getPlanKey()).permissions(permission);
    }

    Project createPoject(String name, SpecKey key) throws ValidationException {
        return new Project()
                .name(name)
                .key(key.getKey());
    }

    Plan createPlan(Project project,String name,SpecKey key,String description) throws ValidationException {
        return new Plan(
                project,
                name,key.getKey())
                .description(description);
    }
    Job createJob(Plan plan, String name,SpecKey key) throws ValidationException {
        return  new Job(name,key.getKey());
    }

    Stage createStage(String name){
        return new Stage(name);
    }
    public String SerializeObject(Object value) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .configure(SerializationFeature.INDENT_OUTPUT,true)
                .configure(SerializationFeature.FAIL_ON_SELF_REFERENCES,false)
                .configure(SerializationFeature.WRITE_NULL_MAP_VALUES,true);
        String json = new String();
        try {
            json = mapper.writeValueAsString(value);
            return json;

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        // return json;
    }
}
