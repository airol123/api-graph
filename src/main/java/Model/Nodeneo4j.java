package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Nodeneo4j {
    private String identity;
    private List<Object> labels ;
    private HashMap<Object,Object> properties;

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public List<Object> getLabels() {
        return labels;
    }

    public void setLabels(List<Object> labels) {
        this.labels = labels;
    }

    public HashMap<Object, Object> getProperties() {
        return properties;
    }

    public void setProperties(HashMap<Object, Object> properties) {
        this.properties.putAll(properties);
    }

    @Override
    public String toString() {
        return "Nodeneo4j{" +
                "identity='" + identity + '\'' +
                ", labels=" + labels +
                ", properties=" + properties +
                '}';
    }
}
