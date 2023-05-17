import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
public class View {
    FileReader fileReader = new FileReader();
    FileReaderResult result = fileReader.readData();
    ArrayList<CityVertex> vertexs = result.vertexs;
    ArrayList<TravelEdge> edges = result.edges;
    Scanner scanner = new Scanner(System.in);

    public void init() {
        clear();
        printMenu();
    }

    private void printPath(LinkedList<CityVertex> path, TravelWeather weather, CityVertex from, CityVertex to) {
        System.out.println("Clima: " + weather);

        if (path == null) {
            System.out.println("Ruta no encontrada" + " de " + from + " a " + to);
            return;
        }

        System.out.print("Ruta: ");

        for (int index = 0; index < path.size(); index++) {
            CityVertex vertex = path.get(index);
            System.out.print(vertex);

            if (index < path.size() - 1) {
                System.out.print(" -> ");
            }
        }
    }

    private void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void printMenu() {
        int opcion = 0;
        while (opcion != 4) {
            System.out.println("Opciones del programa:");
            System.out.println("1. Calcular la ruta más corta entre dos ciudades");
            System.out.println("2. Encontrar la ciudad que queda en el centro del grafo");
            System.out.println("3. Modificar el grafo");
            System.out.println("4. Finalizar el programa");
            System.out.print("Ingrese una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    printShortestPath();
                    break;
                case 2:
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    printCenterGraph();
                    break;
                case 3:
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println("Modificar grafo");
                    break;
                case 4:
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println("Programa finalizado");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, ingrese una opción válida.");
            }

            System.out.println();
        }

        scanner.close();
    }

    private void printShortestPath() {
        boolean validChoice = false;
        TravelWeather selectedWeather = TravelWeather.Normal;

        while (!validChoice) {
            for (TravelWeather weather : TravelWeather.values()) {
                System.out.println(weather.ordinal() + 1 + ". " + weather);
            }

            System.out.print("Selecciona el clima del viaje: ");
            int choice = scanner.nextInt();
            if (choice > 0 && choice < TravelWeather.values().length + 1) {
                selectedWeather = TravelWeather.values()[choice - 1];
                validChoice = true;
            } else {
                clear();
                System.out.println("Opción inválida. Por favor, ingrese una opción válida.");
            }
        }

        CityGraph cityGraph = new CityGraph(vertexs, edges);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(cityGraph);

        scanner.nextLine();
        System.out.print("Ingresa la ciudad de origen: ");
        String fromCity = scanner.nextLine();

        System.out.print("Ingresa la ciudad de destino: ");
        String toCity = scanner.nextLine();

        CityVertex from = null;
        CityVertex to = null;

        for (CityVertex vertex : vertexs) {
            if (vertex.getName().toLowerCase().equals(fromCity.toLowerCase())) {
                from = vertex;
            }
            if (vertex.getName().toLowerCase().equals(toCity.toLowerCase())) {
                to = vertex;
            }
        }

        clear();
        if (from == null)
            System.out.println("No se encontró la ciudad de origen");
        if (to == null)
            System.out.println("No se encontró la ciudad de destino");
        if (from == null || to == null)
            return;

        dijkstra.execute(from, selectedWeather);

        LinkedList<CityVertex> path = dijkstra.getPath(to);
        clear();
        printPath(path, selectedWeather, from, to);
        System.out.println();
    }

    private void printCenterGraph() {
        CityGraph cityGraph = new CityGraph(vertexs, edges);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(cityGraph);

        boolean validChoice = false;
        TravelWeather selectedWeather = TravelWeather.Normal;

        while (!validChoice) {
            for (TravelWeather weather : TravelWeather.values()) {
                System.out.println(weather.ordinal() + 1 + ". " + weather);
            }

            System.out.print("Selecciona el clima: ");
            int choice = scanner.nextInt();
            if (choice > 0 && choice < TravelWeather.values().length + 1) {
                selectedWeather = TravelWeather.values()[choice - 1];
                validChoice = true;
            } else {
                clear();
                System.out.println("Opción inválida.");
            }
        }

        CityVertex center = dijkstra.findCenterVertex(selectedWeather);
        clear();
        System.out.println("Clima: " + selectedWeather);
        System.out.println("Ciudad en el centro del grafo: " + center);
    }
}
