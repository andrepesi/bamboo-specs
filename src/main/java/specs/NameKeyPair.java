package specs;

import javax.xml.bind.ValidationException;

public class NameKeyPair {
    public String Name;
    public  String Key;
    private SpecKey _key;
    protected  NameKeyPair(){

    }
    public NameKeyPair(String name, String key){

        this.Name = name;
        this.Key = key;
        this._key = new SpecKey(key);
    }
}
