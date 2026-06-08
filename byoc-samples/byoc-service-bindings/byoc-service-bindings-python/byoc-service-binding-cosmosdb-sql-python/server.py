from flask import Flask, request
import cosmosSql as cosmos

app = Flask(__name__)

@app.route("/all", methods = ['GET'])
def all():
   try:
      return cosmos.all()
   except Exception as e:
      print(e)
      return e.__str__()       

@app.route("/add", methods = ['POST'])
def add():
   try:
      return cosmos.add(request.args['name'])
   except Exception as e:
      print(e)
      return e.__str__()   

if __name__ == "__main__":
   app.run(port = 1025)