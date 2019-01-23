package specs;

import tasks.builders.GitVersionBuilder;
import tasks.builders.NetCoreBuilder;
import tasks.builders.NetFrameworkBuilder;
import tasks.builders.RepositoryCheckoutBuilder;

import java.util.ArrayList;
import java.util.Comparator;

public class BambooSpecConfiguration {

    public static ProjectSpec DefaultConfiguratioin(String projectName,String projectKey,
                                                    String planName,String projectPath,
                                                    String planKey,String planDescription,
                                                    String jobKey,
                                                    NetVersion version){
        ProjectSpec projectSpec = new ProjectSpec(projectName,projectKey);
        PlanSpec planSpec = new PlanSpec(planName,planKey,planDescription);
        StageSpec stageSpec = new StageSpec("Build");
        JobSpec job = new JobSpec("Default Job",jobKey);
        TaskSpec net = new TaskSpec();
        ArrayList<TaskSpec> tasks = new ArrayList<>();

        if(NetVersion.Framework == version) {
            NetFrameworkBuilder builder = NetFrameworkBuilder.CreateDefault(projectPath);
            net.TaskType = TaskType.NetFramework;
            net.Properties = builder.Properties;
        }
        else {
            NetCoreBuilder builder = NetCoreBuilder.CreateDefault(projectPath);
            net.TaskType = TaskType.NetCore;
            net.Properties = builder.Properties;
        }
        net.Order = 3;

        tasks.add(net);
        tasks.add(new TaskSpec(TaskType.RepositoryCheckout,null,0));
        tasks.add(new TaskSpec(TaskType.GitVersion, GitVersionBuilder.CreateDefault().Properties,1));

        tasks.stream().sorted(Comparator.comparing(t-> t.Order)).forEach((task) ->{
            job.addTask(task);
        });


        stageSpec.addJob(job);
        planSpec.addStage(stageSpec);
        projectSpec.addPlan(planSpec);
        return projectSpec;
    }


}
