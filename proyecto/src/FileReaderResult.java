import java.util.ArrayList;
public class FileReaderResult {
    ArrayList<CityVertex> vertexs;
    ArrayList<TravelEdge> edges;

    public FileReaderResult(ArrayList<CityVertex> vertexs, ArrayList<TravelEdge> edges) {
        this.vertexs = vertexs;
        this.edges = edges;
    }
}
