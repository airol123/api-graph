package Model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Objects;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Node {
    private String id;
    private String comboId;
    private String instanceid;
    private String startvalidtime;
    private String endvalidtime;
    private String label;
    private String type;
    private int nbChange;
    private HashMap<Object,Object> attributes= new HashMap<Object,Object> ();
    private HashMap<Object,Object> style= new HashMap<Object,Object> ();
    public Node() {
    }

    public Node(String id, String comboId, String instanceid, String startvalidtime, String endvalidtime, String label, HashMap<Object, Object> attributes) {
        this.id = id;
        this.comboId = comboId;
        this.instanceid = instanceid;
        this.startvalidtime = startvalidtime;
        this.endvalidtime = endvalidtime;
        this.label = label;
        this.attributes = attributes;
    }

    public Node(String id, String comboId, String instanceid, String startvalidtime, String endvalidtime, String label, int nbChange, HashMap<Object, Object> attributes) {
        this.id = id;
        this.comboId = comboId;
        this.instanceid = instanceid;
        this.startvalidtime = startvalidtime;
        this.endvalidtime = endvalidtime;
        this.label = label;
        this.nbChange = nbChange;
        this.attributes = attributes;
    }
    public Node(String id, String instanceid, String startvalidtime, String endvalidtime, String label, int nbChange, HashMap<Object, Object> attributes) {
        this.id = id;
        this.instanceid = instanceid;
        this.startvalidtime = startvalidtime;
        this.endvalidtime = endvalidtime;
        this.label = label;
        this.nbChange = nbChange;
        this.attributes = attributes;
    }
    public Node(String id, String comboId, String instanceid, String startvalidtime, String endvalidtime, String label, String type, int nbChange, HashMap<Object, Object> attributes) {
        this.id = id;
        this.comboId = comboId;
        this.instanceid = instanceid;
        this.startvalidtime = startvalidtime;
        this.endvalidtime = endvalidtime;
        this.label = label;
        this.type = type;
        this.nbChange = nbChange;
        this.attributes = attributes;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getNbChange() {
        return nbChange;
    }

    public void setNbChange(int nbChange) {
        this.nbChange = nbChange;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HashMap<Object, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<Object, Object> attributes) {
        this.attributes.putAll(attributes);
    }

    public HashMap<Object, Object> getStyle() {
        return style;
    }

    public void setStyle(HashMap<Object, Object> style) {
        this.style.putAll(style);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(id, node.id) && Objects.equals(comboId, node.comboId) && Objects.equals(instanceid, node.instanceid) && Objects.equals(startvalidtime, node.startvalidtime) && Objects.equals(endvalidtime, node.endvalidtime) && Objects.equals(label, node.label) && Objects.equals(attributes, node.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, comboId, instanceid, startvalidtime, endvalidtime, label, attributes);
    }
}
