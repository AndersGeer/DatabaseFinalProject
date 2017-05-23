package Workers;

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
