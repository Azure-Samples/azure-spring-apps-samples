const CosmosClient = require("@azure/cosmos").CosmosClient;

endpoint = process.env.AZURE_COSMOSDB_URI
key = process.env.AZURE_COSMOSDB_KEY
databaseId = process.env.AZURE_COSMOSDB_DATABASE

containerId = 'mycollection'

const client = new CosmosClient({ endpoint, key });
// Make sure Tasks database is already setup. If not, create it.
const database = client.database(databaseId);
const container = database.container(containerId);

// query to return all items
const querySpec = {
  query: "SELECT * from c"
};

async function all(res){ 
  // read all items in the Items container
  const { resources: items } = await container.items.query(querySpec).fetchAll();
  console.log(items);
  res.send(items)
};

async function add(name){
  const newItem = {
    id: console.timeStamp(),
    name: name
  };

  await container.items.create(newItem);
}

module.exports = { all, add }