import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.neo4j.driver.v1.*;

import java.nio.charset.MalformedInputException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CitySearch implements IWorker {
    
    //Neo4J
    Driver driver;
    Session session;
    
    //Mongodb
    
    public CitySearch(String searchTerm, String mongoDatabaseName, String mongoCollectionName) throws Exception {
        if (searchTerm == null || mongoCollectionName == null || mongoDatabaseName == null) throw new NullPointerException();
        Pattern p = Pattern.compile("[A-z]+");
        Matcher searchPatternMatcher = p.matcher(searchTerm.trim());
        Matcher collectionPatternMatcher = p.matcher(mongoCollectionName.trim());
        Matcher dbPatternMatcher = p.matcher(mongoDatabaseName.trim());
        if(!searchPatternMatcher.matches())
        {
            throw new Exception(searchTerm + " not a valid search term");
        }
        if (!collectionPatternMatcher.matches())
        {
            throw new Exception(mongoCollectionName + " not a valid mongo collection term");
        }
        if (!dbPatternMatcher.matches())
        {
            throw new Exception(mongoDatabaseName + " not a valid mongo database term");
        }
        Neo4jConnect();
        //MongoConnect(mongoDatabaseName, mongoCollectionName);

        //Search(searchTerm);

    }
    
    
    @Override
    public void Neo4jConnect() {
        driver = GraphDatabase.driver(
                "bolt://165.227.128.49:7687/",
                AuthTokens.basic( "neo4j", "class" ));
        session = driver.session();

        
        // Run a query matching all nodesx
        StatementResult result = session.run("MATCH (c:City) RETURN c");
    
        for (Record obj : result.list()) {
            System.out.println(obj);
        }
        /*
        while ( result.hasNext() ) {
            Record record = result.next();
            System.out.println( record.get("name").asString() );
        }

        session.close();
        driver.close();
        */
    }
    
    @Override
    public void MongoConnect(String databaseName, String collectionName) {
        MongoClientURI connStr = new MongoClientURI("mongodb://207.154.228.197");
        MongoClient mongoClient = new MongoClient(connStr);
    
        MongoDatabase db = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> collection = db.getCollection(collectionName);
    
        Document myDoc = collection.find().first();
        System.out.println(myDoc.toJson());
    }
    
    //Work method(s)
    @Override
    public void Search(String searchString)
    {
        //TODO: 0. Tests
        //TODO: 1. Search
        //TODO: 2. Query for Databases
        //TODO: 3. Add timer
    }


}
