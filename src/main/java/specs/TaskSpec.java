package specs;

import java.util.HashMap;
import java.util.Map;

public class TaskSpec {
    public int Order ;
    public TaskType TaskType;
    public Map<String,String> Properties;
   public TaskSpec(){

   }
   public TaskSpec(TaskType type,Map<String,String> properties,int order){
       this.TaskType = type;
       this.Properties = properties;
       this.Order = order;
   }
}
