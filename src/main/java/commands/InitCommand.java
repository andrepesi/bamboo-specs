package commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import specs.NetVersion;

@Parameters(commandNames = {"init","-i"},commandDescription = "Record changes to the repository")
public class InitCommand {

    @Parameter(names = { "-projectDirectory" }, description = "Directory of the project",required = true)
    public String projectDirectory;
    @Parameter(names = { "-target" }, description = ".Net version (Core or Framework)",required = true)
    public NetVersion version;
    @Parameter(names = { "-name" }, description = "Name of Bamboo Build Project", required = true)
    public String name;
}