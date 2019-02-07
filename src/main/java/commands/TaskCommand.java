package commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.ParametersDelegate;
import specs.TaskType;

@Parameters(commandNames = {"task"},commandDescription = "Add tasks")
public class TaskCommand
{
    @Parameter(order =0)
    public String type;
    @ParametersDelegate
    public NugetAddTaskCommand nugetAddTaskCommand = new NugetAddTaskCommand();

}
