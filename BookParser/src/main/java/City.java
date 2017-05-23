
public class City {

    private final String CSV_SEPERATOR = ",";
    private String name;
    private String latitude;
    private String longitude;

    public City(String name, String latitude, String longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String oneLine = "";

        sb.append(getName());
        sb.append(CSV_SEPERATOR);
        sb.append(getLatitude());
        sb.append(CSV_SEPERATOR);
        sb.append(getLongitude());

        return sb.toString();
    }
}
