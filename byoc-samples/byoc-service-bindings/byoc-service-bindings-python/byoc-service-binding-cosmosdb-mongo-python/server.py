from flask import Flask, request
import mongo as mongoConn

app = Flask(__name__)

@app.route("/all", methods = ['GET'])
def all():
   try:
      return mongoConn.all()
   except Exception as e:
      print(e)
      return e.__str__()       

@app.route("/add", methods = ['POST'])
def add():
   try:
      return mongoConn.add(request.args['name'])
   except Exception as e:
      print(e)
      return e.__str__()   

if __name__ == "__main__":
   app.run(port = 1025)