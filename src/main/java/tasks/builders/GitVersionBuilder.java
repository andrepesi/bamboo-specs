package tasks.builders;
import com.atlassian.bamboo.specs.api.exceptions.PropertiesValidationException;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
/*
*  { args", "/UpdateAssemblyInfo" }
* */
public class GitVersionBuilder extends BaseTask {

    public GitVersionBuilder(@NotNull Map<String, String> properties) throws PropertiesValidationException {
        super(properties, "com.carolynvs.gitversion:GitVersionTask");
    }
   /* public GitVersionBuilder() throws PropertiesValidationException {
        super(new AtlassianModule("com.carolynvs.gitversion:GitVersionTask"));
        super.description("Set Version")
                .configuration(new MapBuilder()
                        .put("args", "/UpdateAssemblyInfo")
                        .build());
    }*/
   public static  GitVersionBuilder CreateDefault(){
       return Create("/UpdateAssemblyInfo","","");
   }
    public static GitVersionBuilder Create(String args,String savedVars,String repoPath){
        Map<String,String> config = new HashMap<>();
        config.put("args", args);
        config.put("savedVars",savedVars);
        config.put("repoPath", repoPath);
        return new GitVersionBuilder(config);
    }
}
