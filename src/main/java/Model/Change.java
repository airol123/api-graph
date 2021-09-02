package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Change {
    private ArrayList<String> disappear=new ArrayList<>();
    private ArrayList<String > appear=new ArrayList<>();
    private HashMap<Object,Object> valuechange =new HashMap<>();
    private HashMap<Object,Object> validtime;

    public ArrayList<String> getDisappear() {
        return disappear;
    }

    public void setDisappear(ArrayList<String> disappear) {
        this.disappear = disappear;
    }

    public ArrayList<String> getAppear() {
        return appear;
    }

    public void setAppear(ArrayList<String> appear) {
        this.appear = appear;
    }

    public HashMap<Object, Object> getValuechange() {
        return valuechange;
    }

    public void setValuechange(HashMap<Object, Object> valuechange) {
        this.valuechange = valuechange;
    }

    public HashMap<Object, Object> getValidtime() {
        return validtime;
    }

    public void setValidtime(HashMap<Object, Object> validtime) {
        this.validtime = validtime;
    }
}
