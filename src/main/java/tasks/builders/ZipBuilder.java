package tasks.builders;

import com.atlassian.bamboo.specs.api.builders.AtlassianModule;
import com.atlassian.bamboo.specs.api.builders.task.AnyTask;
import com.atlassian.bamboo.specs.api.exceptions.PropertiesValidationException;
import com.atlassian.bamboo.specs.util.MapBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/* Properties
*  { zipFileName : filename.zip }
*  { zipSourcePath : sourceFilePath\ }
*  { zipDestinationPath : destinationDirectory\
* */

public class ZipBuilder extends BaseTask {
    public ZipBuilder(Map<String,String> properties){
        super(properties,"nuance.nuanceTask:NuanceZIPTask");

    }
    public ZipBuilder(@NotNull AtlassianModule atlassianPlugin) throws PropertiesValidationException {
        super(new HashMap<>(),"nuance.nuanceTask:NuanceZIPTask");
    }
}
