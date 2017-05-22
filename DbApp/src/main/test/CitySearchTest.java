
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.nio.charset.MalformedInputException;

import static org.junit.Assert.*;


public class CitySearchTest {

    IWorker worker = null;

    @Test(expected = Exception.class)
    public void testNumbers() throws Exception {
        worker = new CitySearch("abc123","abc","abc");
    }

    @Test
    public void CorrectUse() throws Exception {

        worker = new CitySearch("abc","abc","abc");
    }

    @Test(expected= Exception.class)
    public void searchSpecialChar() throws Exception {

        worker = new CitySearch("å", "Correct", "Correct");

    }

    @Test(expected=Exception.class)
    public void dbNameSpecialChar() throws Exception {

        worker = new CitySearch("Correct", "å", "Correct");

    }

    @Test(expected=Exception.class)
    public void collectionSpecialChar() throws Exception {

        worker = new CitySearch("Correct", "Correct", "å");

    }
}