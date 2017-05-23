import java.util.Collection;
import java.util.HashSet;

public class Book {

    private static int UNKNOWN_TITLE_NUMBER = 0;
    private String title;
    private String author;
    private HashSet<City> cities;
    private final String CSV_SEPERATOR = "|";

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

    public Collection<City> getCities() {
        return cities;
    }

    public void setTitleUnknown(){this.title = "unknown title #" + ++UNKNOWN_TITLE_NUMBER;}

    public void setTitle(String title) {
        this.title = title.trim();
    }

    public void addToTitle(String stringToAdd){
        this.title = this.title + " " + stringToAdd.trim();
    }

    public void addToAuthor(String stringToAdd){
        this.author = this.author + " " + stringToAdd.trim();
    }

    public void setAuthor(String author) {
        this.author = author.trim();
    }

    public void addCity(City city)
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
        for (City c:cities){
            sb.append(c.toString());
            sb.append(CSV_SEPERATOR);
        }
        return sb.toString();
    }
}
