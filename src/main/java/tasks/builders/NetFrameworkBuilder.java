package tasks.builders;

import com.atlassian.bamboo.specs.api.builders.AtlassianModule;
import com.atlassian.bamboo.specs.api.exceptions.PropertiesValidationException;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class NetFrameworkBuilder extends BaseTask {
    public NetFrameworkBuilder(Map<String,String> properties){
        super(properties,"nuance.nuanceTask:NuanceDotNetFrameworkBuildTask");
    }

    public NetFrameworkBuilder(@NotNull AtlassianModule atlassianPlugin) throws PropertiesValidationException {
        super(new HashMap<>(),"nuance.nuanceTask:NuanceDotNetFrameworkBuildTask");
    }
    public static  NetFrameworkBuilder CreateDefault(String projectPath){
        return Create(projectPath,"","");
    }
    public static NetFrameworkBuilder Create(String projectPath,String version,String args){
        Map<String,String> config = new HashMap<>();
        config.put("projectPath",projectPath);
        config.put("version",version);
        config.put("args", args);
        return new NetFrameworkBuilder(config);
    }
}
