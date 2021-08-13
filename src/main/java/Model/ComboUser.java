package Model;

import java.util.Objects;

public class ComboUser {
    private String id;
    private String label;

    public ComboUser() {
    }
    public ComboUser(String id, String label) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComboUser comboUser = (ComboUser) o;
        return Objects.equals(id, comboUser.id) && Objects.equals(label, comboUser.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label);
    }
}
