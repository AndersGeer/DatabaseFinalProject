import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.neo4j.driver.v1.*;

/**
 * Created by flaps_win10 on 21-05-2017.
 */
public interface IWorker {
    
    //Connect to do databases method(s)
    void Neo4jConnect();

    void MongoConnect(String databaseName, String collectionName);

    //Work method(s)
    void Search(String searchString);
}
