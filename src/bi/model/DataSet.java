package bi.model;

/**
 * Created by Patrick on 30.11.16.
 */

import java.util.ArrayList;
import java.util.List;

public class DataSet {

    private List<Attribute> attributes = new ArrayList<Attribute>();
    private int length;

    public List<Attribute> getAttributes() {
        return this.attributes;
    }

    public void addAttribute(int pos, String name) {
        this.attributes.add(new Attribute(pos, name));
    }

    public void addAttribute(Attribute attribute) {
        this.attributes.add(attribute);
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getLength() {
        return this.length;
    }

    public void setAttributeAsCl(int pos) {
        this.attributes.get(pos).setAsCl();
    }

}
