package tasks.builders;

import com.atlassian.bamboo.specs.api.builders.AtlassianModule;
import com.atlassian.bamboo.specs.api.exceptions.PropertiesValidationException;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/*
 * { sourceFile : file to be uploaded }
 * { projectPath: directory of project }
 * { nexusPath  : Nexus destination path + destination filename }
 * { projectName : nameOfProject }
 * */
public class NexusBuilder extends BaseTask {
    public NexusBuilder(Map<String,String> properties){
        super(properties,"nuance.nuanceTask:NuanceNexusDeployTask");
    }
    public NexusBuilder(@NotNull AtlassianModule atlassianPlugin) throws PropertiesValidationException {
        super(new HashMap<>(),"nuance.nuanceTask:NuanceNexusDeployTask");
    }
}
