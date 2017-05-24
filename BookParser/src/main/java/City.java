
public class City {

    private final String CSV_SEPERATOR = ",";
    private static int BOOK_ID = 0;
    private int id;
    private String name;
    private String latitude;
    private String longitude;

    public City(String name, String latitude, String longitude) {
        this.id = BOOK_ID++;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getCityId() {return id; }
    
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
        sb.append(String.valueOf(getCityId()).trim());
        sb.append(CSV_SEPERATOR);
        sb.append(getName().trim());
        sb.append(CSV_SEPERATOR);
        sb.append(getLatitude().trim());
        sb.append(CSV_SEPERATOR);
        sb.append(getLongitude().trim());

        return sb.toString();
    }
}
