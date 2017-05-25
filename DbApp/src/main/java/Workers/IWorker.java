package Workers;

import Exceptions.InputException;
import com.mongodb.MongoClient;

import java.net.InetSocketAddress;

public interface IWorker {
    
    //Connect to do databases method(s)
    void Neo4jConnect(InetSocketAddress webAddress);

    void MongoConnect(String databaseName, String collectionName, MongoClient mongoClient);

    //Work method(s)
    void Search(String searchString) throws InputException;
}
