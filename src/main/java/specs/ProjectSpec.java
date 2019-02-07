package specs;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ProjectSpec extends NameKeyPair {
    public String OID;
    public String Repository;
    public String RepositoryUrl;
    public String DefaultBranch = "master";
    public BranchManagementType PlanBranchManagement = BranchManagementType.BranchMatching;
    public PlanSpec Plan;

    public ProjectSpec(){}
    ProjectSpec(String name, String key, String oid) {
        super(name,key);
        OID = oid;

    }
    public void addPlan(@NotNull PlanSpec plan){
        this.Plan = plan;
    }
}
