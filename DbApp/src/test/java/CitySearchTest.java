
import Workers.*;
import Exceptions.InputException;
import org.junit.Test;


public class CitySearchTest {

    IWorker worker = null;

    //region Black box input tests
    @Test(expected = InputException.class)
    public void testNumbers() throws InputException {
        worker = new CitySearch("abc123","abc","abc");
    }

    @Test
    public void CorrectUse() throws InputException {

        worker = new CitySearch("abc","abc","abc");
    }

    @Test(expected= InputException.class)
    public void searchSpecialChar() throws InputException {

        worker = new CitySearch("å", "Correct", "Correct");

    }

    @Test(expected=InputException.class)
    public void dbNameSpecialChar() throws InputException {

        worker = new CitySearch("Correct", "å", "Correct");

    }

    @Test(expected=InputException.class)
    public void collectionSpecialChar() throws InputException {

        worker = new CitySearch("Correct", "Correct", "å");
    }

    @Test(expected = NullPointerException.class)
    public void searchNullValue() throws NullPointerException, InputException {
        worker = new CitySearch(null, "Correct", "Correct");
    }
    @Test(expected = NullPointerException.class)
    public void dbNameNullValue() throws NullPointerException, InputException {
        worker = new CitySearch("Correct", null, "Correct");
    }
    @Test(expected = NullPointerException.class)
    public void collectionNameNullValue() throws NullPointerException, InputException {
        worker = new CitySearch("Correct", "Correct", null);
    }

    @Test(expected = InputException.class)
    public void searchEmptyValue() throws InputException {
        worker = new CitySearch("", "Correct", "Correct");
    }
    @Test(expected = InputException.class)
    public void dbNameEmptyValue() throws InputException {
        worker = new CitySearch("Correct", "", "Correct");
    }
    @Test(expected = InputException.class)
    public void collectionNameEmptyValue() throws InputException {
        worker = new CitySearch("Correct", "Correct", "");
    }

    @Test(expected = InputException.class)
    public void searchWSValue() throws InputException {
        worker = new CitySearch("  ", "Correct", "Correct");
    }
    @Test(expected = InputException.class)
    public void dbNameWSValue() throws InputException {
        worker = new CitySearch("Correct", "  ", "Correct");
    }
    @Test(expected = InputException.class)
    public void collectionNameWSValue() throws InputException {
        worker = new CitySearch("Correct", "Correct", "  ");
    }
    //endregion



}