const redis = require("redis");
const bluebird = require("bluebird");

// Convert Redis client API to use promises, to make it usable with async/await syntax
bluebird.promisifyAll(redis.RedisClient.prototype);
bluebird.promisifyAll(redis.Multi.prototype);

const configuration = {
    host: process.env.SPRING_REDIS_HOST,
    port: process.env.SPRING_REDIS_PORT,
    auth_pass: process.env.SPRING_REDIS_PASSWORD,
    tls: {
        servername: process.env.SPRING_REDIS_HOST
    }
}

const client  = redis.createClient(configuration);  
    
client.on('error', err => {
    console.log(err)
});

async function setAndGetFoo() {
    client.set('foo', 'bar', (err, reply) => {
        if (err) throw err
        console.log(reply)
    })
    
    const result = await client.getAsync('foo')
    return result
}

module.exports = { setAndGetFoo }