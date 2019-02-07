package commands;

import com.beust.jcommander.Parameter;

public class NugetAddTaskCommand {
    @Parameter(names = {"-projectDirectory","-project","-p"})
    public  String projectDirectory;
    @Parameter(names = {"-version","-v"})
    public  String version;
    @Parameter(names = {"-output","-o"})
    public  String output;
    @Parameter(names = {"-server","-s"})
    public  String server;
    @Parameter(names = {"-token","-t"})
    public  String token;
}
