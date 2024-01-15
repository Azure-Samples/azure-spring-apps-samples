from flask import Flask, request
import identity as identity

app = Flask(__name__)

@app.route("/secrets/<name>", methods = ['PUT', 'GET'])
def secret(name):
      try:
         if request.method == 'GET':
            return identity.getSecret(name)
         else :
            value = request.args['value']
            return identity.setSecret(name, value)
      except Exception as e:
        print(e)
        return e.__str__()       


if __name__ == "__main__":
   app.run(port = 1025)