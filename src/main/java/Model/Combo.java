package Model;

import java.util.HashMap;
import java.util.Objects;

public class Combo {
    private String id;
    private String label;
    private HashMap<Object,Object> style= new HashMap<Object,Object> ();

    public Combo() {
    }

    public Combo(String id, String label) {
        this.id = id;
        this.label = label;
    }

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
        Combo combo = (Combo) o;
        return Objects.equals(id, combo.id) && Objects.equals(label, combo.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label);
    }
}
