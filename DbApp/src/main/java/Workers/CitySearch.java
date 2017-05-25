package Workers;

import Exceptions.InputException;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.neo4j.driver.v1.*;

import java.net.InetSocketAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CitySearch implements IWorker {

    //Neo4J
    Driver driver;
    Session session;
    
    //Mongodb
    
    public CitySearch(String mongoDatabaseName,
                      String mongoCollectionName,
                      InetSocketAddress neo4JInetSocket,
                      MongoClient mongoClient               ) throws InputException {

        if (mongoCollectionName == null || mongoDatabaseName == null) {throw new NullPointerException();}
        Pattern p = Pattern.compile("[A-z]+");
        Matcher collectionPatternMatcher = p.matcher(mongoCollectionName.trim());
        Matcher dbPatternMatcher = p.matcher(mongoDatabaseName.trim());
        if (!collectionPatternMatcher.matches())
        {
            throw new InputException(mongoCollectionName + " not a valid mongo collection term");
        }
        if (!dbPatternMatcher.matches())
        {
            throw new InputException(mongoDatabaseName + " not a valid mongo database term");
        }

        Neo4jConnect(neo4JInetSocket);
        MongoConnect(mongoDatabaseName, mongoCollectionName,mongoClient);

    }


    @Override
    public void Neo4jConnect(InetSocketAddress webAddress) {
        driver = GraphDatabase.driver(
                "bolt:/" + webAddress.getAddress()+":"+ webAddress.getPort()+"/",
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
        */
    }
    
    @Override
    public void MongoConnect(String databaseName, String collectionName, MongoClient mongoClient) {
        // connStr = new MongoClientURI("mongodb://207.154.228.197:27017");
        // MongoClient mongoClient = new MongoClient(connStr);
    
        MongoDatabase db = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> collection = db.getCollection(collectionName);

        Document myDoc = collection.find().first();
        System.out.println(myDoc.toJson());
    }
    
    //Work method(s)
    @Override
    public void Search(String searchString) throws InputException {
        //region Logic for input values
        if (searchString == null) throw new NullPointerException();
        Pattern p = Pattern.compile("[A-z]+");
        Matcher searchPatternMatcher = p.matcher(searchString.trim());

        if(!searchPatternMatcher.matches())
        {
            throw new InputException(searchString + " not a valid search term");
        }

        //endregion
        //TODO: 0. Tests
        //TODO: 1. Search
        //TODO: 2. Query for Databases
        //TODO: 3. Add timer

        //region Neo4J close statements
        session.close();
        driver.close();
        //endregion
    }


}
