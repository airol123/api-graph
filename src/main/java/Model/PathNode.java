package Model;

import java.util.Objects;

public class PathNode {
    private String id;
    private String label;
    private String labelForQuery;
// constructor
    public PathNode() {

    }
    public PathNode(String id, String label) {
        this.id = id;
        this.label = label;
    }

    public PathNode(String id, String label, String labelForQuery) {
        this.id = id;
        this.label = label;
        this.labelForQuery = labelForQuery;
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

    public String getLabelForQuery() {
        return labelForQuery;
    }

    public void setLabelForQuery(String labelForQuery) {
        this.labelForQuery = labelForQuery;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathNode pathNode = (PathNode) o;
        return Objects.equals(id, pathNode.id) && Objects.equals(label, pathNode.label) && Objects.equals(labelForQuery, pathNode.labelForQuery);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label, labelForQuery);
    }
}
