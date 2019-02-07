package tasks.builders;

import com.atlassian.bamboo.specs.builders.task.CheckoutItem;
import com.atlassian.bamboo.specs.builders.task.VcsCheckoutTask;

import java.util.HashMap;
import java.util.Map;


/*Properties of this Task
* { repository : $repositoryName }
* {cleanCheckout : false }
*
* */

public class RepositoryCheckoutBuilder extends VcsCheckoutTask {
    public Map<String,String> Properties;

    RepositoryCheckoutBuilder(){
        super();
        checkoutItems(new CheckoutItem().defaultRepository());
    }
    public RepositoryCheckoutBuilder(Map<String, String> properties){
        super();
        Properties = properties;
        CheckoutItem checkoutItem = null;
        if(properties != null) {

            String repository = "";

            if (!properties.containsKey("repository") ||  properties.getOrDefault("repository","") == "")
                checkoutItem = new CheckoutItem().defaultRepository();
            else
                checkoutItem = new CheckoutItem().repository(repository);
        }
       // checkoutItems(checkoutItem);
        super.description("RepositoryCheckout").checkoutItems((checkoutItem));
        super.build();
    }
    public static  RepositoryCheckoutBuilder CreateDefault(){
        Map<String,String> config = new HashMap<>();
        config.put("repository","");
        return new RepositoryCheckoutBuilder(config);
    }
    public static VcsCheckoutTask Create(){
        return new VcsCheckoutTask()
                .description("Checkout Default Repository")
                .checkoutItems(new CheckoutItem().defaultRepository());
    }
}
