package commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandNames = {"publish","-p"},commandDescription = "Publish plan to BambooServer")
public class PublishCommand {
    @Parameter(names = { "-h" }, description = "ajuda")
    public String help;
}