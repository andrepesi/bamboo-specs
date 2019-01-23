package tasks.builders;

import com.atlassian.bamboo.specs.api.builders.AtlassianModule;
import com.atlassian.bamboo.specs.api.builders.task.AnyTask;
import com.atlassian.bamboo.specs.api.exceptions.PropertiesValidationException;
import com.atlassian.bamboo.specs.util.MapBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class BaseTask extends AnyTask {
    private MapBuilder configuration;
    public Map<String,String> Properties;

    public BaseTask(@NotNull Map<String,String> properties, @NotNull String atlassianPlugin) throws PropertiesValidationException {
        super(new AtlassianModule(atlassianPlugin));
        Properties = properties != null ? properties : new HashMap<>();
        this.Properties = properties;

        this.configuration = new MapBuilder();
        this.Properties.forEach((key,value) -> {
            this.configuration.put(key,value);
        });
        super.description("Build")
                .configuration(this.configuration.build());
    }
    public MapBuilder getConfigurationBuilder(){
        return this.configuration;
    }
}
