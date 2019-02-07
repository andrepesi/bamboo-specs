package specs;

import org.jetbrains.annotations.NotNull;

import javax.xml.bind.ValidationException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpecKey {
    private final String Key;
    private final String OID ;
    public SpecKey(@NotNull String key){
        Key = key.toUpperCase();
        OID = UUID.randomUUID().toString().replace("-","").substring(0,12).toLowerCase();
    }
    private  void validateKey() throws ValidationException {
        Pattern pattern = Pattern.compile("[A-Z][0-9]");
        Matcher matcher = pattern.matcher(this.Key);
        if(!matcher.find()){
            throw  new ValidationException(String.format("Bamboo Key is invalid. The must contain uppercase letters and one least number"));
        }
    }
    public String getKey() throws ValidationException {
        this.validateKey();
        return this.Key;
    }
    public String getOID() {
        return this.OID;
    }
}
