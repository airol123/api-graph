package Model;

import java.util.ArrayList;

public class PathData {
    private ArrayList<PathNode> nodes= new ArrayList<>();
    private ArrayList<PathEdge> edges=new ArrayList<>();

    //constructor


    public PathData() {
    }

    public PathData(ArrayList<PathNode> pathNodes, ArrayList<PathEdge> pathEdges) {
        this.nodes = pathNodes;
        this.edges = pathEdges;
    }
    //getter&setter

    public ArrayList<PathNode> getPathNodes() {
        return nodes;
    }

    public void setPathNodes(ArrayList<PathNode> pathNodes) {
        this.nodes = pathNodes;
    }

    public ArrayList<PathEdge> getPathEdges() {
        return edges;
    }

    public void setPathEdges(ArrayList<PathEdge> pathEdges) {
        this.edges = pathEdges;
    }
}
