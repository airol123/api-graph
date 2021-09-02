package Model;

import java.util.HashMap;

public class NodeHistory {
    private String id;
    private String label;
    private String size;
    private String inDegree1;
    private String inDegree2;
    private String inDegree3;
    private String degree;
    private String type;
    private String x;
    private String y;
    private String color1;
    private String color2;

    private HashMap<Object,Object> style;

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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getInDegree1() {
        return inDegree1;
    }

    public void setInDegree1(String inDegree1) {
        this.inDegree1 = inDegree1;
    }

    public String getInDegree2() {
        return inDegree2;
    }

    public void setInDegree2(String inDegree2) {
        this.inDegree2 = inDegree2;
    }

    public String getInDegree3() {
        return inDegree3;
    }

    public void setInDegree3(String inDegree3) {
        this.inDegree3 = inDegree3;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public HashMap<Object, Object> getStyle() {
        return style;
    }

    public void setStyle(HashMap<Object, Object> style) {
        this.style=style;
    }

    public String getColor1() {
        return color1;
    }

    public void setColor1(String color1) {
        this.color1 = color1;
    }

    public String getColor2() {
        return color2;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }
}
