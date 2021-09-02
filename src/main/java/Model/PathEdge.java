package Model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathEdge pathEdge = (PathEdge) o;
        return Objects.equals(source, pathEdge.source) && Objects.equals(target, pathEdge.target) && Objects.equals(label, pathEdge.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, target, label);
    }
}
