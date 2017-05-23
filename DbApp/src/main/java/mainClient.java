import Exceptions.InputException;
import Workers.CitySearch;
import asg.cliche.Command;
import asg.cliche.Param;

import java.io.IOException;

public class mainClient {

    //Commands
    @Command(description="Searches a city name to find all books and their authors mentioning that city")
    public void citySearch (@Param(name="City Name") String city) throws InputException {
        //TODO:
        CitySearch citySearch = new CitySearch(city,"helo","ff");
    }

    //Commands
    @Command(description="Searches a book title, plots all cities mentioned in this book onto a map.")
    public void titleSearch (@Param(name="Book Title") String title)
    {
        //TODO:
    }

    //Commands
    @Command(description="Searches an author name, lists all books written by that author and plots all cities mentioned in any of the books onto a map.")
    public void authorSearch (@Param(name="Author Name") String author)
    {
        //TODO:
    }

    //Commands
    @Command(description="Searches a geolocation, your application lists all books mentioning a city in vicinity of the given geolocation.")
    public void geoSearch (@Param(name="City Name") String city)
    {
        //TODO:
    }
    
    public static void main(String[] args) throws InputException, IOException {
        mainClient m = new mainClient();
        m.citySearch("Roskilde");
        /*
        Shell shell = ShellFactory.createConsoleShell("Books<^.^", "Welcome to books-console! \n\n?help for instructions\n?list for a list of commands\n\n", m);
        shell.commandLoop();
        */

    }
}
