import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class AppTest {
    private List<CityVertex> nodes;
    private List<TravelEdge> edges;

    @Test
    public void testExcute() {
        nodes = new ArrayList<CityVertex>();
        edges = new ArrayList<TravelEdge>();
        for (int i = 0; i < 11; i++) {
            CityVertex location = new CityVertex("Node_" + i, "Node_" + i);
            nodes.add(location);
        }

        addLane("Edge_0", 0, 1, 85);
        addLane("Edge_1", 0, 2, 217);
        addLane("Edge_2", 0, 4, 173);
        addLane("Edge_3", 2, 6, 186);
        addLane("Edge_4", 2, 7, 103);
        addLane("Edge_5", 3, 7, 183);
        addLane("Edge_6", 5, 8, 250);
        addLane("Edge_7", 8, 9, 84);
        addLane("Edge_8", 7, 9, 167);
        addLane("Edge_9", 4, 9, 502);
        addLane("Edge_10", 9, 10, 40);
        addLane("Edge_11", 1, 10, 600);

        CityGraph graph = new CityGraph(nodes, edges);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
        dijkstra.execute(nodes.get(0), TravelWeather.Normal);
        LinkedList<CityVertex> path = dijkstra.getPath(nodes.get(10));

        assertNotNull(path);
        assertTrue(path.size() > 0);

        for (CityVertex vertex : path) {
            System.out.println(vertex);
        }

    }

    @Test
    public void testFileReader() {
        FileReader reader = new FileReader();
        FileReaderResult result = reader.readData();
        assertNotNull(result);

        ArrayList<CityVertex> vertexs = result.vertexs;
        ArrayList<TravelEdge> edges = result.edges;
        assertNotNull(vertexs);
        assertNotNull(edges);

        assertTrue(vertexs.size() > 0);
        assertTrue(edges.size() > 0);
    }

    @Test
    public void testDijkstraAlgorithm() {
        FileReader reader = new FileReader();
        FileReaderResult result = reader.readData();
        assertNotNull(result);

        ArrayList<CityVertex> vertexs = result.vertexs;
        ArrayList<TravelEdge> edges = result.edges;
        assertNotNull(vertexs);
        assertNotNull(edges);

        assertTrue(vertexs.size() > 0);
        assertTrue(edges.size() > 0);

        CityGraph graph = new CityGraph(vertexs, edges);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
        dijkstra.execute(vertexs.get(0), TravelWeather.Normal);
        LinkedList<CityVertex> path = dijkstra.getPath(vertexs.get(1));

        assertNotNull(path);
        assertTrue(path.size() > 0);

        for (CityVertex vertex : path) {
            System.out.println(vertex);
        }
    }

    @Test
    public void testGetVertexes() {
        List<CityVertex> vertexes = new ArrayList<>();
        vertexes.add(new CityVertex("A", "A"));
        vertexes.add(new CityVertex("B", "B"));
        vertexes.add(new CityVertex("C", "C"));
        CityGraph graph = new CityGraph(vertexes, new ArrayList<TravelEdge>());

        assertEquals(vertexes, graph.getVertexes());
    }

    @Test
    public void testRemoveVertexWithEdge() {
        List<CityVertex> vertexes = new ArrayList<>();
        CityVertex vertexA = new CityVertex("A", "A");
        CityVertex vertexB = new CityVertex("B", "B");
        CityVertex vertexC = new CityVertex("C", "C");
        vertexes.add(vertexA);
        vertexes.add(vertexB);
        vertexes.add(vertexC);

        List<TravelEdge> edges = new ArrayList<>();
        edges.add(new TravelEdge("1", vertexA, vertexB, 10, 10, 10, 10));
        edges.add(new TravelEdge("2", vertexB, vertexC, 20, 20, 20, 20));
        edges.add(new TravelEdge("3", vertexC, vertexA, 15, 15, 15, 15));

        CityGraph graph = new CityGraph(vertexes, edges);

        graph.removeVertexWithEdge(vertexB);

        assertFalse(graph.getVertexes().contains(vertexB));
        assertFalse(graph.getEdges().stream()
                .anyMatch(edge -> edge.getSource().equals(vertexB) || edge.getDestination().equals(vertexB)));
    }

    private void addLane(String laneId, int sourceLocNo, int destLocNo, int duration) {
        TravelEdge lane = new TravelEdge(laneId, nodes.get(sourceLocNo), nodes.get(destLocNo), duration, duration,
                duration, duration);
        edges.add(lane);
    }
}
