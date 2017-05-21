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
    default void Neo4jConnect()
    {
        Driver driver = GraphDatabase.driver(
                "bolt://:7687",
                AuthTokens.basic( "neo4j", "class" ) );
        Session session = driver.session();

        // Run a query matching all nodes
        StatementResult result = session.run(
                "MATCH (s)" +
                        "RETURN s.name AS name, s.job AS job");
        /*
        while ( result.hasNext() ) {
            Record record = result.next();
            System.out.println( record.get("name").asString() );
        }

        session.close();
        driver.close();
        */
    }

    default void MongoConnect(String databaseName, String collectionName)
    {
        MongoClientURI connStr = new MongoClientURI("");
        MongoClient mongoClient = new MongoClient(connStr);

        MongoDatabase db = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> collection = db.getCollection(collectionName);

        Document myDoc = collection.find().first();
        System.out.println(myDoc.toJson());
    }

    //Work method(s)
    void Search(String searchString);
}
