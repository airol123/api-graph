package Model;

import java.util.ArrayList;

public class PathData {
    private ArrayList<PathNode> pathNodes= new ArrayList<>();
    private ArrayList<PathEdge> pathEdges=new ArrayList<>();

    //constructor


    public PathData() {
    }

    public PathData(ArrayList<PathNode> pathNodes, ArrayList<PathEdge> pathEdges) {
        this.pathNodes = pathNodes;
        this.pathEdges = pathEdges;
    }
    //getter&setter

    public ArrayList<PathNode> getPathNodes() {
        return pathNodes;
    }

    public void setPathNodes(ArrayList<PathNode> pathNodes) {
        this.pathNodes = pathNodes;
    }

    public ArrayList<PathEdge> getPathEdges() {
        return pathEdges;
    }

    public void setPathEdges(ArrayList<PathEdge> pathEdges) {
        this.pathEdges = pathEdges;
    }
}
