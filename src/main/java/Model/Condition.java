package Model;

import java.util.Objects;

public class Condition {

    private String id;
    private String attribute;
    private String label;
    private String labelForQuery;
    private String operation;
    private String value;
    private String relation;

    public Condition() {
    }


    public Condition(String id, String attribute, String label, String labelForQuery, String operation, String value, String relation) {
        this.id = id;
        this.attribute = attribute;
        this.label = label;
        this.labelForQuery = labelForQuery;
        this.operation = operation;
        this.value = value;
        this.relation = relation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
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
        Condition condition = (Condition) o;
        return Objects.equals(id, condition.id) && Objects.equals(attribute, condition.attribute) && Objects.equals(label, condition.label) && Objects.equals(labelForQuery, condition.labelForQuery) && Objects.equals(operation, condition.operation) && Objects.equals(value, condition.value) && Objects.equals(relation, condition.relation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, attribute, label, labelForQuery, operation, value, relation);
    }
}
