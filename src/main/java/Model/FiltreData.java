package Model;

import java.util.ArrayList;
import java.util.Objects;

public class FiltreData {
    private PathData pathData;
    private ArrayList<Condition> conditions;

    public FiltreData() {}

    public FiltreData(PathData pathData, ArrayList<Condition> conditions) {
        this.pathData = pathData;
        this.conditions = conditions;
    }

    public PathData getPathData() {
        return pathData;
    }

    public void setPathData(PathData pathData) {
        this.pathData = pathData;
    }

    public ArrayList<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(ArrayList<Condition> conditions) {
        this.conditions = conditions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FiltreData that = (FiltreData) o;
        return Objects.equals(pathData, that.pathData) && Objects.equals(conditions, that.conditions);
    }
}
