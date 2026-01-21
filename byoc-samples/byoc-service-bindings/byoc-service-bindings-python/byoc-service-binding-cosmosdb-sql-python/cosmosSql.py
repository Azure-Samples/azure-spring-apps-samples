import os
import uuid
import azure.cosmos.cosmos_client as cosmos_client

HOST = os.environ['AZURE_COSMOSDB_URI']
MASTER_KEY = os.environ['AZURE_COSMOSDB_KEY']
DATABASE_ID = os.environ['AZURE_COSMOSDB_DATABASE']

client = cosmos_client.CosmosClient(HOST, {'masterKey': MASTER_KEY} )

# Pre create your own database and container in your Cosmosdb for sql
db = client.get_database_client(DATABASE_ID)
container_id = 'mycollection'
container = db.get_container_client(container=container_id)

def all():
  item_list = read_items(container)
  return ''.join([str(x) for x in item_list])

def add(name):
  create_items(container, name)
  return "OK"

def read_items(container):
  # NOTE: Use MaxItemCount on Options to control how many items come back per trip to the server
  #       Important to handle throttles whenever you are doing operations such as this that might
  #       result in a 429 (throttled request)
  item_list = list(container.read_all_items(max_item_count=10))
  return item_list

def create_items(container, name):
    print('Creating Items')

    # Create a User object.
    # This can be saved as JSON as is without converting into rows/columns.
    user_obj = get_user(str(uuid.uuid1()), name)
    container.create_item(body=user_obj)

def get_user(item_id, name):
  user1 = {
    'id' : item_id,
    'name' : name,
  }
  return user1