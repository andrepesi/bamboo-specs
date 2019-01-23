package tasks.builders;

import com.atlassian.bamboo.specs.util.MapBuilder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class NetCoreBuilder extends BaseTask {
    public NetCoreBuilder(Map<String,String> properties){
        super(properties,"nuance.nuanceTask:NuanceDotNetCoreBuildTask");



    }
    public NetCoreBuilder(){
        super(new HashMap<>(),"nuance.nuanceTask:NuanceDotNetCoreBuildTask");

    }
    public static  NetCoreBuilder CreateDefault(String projectPath){
        return Create(projectPath,"${bamboo.GitVersion.AssemblySemVer}","","Release");
    }
    public static NetCoreBuilder Create(String projectPath,String version,String args,String configuration){
        Map<String,String> config = new HashMap<>();
        config.put("projectPath",projectPath);
        config.put("version",version);
        config.put("configuration",version);
        config.put("args", args);
        return new NetCoreBuilder(config);
    }
}