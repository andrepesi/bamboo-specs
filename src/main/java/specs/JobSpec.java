package specs;

import com.atlassian.bamboo.specs.api.builders.task.AnyTask;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class JobSpec extends NameKeyPair{
    public ArrayList<TaskSpec> Tasks;
    public JobSpec(String name, String key){
        super(name, key);
        this.Tasks = new ArrayList<>();
    }
    public JobSpec(){}

    public  void addTask(@NotNull TaskSpec task){
        this.Tasks.add(task);
    }
}
