package Model;

import java.util.Objects;

public class NodeId {
    private String id;
    private String entityid;

    public NodeId() {
    }
    public NodeId(String id) {
        this.id = id;

    }
    public NodeId(String id, String entityid) {
        this.id = id;
        this.entityid = entityid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntityid() {
        return entityid;
    }

    public void setEntityid(String entityid) {
        this.entityid = entityid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeId nodeId = (NodeId) o;
        return Objects.equals(id, nodeId.id) && Objects.equals(entityid, nodeId.entityid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, entityid);
    }
}
