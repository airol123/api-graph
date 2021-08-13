package Model;

import java.util.HashMap;
import java.util.Objects;

public class Edge {
    private String id;
    private String source;
    private String target;
    private String typeEdge;
    private HashMap<Object,Object> attributes= new HashMap<Object,Object> ();
    private HashMap<Object,Object> style= new HashMap<Object,Object> ();
    public Edge() {
    }

    public Edge(String source, String target, String type, HashMap<Object, Object> attributes) {
        this.source = source;
        this.target = target;
        this.typeEdge = type;
        this.attributes = attributes;
    }

    public Edge(String id, String source, String target, String type, HashMap<Object, Object> attributes) {
        this.id = id;
        this.source = source;
        this.target = target;
        this.typeEdge = type;
        this.attributes = attributes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getTypeEdge() {
        return typeEdge;
    }

    public void setTypeEdge(String typeEdge) {
        this.typeEdge = typeEdge;
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
        Edge edge = (Edge) o;
        return Objects.equals(id, edge.id) && Objects.equals(source, edge.source) && Objects.equals(target, edge.target) && Objects.equals(typeEdge, edge.typeEdge) && Objects.equals(attributes, edge.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, source, target, typeEdge, attributes);
    }
}
