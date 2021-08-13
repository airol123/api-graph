package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Category {

    private String id;
    private String comboId;
    private String startvalidtime;
    private String endvalidtime;
    private String label;

    private HashMap<Object,Object> attributes= new HashMap<Object,Object> ();

    private ArrayList<Category> subCategory = new ArrayList<Category>();

    public Category() {
    }

    public Category(HashMap<Object, Object> attributes) {
        this.attributes = attributes;
    }

    public Category(String id, String comboId, String startvalidtime, String endvalidtime, String label, HashMap<Object, Object> attributes) {
        this.id = id;
        this.comboId = comboId;
        this.startvalidtime = startvalidtime;
        this.endvalidtime = endvalidtime;
        this.label = label;
        this.attributes = attributes;
    }

    public Category(String id, String comboId, String startvalidtime, String endvalidtime, String label, HashMap<Object, Object> attributes, ArrayList<Category> subCategory) {
        this.id = id;
        this.comboId = comboId;
        this.startvalidtime = startvalidtime;
        this.endvalidtime = endvalidtime;
        this.label = label;
        this.attributes = attributes;
        this.subCategory = subCategory;
    }

    public HashMap<Object, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<Object, Object> attributes) {
        this.attributes.putAll(attributes);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComboId() {
        return comboId;
    }

    public void setComboId(String comboId) {
        this.comboId = comboId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getStartvalidtime() {
        return startvalidtime;
    }

    public void setStartvalidtime(String startvalidtime) {
        this.startvalidtime = startvalidtime;
    }

    public String getEndvalidtime() {
        return endvalidtime;
    }

    public void setEndvalidtime(String endvalidtime) {
        this.endvalidtime = endvalidtime;
    }
}
