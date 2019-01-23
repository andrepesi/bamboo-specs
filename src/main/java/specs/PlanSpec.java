package specs;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PlanSpec extends NameKeyPair {
    public String Description;
    public ArrayList<StageSpec> Stages;

    public PlanSpec(){}
    public PlanSpec(String name,String key,String description){
        super(name,key);
        this.Description = description;
        Stages = new ArrayList<>();

    }
    public void addStage(@NotNull StageSpec stage){
        this.Stages.add(stage);
    }
}
