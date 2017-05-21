public class CitySearch implements IWorker {
    public CitySearch(String searchTerm, String mongoDatabaseName, String mongoCollectionName)
    {
        Neo4jConnect();
        MongoConnect(mongoDatabaseName, mongoCollectionName);

        Search(searchTerm);

    }


    //Work method(s)
    @Override
    public void Search(String searchString)
    {

    }


}
