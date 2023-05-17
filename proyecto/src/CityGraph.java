import java.util.List;
public class CityGraph {
    private final List<CityVertex> vertexes;
    private final List<TravelEdge> edges;

    public CityGraph(List<CityVertex> vertexes, List<TravelEdge> edges) {
        this.vertexes = vertexes;
        this.edges = edges;
    }

    public List<CityVertex> getVertexes() {
        return vertexes;
    }

    public List<TravelEdge> getEdges() {
        return edges;
    }

    public void removeVertexWithEdge(CityVertex vertex) {
        vertexes.remove(vertex);
        edges.removeIf(edge -> edge.getSource().equals(vertex) || edge.getDestination().equals(vertex));
    }
}
