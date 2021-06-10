import redis
import os

r = redis.Redis(host = os.environ['SPRING_REDIS_HOST'], 
    port = os.environ['SPRING_REDIS_PORT'],
    password = os.environ['SPRING_REDIS_PASSWORD'],
    ssl = os.environ['SPRING_REDIS_SSL'])

def setAndGetFoo():
    r.set("foo", "bar")
    return r.get("foo")
    