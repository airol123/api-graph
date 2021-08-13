package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Item {

    private String id;
    private String comboId;
    private String instanceid;
    private String startvalidtime;
    private String endvalidtime;
    private String label;
    private HashMap<Object,Object> attributes= new HashMap<Object,Object> ();

    private ArrayList<User> addtocart =new ArrayList<>();
    private ArrayList<User> view =new ArrayList<>();
    private ArrayList<User> transaction = new ArrayList<>();

    public Item() {
    }

    public Item(String id, String comboId, String instanceid, String startvalidtime, String endvalidtime, String label, HashMap<Object, Object> attributes) {
        this.id = id;
        this.comboId = comboId;
        this.instanceid = instanceid;
        this.startvalidtime = startvalidtime;
        this.endvalidtime = endvalidtime;
        this.label = label;
        this.attributes = attributes;
    }

    public Item(HashMap<Object, Object> attributes) {
        this.attributes = attributes;
    }


    public Item(String id, String comboId, String instanceid, String startvalidtime, String endvalidtime, String label, HashMap<Object, Object> attributes, ArrayList<User> addtocart, ArrayList<User> view, ArrayList<User> transaction) {
        this.id = id;
        this.comboId = comboId;
        this.instanceid = instanceid;
        this.startvalidtime = startvalidtime;
        this.endvalidtime = endvalidtime;
        this.label = label;
        this.attributes = attributes;
        this.addtocart = addtocart;
        this.view = view;
        this.transaction = transaction;
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

    public String getInstanceid() {
        return instanceid;
    }

    public void setInstanceid(String instanceid) {
        this.instanceid = instanceid;
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
