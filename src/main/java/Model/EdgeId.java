package Model;

import java.util.Objects;

public class EdgeId {
    private String source;
    private String target;

    public EdgeId() {
    }

    public EdgeId(String source, String target) {
        this.source = source;
        this.target = target;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EdgeId edgeId = (EdgeId) o;
        return Objects.equals(source, edgeId.source) && Objects.equals(target, edgeId.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, target);
    }
}
