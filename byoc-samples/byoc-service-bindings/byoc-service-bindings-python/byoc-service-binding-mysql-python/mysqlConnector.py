import mysql.connector
from mysql.connector import errorcode
import os

config = {
  'host': os.environ['AZURE_DATASOURCE_HOST'],
  'database': os.environ['AZURE_DATASOURCE_DATABASE'],
  'user': os.environ['SPRING_DATASOURCE_USERNAME'],
  'password':os.environ['SPRING_DATASOURCE_PASSWORD'],
  'client_flags': [mysql.connector.ClientFlag.SSL]
}

# Construct connection string
try:
   conn = mysql.connector.connect(**config)
   print("Connection established")
except mysql.connector.Error as err:
  if err.errno == errorcode.ER_ACCESS_DENIED_ERROR:
    print("Something is wrong with the user name or password")
  elif err.errno == errorcode.ER_BAD_DB_ERROR:
    print("Database does not exist")
  else:
    print(err)
else:
  cursor = conn.cursor()

# Pre create table in your database
# DROP TABLE IF EXISTS user;
# CREATE TABLE user (id serial PRIMARY KEY, name VARCHAR(50), email VARCHAR(50)); 

def all():
  cursor.execute("SELECT * FROM user;")
  rows = cursor.fetchall()
  print("Read", cursor.rowcount,"row(s) of data.")
  return str(rows)

def add(name, email):
  print(name, email)
  cursor.execute("INSERT INTO user (name, email) VALUES (%s, %s);", (name, email))
  conn.commit()
  print("Inserted", cursor.rowcount,"row(s) of data.")
  return "OK"