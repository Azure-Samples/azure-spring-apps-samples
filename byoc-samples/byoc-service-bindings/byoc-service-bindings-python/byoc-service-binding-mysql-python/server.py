from flask import Flask, request
import mysqlConnector as mysqlConn

app = Flask(__name__)

@app.route("/all", methods = ['GET'])
def all():
   try:
      return mysqlConn.all()
   except Exception as e:
      print(e)
      return e.__str__()       

@app.route("/add", methods = ['POST'])
def add():
   try:
      return mysqlConn.add(request.args['name'], request.args['email'])
   except Exception as e:
      print(e)
      return e.__str__()   

if __name__ == "__main__":
   app.run(port = 1025)