import java.util.Collection;
import java.util.HashSet;

public class Book {

    private static int UNKNOWN_TITLE_NUMBER = 0;
    private static int UNKNOWN_AUTHOR_NUMER = 0;
    private String title;
    private String author;
    private HashSet<City> cities;
    private final String CSV_SEPERATOR = ",";


    public Book() {
        title = "";
        author="";
        cities = new HashSet<>();
    }

    public String getTitle() {
        return title.replace("\"", "'");
    }

    public String getAuthor() {
        return author.replace("\"", "'");
    }

    public Collection<City> getCities() {
        return cities;
    }

    public void setTitleUnknown(){this.title = "unknown title #" + ++UNKNOWN_TITLE_NUMBER;}

    public void setAuthorUnknown(){this.author = "unknown author #" + ++UNKNOWN_AUTHOR_NUMER;}

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
        City[] arr = getCities().toArray(new City[getCities().size()]);
        StringBuilder sb = new StringBuilder();
        sb.append("\"" + getTitle().trim()+ "\"");
        sb.append(CSV_SEPERATOR);
        sb.append("\"" + getAuthor().trim()+ "\"");
        if (arr.length != 0) {
            sb.append(CSV_SEPERATOR);


            for (int i = 0; i < arr.length; i++) {
                sb.append("\"" + arr[i].toString().trim() + "\"");

                if (i == arr.length) {
                    sb.append(CSV_SEPERATOR);
                }
            }
        }


        return sb.toString();
    }

    public String toMongoString() {
        City[] arr = getCities().toArray(new City[getCities().size()]);
        StringBuilder sb = new StringBuilder();
        sb.append("\"" + getTitle().trim()+ "\"");
        sb.append(CSV_SEPERATOR);
        sb.append("\"" + getAuthor().trim()+ "\"");
        if (arr.length != 0) {
            sb.append(CSV_SEPERATOR);
            sb.append("\"");
            for (int i = 0; i < arr.length; i++) {
                sb.append(arr[i].toString().trim());

                if (i != arr.length-1) {
                    sb.append(".");
                }
            }
            sb.append("\"");
        }


        return sb.toString();
    }
}
