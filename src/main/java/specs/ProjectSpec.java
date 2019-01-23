package specs;

import org.jetbrains.annotations.NotNull;

public class ProjectSpec extends NameKeyPair {
    public String Repository;
    public String RepositoryUrl;
    public String DefaultBranch = "master";
    public BranchManagementType PlanBranchManagement = BranchManagementType.BranchMatching;
    public PlanSpec Plan;

    public ProjectSpec(){}
    ProjectSpec(String name, String key) {
        super(name,key);
    }
    public void addPlan(@NotNull PlanSpec plan){
        this.Plan = plan;
    }
}
