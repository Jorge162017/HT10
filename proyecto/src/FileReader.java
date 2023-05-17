import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class FileReader {
    public FileReaderResult readData() {
        try {
            File file = new File("src/main/java/com/hdt10/logistica.txt");
            Scanner scanner = new Scanner(file);
            ArrayList<CityVertex> vertexs = new ArrayList<>();
            ArrayList<TravelEdge> edges = new ArrayList<>();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");

                String sourceName = parts[0];
                String destinationName = parts[1];
                int weight = Integer.parseInt(parts[2]);
                int rainWeight = Integer.parseInt(parts[3]);
                int snowWeight = Integer.parseInt(parts[4]);
                int stormWeight = Integer.parseInt(parts[5]);

                CityVertex source = new CityVertex(sourceName, sourceName);
                CityVertex destination = new CityVertex(destinationName, destinationName);
                vertexs.add(source);
                vertexs.add(destination);

                TravelEdge edge = new TravelEdge(source + "_" + destination, source, destination, weight, rainWeight,
                        snowWeight, stormWeight);
                edges.add(edge);
            }
            scanner.close();

            HashSet<CityVertex> vertexSet = new HashSet<>(vertexs);
            vertexs.clear();
            vertexs.addAll(vertexSet);

            return new FileReaderResult(vertexs, edges);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
