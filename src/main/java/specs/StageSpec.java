package specs;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class StageSpec {
    public  String Name;
    public ArrayList<JobSpec> Jobs;
    public StageSpec(String name){
        this.Name = name;
        this.Jobs = new ArrayList<>();
    }
    public StageSpec(){}
    public void addJob(@NotNull  JobSpec job){
        this.Jobs.add(job);
    }
}
