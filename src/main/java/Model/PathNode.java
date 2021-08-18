package Model;

public class PathNode {
    private String id;
    private String label;
// constructor
    public PathNode() {

    }
    public PathNode(String id, String label) {
        this.id = id;
        this.label = label;
    }
    // getter& setter


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
