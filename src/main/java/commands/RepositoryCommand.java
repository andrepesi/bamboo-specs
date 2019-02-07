package commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandNames = {"repository"},commandDescription = "Configure an repository")
public class RepositoryCommand
{
    @Parameter(names = {"--repo-name"})
    public  String name;
    @Parameter(names = {"--repo-url"})
    public  String url;
}

