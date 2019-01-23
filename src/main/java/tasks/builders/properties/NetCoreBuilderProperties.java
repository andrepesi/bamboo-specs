package tasks.builders.properties;

import com.atlassian.bamboo.specs.api.codegen.annotations.Builder;
import com.atlassian.bamboo.specs.api.codegen.annotations.ConstructFrom;
import com.atlassian.bamboo.specs.api.model.AtlassianModuleProperties;
import com.atlassian.bamboo.specs.api.model.plan.condition.AnyConditionProperties;
import com.atlassian.bamboo.specs.api.model.plan.condition.ConditionProperties;
import com.atlassian.bamboo.specs.api.model.plan.requirement.RequirementProperties;
import com.atlassian.bamboo.specs.api.model.task.TaskProperties;
import com.atlassian.bamboo.specs.api.codegen.annotations.Setter;
import jdk.nashorn.internal.ir.annotations.Immutable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Immutable
public class NetCoreBuilderProperties  extends TaskProperties {
    @Setter("projectPath")
    String projectPath;
    @Setter("projectName")
    String projectName;
    @Setter("version")
    String version;
    @Setter("arguments")
    String arguments;
    @Setter("configuration")
    String configuration;
    public NetCoreBuilderProperties(String projectPath, String projectName, String version, String arguments, String configuration){
        super();

        this.projectPath = projectPath;
        this.projectName = projectName;
        this.version = version;
        this.arguments = arguments;
        this.configuration = configuration;

    }
    @NotNull
    @Override
    public AtlassianModuleProperties getAtlassianPlugin() {
        return new AtlassianModuleProperties("nuance.nuanceTask");
    }



}

