import java.util.Collection;
import java.util.HashSet;

public class Book {

    private static int UNKNOWN_TITLE_NUMBER = 0;
    private static int UNKNOWN_AUTHOR_NUMER = 0;
    private static int BOOK_ID = 0;
    private int id;
    private String title;
    private String author;
    private HashSet<City> cities;
    private final String CSV_SEPERATOR = ",";


    public Book() {
        id = BOOK_ID++;
        title = "";
        author="";
        cities = new HashSet<>();
    }

    public String getTitle() {
        return title.replace("\"","'");
    }

    public String getAuthor() {
        return author.replace("\"","'");
    }

    public Collection<City> getCities() {
        return cities;
    }
    
    public int getBookId(){return id;}

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
        City[] cityArr = getCities().toArray(new City[getCities().size()]);
        
        StringBuilder sb = new StringBuilder();
        sb.append("\"" + String.valueOf(getBookId()).trim()+ "\"");
        sb.append(CSV_SEPERATOR);
        sb.append("\"" + getTitle().trim()+ "\"");
        sb.append(CSV_SEPERATOR);
        sb.append("\"" + getAuthor().trim()+ "\"");
        
       if (cityArr.length != 0) {
        sb.append(CSV_SEPERATOR);
           
            for (int i = 0; i < cityArr.length; i++) {
                sb.append("\"" + cityArr[i].toString().trim() + "\"");
                if (i != cityArr.length - 1) {
                    sb.append(CSV_SEPERATOR);
                }
            }
        }
        
        return sb.toString();
    }
}
