package Model;

import java.util.HashMap;

public class Belongto {

    private long id;
    private String startId;
    private String endId;
    private String startvalidtime;
    private String endvalidtime;
    private String type;
    HashMap<Object,Object> attributes =new HashMap<>();

    public Belongto() {
    }

    public Belongto(long id, String startId, String endId, String startvalidtime, String endvalidtime, String type, HashMap<Object, Object> attributes) {
        this.id = id;
        this.startId = startId;
        this.endId = endId;
        this.startvalidtime = startvalidtime;
        this.endvalidtime = endvalidtime;
        this.type = type;
        this.attributes = attributes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStartId() {
        return startId;
    }

    public void setStartId(String startId) {
        this.startId = startId;
    }

    public String getEndId() {
        return endId;
    }

    public void setEndId(String endId) {
        this.endId = endId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HashMap<Object, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<Object, Object> attributes) {
        this.attributes.putAll(attributes);
    }

    public String getStartvalidtime() {
        return startvalidtime;
    }

    public void setStartvalidtime(String startvalidtime) {
        this.startvalidtime = startvalidtime;
    }

    public String getEndvalidtime() {
        return endvalidtime;
    }

    public void setEndvalidtime(String endvalidtime) {
        this.endvalidtime = endvalidtime;
    }
}
