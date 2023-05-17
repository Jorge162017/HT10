import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DijkstraAlgorithm {
    private final List<CityVertex> nodes;
    private final List<TravelEdge> edges;
    private Set<CityVertex> settledNodes;
    private Set<CityVertex> unSettledNodes;
    private Map<CityVertex, CityVertex> predecessors;
    private Map<CityVertex, Integer> distance;

    public DijkstraAlgorithm(CityGraph graph) {
        this.nodes = new ArrayList<CityVertex>(graph.getVertexes());
        this.edges = new ArrayList<TravelEdge>(graph.getEdges());
    }
    public void execute(CityVertex source, TravelWeather weather) {
        settledNodes = new HashSet<CityVertex>();
        unSettledNodes = new HashSet<CityVertex>();
        distance = new HashMap<CityVertex, Integer>();
        predecessors = new HashMap<CityVertex, CityVertex>();
        distance.put(source, 0);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            CityVertex node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node, weather);
        }
    }
    private void findMinimalDistances(CityVertex node, TravelWeather weather) {
        List<CityVertex> adjacentNodes = getNeighbors(node);
        for (CityVertex target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node, target, weather)) {
                distance.put(target, getShortestDistance(node)
                        + getDistance(node, target, weather));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }

    }
    private int getDistance(CityVertex node, CityVertex target, TravelWeather weather) {
        for (TravelEdge edge : edges) {
            if (edge.getSource().equals(node)
                    && edge.getDestination().equals(target)) {
                return edge.getWeight(weather);
            }
        }
        throw new RuntimeException("No debe pasar nunca");
    }
    private List<CityVertex> getNeighbors(CityVertex node) {
        List<CityVertex> neighbors = new ArrayList<CityVertex>();
        for (TravelEdge edge : edges) {
            if (edge.getSource().equals(node)
                    && !isSettled(edge.getDestination())) {
                neighbors.add(edge.getDestination());
            }
        }
        return neighbors;
    }
    private CityVertex getMinimum(Set<CityVertex> vertexes) {
        CityVertex minimum = null;
        for (CityVertex vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }
    private boolean isSettled(CityVertex vertex) {
        return settledNodes.contains(vertex);
    }
    private int getShortestDistance(CityVertex destination) {
        Integer d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }
    public LinkedList<CityVertex> getPath(CityVertex target) {
        LinkedList<CityVertex> path = new LinkedList<CityVertex>();
        CityVertex step = target;
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        Collections.reverse(path);
        return path;
    }
    public CityVertex findCenterVertex(TravelWeather weather) {
        CityVertex centerVertex = null;
        int minDistanceSum = Integer.MAX_VALUE;

        for (CityVertex vertex : nodes) {
            execute(vertex, weather);
            int distanceSum = 0;

            for (CityVertex otherVertex : nodes) {
                if (otherVertex != vertex) {
                    LinkedList<CityVertex> path = getPath(otherVertex);
                    if (path != null) {
                        for (int i = 0; i < path.size() - 1; i++) {
                            CityVertex currentNode = path.get(i);
                            CityVertex nextNode = path.get(i + 1);
                            distanceSum += getDistance(currentNode, nextNode, weather);
                        }
                    }
                }
            }

            if (distanceSum < minDistanceSum) {
                minDistanceSum = distanceSum;
                centerVertex = vertex;
            }
        }

        return centerVertex;
    }
}

//Referencia : https://www.softwaretestinghelp.com/dijkstras-algorithm-in-java/