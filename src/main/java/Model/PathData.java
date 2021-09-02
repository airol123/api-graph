package Model;

import java.util.ArrayList;

public class PathData {
    private ArrayList<PathNode> nodes= new ArrayList<>();
    private ArrayList<PathEdge> edges=new ArrayList<>();

    public PathData(ArrayList<PathNode> nodes, ArrayList<PathEdge> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    public ArrayList<PathNode> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<PathNode> nodes) {
        this.nodes = nodes;
    }

    public ArrayList<PathEdge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<PathEdge> edges) {
        this.edges = edges;
    }


    @Override
    public String toString() {
        return "PathData{" +
                "nodes=" + nodes +
                ", edges=" + edges +
                '}';
    }
}
