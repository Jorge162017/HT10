public class TravelEdge {
    private final String id;
    private final CityVertex source;
    private final CityVertex destination;

    private final int weight;
    private final int rainWeight;
    private final int snowWeight;
    private final int stormWeight;

    public TravelEdge(String id, CityVertex source, CityVertex destination, int weight, int rainWeight, int snowWeight,
                      int stormWeight) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.weight = weight;
        this.rainWeight = rainWeight;
        this.snowWeight = snowWeight;
        this.stormWeight = stormWeight;
    }
    public String getId() {
        return id;
    }
    public CityVertex getDestination() {
        return destination;
    }
    public CityVertex getSource() {
        return source;
    }
    public int getWeight(TravelWeather weather) {
        switch (weather) {
            case Normal:
                return weight;
            case Lluvioso:
                return rainWeight;
            case Nevando:
                return snowWeight;
            case Tormenta:
                return stormWeight;
            default:
                return weight;
        }
    }
    @Override
    public String toString() {
        return source + " " + destination;
    }
}
