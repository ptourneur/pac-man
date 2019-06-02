package Model;

import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


class Vertex implements Comparable<Vertex> {
    public int x;
    public int y;
    public Edge[] adjacencies;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Vertex previous;

    public Vertex(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return Integer.toString(x) + " " + Integer.toString(y) ;
    }

    public int compareTo(Vertex other) {
        return Double.compare(minDistance, other.minDistance);
    }
}

class Edge {
    public final Vertex target;
    public final double weight = 1;

    public Edge(Vertex argTarget) {
        target = argTarget;
    }
}

public class Dijkstra {


    private ArrayList<Vertex> gridToGraph(Grid grid) {
        ArrayList<Vertex> v = gridToVertexs(grid);
        return createEdges(v, grid);
    }

    private ArrayList<Vertex> gridToVertexs(Grid grid) {
        ArrayList<Vertex> graph = new ArrayList<>();
        for (int i = 0; i < grid.getColumnCount(); i++) {
            for (int j = 0; j < grid.getColumnCount(); j++) {
                if (!(grid.getElement(i, j) instanceof Wall))
                    graph.add(new Vertex(i, j));
            }
        }
        return graph;
    }

    private ArrayList<Vertex> createEdges(ArrayList<Vertex> list, Grid grid) {
        Position[] positions;
        for (Vertex vertex : list) {
            positions = grid.getNeighbors(vertex.x, vertex.y);
            if (positions != null) {
//				System.out.println(vertex.toString());
//				for (Position p : positions) {
//					System.out.println(p.toString());
//				}
                Edge[] edges = new Edge[positions.length];
                for (int i = 0; i < positions.length; i++) {
                    Vertex v = getVertexByPos(list, positions[i].x,	positions[i].y);
                    if(v!=null)
                        edges[i] = new Edge(v);
                }
                vertex.adjacencies = edges;
            }
        }
        return list;
    }

    private Vertex getVertexByPos(ArrayList<Vertex> list, int x, int y) {
        for (Vertex v : list) {
            if (v.x == x && v.y == y)
                return v;
        }
        return null;
    }

    private void computePaths(Vertex source) {
        source.minDistance = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
        vertexQueue.add(source);

        while (!vertexQueue.isEmpty()) {
            Vertex u = vertexQueue.poll();

            // Visit each edge exiting u
            for (Edge e : u.adjacencies) {
                Vertex v = e.target;
                double weight = e.weight;
                double distanceThroughU = u.minDistance + weight;
                if (distanceThroughU < v.minDistance) {
                    vertexQueue.remove(v);
                    v.minDistance = distanceThroughU;
                    v.previous = u;
                    vertexQueue.add(v);
                }
            }
        }
    }

    private List<Vertex> getShortestPathTo(Vertex target) {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);
        Collections.reverse(path);
        return path;
    }

    public int getDistanceToTheClosestDot(int x, int y, Grid grid) {
        ArrayList<Vertex> gridGraph = gridToGraph(grid);
        computePaths(getVertexByPos(gridGraph, x, y));

        int disClosestDot = grid.getColumnCount() * grid.getRowCount();
        for (int i = 0; i < grid.getColumnCount(); i++) {
            for (int j = 0; j < grid.getRowCount(); j++) {
                Vertex v = getVertexByPos(gridGraph, i, j);
                if (v != null && grid.getPacman().getX()==i && grid.getPacman().getY()==j )
                    if (v.minDistance < disClosestDot)
                        disClosestDot = (int) v.minDistance;
            }
        }
        return disClosestDot;
    }


    public int getDistanceToTheClosestGhost(int x, int y, Ghost[] ghosts, Grid grid, boolean eatable) {
        ArrayList<Vertex> gridGraph = gridToGraph(grid);
        computePaths(getVertexByPos(gridGraph, x, y));

        int disClosestGhost = grid.getColumnCount() * grid.getRowCount();
        for (int i = 0; i < ghosts.length; i++) {
            Vertex v = getVertexByPos(gridGraph, x, y);
            if (v != null )
                if (v.minDistance < disClosestGhost)
                    disClosestGhost = (int) v.minDistance;
        }
        return disClosestGhost==grid.getColumnCount() * grid.getRowCount()?0:disClosestGhost;
    }

    public float getDistance(int x1, int y1, int x2, int y2, Grid grid){
        ArrayList<Vertex> gridGraph = gridToGraph(grid);
        computePaths(getVertexByPos(gridGraph, x1, y1));
        Vertex v = getVertexByPos(gridGraph, x2, y2);
        return (float) (v==null?-1:v.minDistance);
    }

}