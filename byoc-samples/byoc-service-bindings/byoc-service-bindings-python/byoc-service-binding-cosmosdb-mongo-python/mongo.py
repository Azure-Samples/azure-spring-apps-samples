import os
from pymongo import MongoClient

client = MongoClient(os.environ['SPRING_DATA_MONGODB_URI'])

def all():
  cursor = client.get_database().users.find()
  results = list(cursor)
  return ''.join([str(x) for x in results])

def add(name):
  mydict = { "name": name }
  x = client.get_database().users.insert_one(mydict)
  return "OK"