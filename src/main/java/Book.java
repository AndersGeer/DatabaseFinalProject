import java.util.HashSet;

public class Book {

    private String title;
    private String author;
    private HashSet<String> cities;
    private final String CSV_SEPERATOR = ",";

    public Book() {
        title = "";
        author="";
        cities = new HashSet<>();
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public HashSet<String> getCities() {
        return cities;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void addCity(String city)
    {
        cities.add(city);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String oneLine = "";

        sb.append(getTitle());
        sb.append(CSV_SEPERATOR);
        sb.append(getAuthor());
        sb.append(CSV_SEPERATOR);
        sb.append(getCities());

        return sb.toString();
    }
}
