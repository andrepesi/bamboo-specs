package tasks.builders;

import com.atlassian.bamboo.specs.api.builders.AtlassianModule;
import com.atlassian.bamboo.specs.api.exceptions.PropertiesValidationException;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
/*
 * { nugetToken : authentication token }
 * { projectPath: directory of project }
 * { version : version of package }
 * { projectName : nameOfProject }
 * { nugetServer : url of nugetserver }
 *
 * */
public class NugetBuilder  extends BaseTask{
    public NugetBuilder(Map<String,String> properties){
        super(properties,"nuance.nuanceTask:NuanceNugetPackTask");
    }
    public NugetBuilder(@NotNull AtlassianModule atlassianPlugin) throws PropertiesValidationException {
        super(new HashMap<>(),"nuance.nuanceTask:NuanceNugetPackTask");
    }
    public static Map<String,String> SetProperties(String projectDirectory,String version,String nugetServer,String nugetToken,String nugetPackagePath){
        HashMap<String,String> props =  new HashMap<>();
        props.put("nugetToken",nugetToken);
        props.put("version",version);
        props.put("nugetServer",nugetServer);
        props.put("nugetPackagePath",nugetPackagePath);
        props.put("projectPath",projectDirectory);
        return props;
    }
}
