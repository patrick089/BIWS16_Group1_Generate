package bi.model;

/**
 * Created by Patrick on 30.11.16.
 */

import java.util.ArrayList;
import java.util.List;

public class Attribute {

    private int pos;
    private String name;
    private List<String> values;
    private boolean cl = false;

    public Attribute(int pos, String name, boolean cl) {
        this.pos = pos;
        this.name = name;
        this.cl = cl;
        this.values = new ArrayList<String>();
    }

    public Attribute(int pos, String name) {
        this.pos = pos;
        this.name = name;
        this.values = new ArrayList<String>();
    }

    public int getPos() {
        return pos;
    }

    public String getName() {
        return name;
    }

    public void addValue(String value) {
        this.values.add(value);
    }

    public List<String> getValues() {
        return this.values;
    }
    
    public void setValues(List<String> values){
    	this.values = values;
    }

    public String getValue(int pos) {
        return this.values.get(pos);
    }

    public boolean isCl() {
        return this.cl;
    }

    protected void setAsCl() {
        this.cl = true;
    }

}
