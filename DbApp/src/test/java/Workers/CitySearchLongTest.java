package Workers;

import Exceptions.InputException;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoTimeoutException;
import org.junit.Test;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class CitySearchLongTest {
    IWorker worker = null;

    @Test(expected = MongoTimeoutException.class)
    public void NonExistentPortMongo() throws InputException, UnknownHostException {

        worker = new CitySearch("FinalDatabaseProject",
                "Books",
                new InetSocketAddress(InetAddress.getByName("165.227.128.49"),7687),
                new MongoClient(new MongoClientURI("mongodb://207.154.228.197:60000")));
    }

    @Test(expected = MongoTimeoutException.class)
    public void MongoPortNegative5() throws InputException, UnknownHostException {

        worker = new CitySearch("FinalDatabaseProject",
                "Books",
                new InetSocketAddress(InetAddress.getByName("165.227.128.49"),7687),
                new MongoClient(new MongoClientURI("mongodb://207.154.228.197:-5")));
    }
    @Test(expected = MongoTimeoutException.class)
    public void MongoPortNegative1() throws InputException, UnknownHostException {

        worker = new CitySearch("FinalDatabaseProject",
                "Books",
                new InetSocketAddress(InetAddress.getByName("165.227.128.49"),7687),
                new MongoClient(new MongoClientURI("mongodb://207.154.228.197:-1")));
    }
    @Test(expected = MongoTimeoutException.class)
    public void MongoPort0() throws InputException, UnknownHostException {

        worker = new CitySearch("FinalDatabaseProject",
                "Books",
                new InetSocketAddress(InetAddress.getByName("165.227.128.49"),7687),
                new MongoClient(new MongoClientURI("mongodb://207.154.228.197:0")));
    }
    @Test(expected = MongoTimeoutException.class)
    public void MongoPortPositive1() throws InputException, UnknownHostException {

        worker = new CitySearch("FinalDatabaseProject",
                "Books",
                new InetSocketAddress(InetAddress.getByName("165.227.128.49"),7687),
                new MongoClient(new MongoClientURI("mongodb://207.154.228.197:1")));
    }

    @Test(expected = MongoTimeoutException.class)
    public void MongoPortPositive2() throws InputException, UnknownHostException {

        worker = new CitySearch("FinalDatabaseProject",
                "Books",
                new InetSocketAddress(InetAddress.getByName("165.227.128.49"),7687),
                new MongoClient(new MongoClientURI("mongodb://207.154.228.197:2")));
    }

    @Test(expected = MongoTimeoutException.class)
    public void NonExistentIPMongo() throws InputException, UnknownHostException {

        worker = new CitySearch("FinalDatabaseProject",
                "Books",
                new InetSocketAddress(InetAddress.getByName("165.227.128.49"),7687),
                new MongoClient(new MongoClientURI("mongodb://256.256.256.256:27017")));
    }

}
