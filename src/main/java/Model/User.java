package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class User {

    private String id; // unique id in the neo4j
    private String comboId; // userid
    private String label;
    private String startvalidtime;
    private String endvalidtime;

    private HashMap<Object,Object> attributes= new HashMap<Object,Object> ();

    private ArrayList<Item> addtocart =new ArrayList<>();
    private ArrayList<Item> view =new ArrayList<>();
    private ArrayList<Item> transaction = new ArrayList<>();

    public User() {
    }

    public User(HashMap<Object, Object> attributes) {
        this.attributes = attributes;
    }

    public User(String id, String comboId, String label, String startvalidtime, String endvalidtime, HashMap<Object, Object> attributes) {
        this.id = id;
        this.comboId = comboId;
        this.label = label;
        this.startvalidtime = startvalidtime;
        this.endvalidtime = endvalidtime;
        this.attributes = attributes;
    }

    public User(String id, String comboId, String label, String startvalidtime, HashMap<Object, Object> attributes) {
        this.id = id;
        this.comboId = comboId;
        this.label = label;
        this.startvalidtime = startvalidtime;
        this.attributes = attributes;
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
