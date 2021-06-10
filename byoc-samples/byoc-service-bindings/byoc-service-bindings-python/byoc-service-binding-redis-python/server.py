from flask import Flask, request
import redisTemplate as redis

app = Flask(__name__)

@app.route("/setAndGetFoo")
def setAndGetFoo():
      try:
         return redis.setAndGetFoo()
      except Exception as e:
        print(e)
        return e.__str__()       


if __name__ == "__main__":
   app.run(port = 1025)