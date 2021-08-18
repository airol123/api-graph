package Model;

public class PathEdge {
    private String source;
    private String target;
    private String label;

    //constructor

    public PathEdge() {
    }

    public PathEdge(String source, String target, String label) {
        this.source = source;
        this.target = target;
        this.label = label;
    }

    // getter&setter

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
