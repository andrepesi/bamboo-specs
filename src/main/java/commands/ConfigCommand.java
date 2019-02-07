package commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.ParametersDelegate;
import com.beust.jcommander.SubParameter;

@Parameters(commandNames = "config",commandDescription = "Enable custom Configurations")
public class ConfigCommand {
    @Parameter(order = 0)
    public String configurationType;

    @ParametersDelegate
    public RepositoryCommand repositoryCommand = new RepositoryCommand();

    static  class ConfigParameters
    {
        @SubParameter(order = 0)
        String type;
    }
    public enum ConfigType
    {
        repository,
        task
    }

}
