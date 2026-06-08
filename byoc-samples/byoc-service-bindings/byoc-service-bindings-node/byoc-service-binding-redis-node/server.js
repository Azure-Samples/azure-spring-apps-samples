const redis = require('./redis.js')

const http = require('http')
const port = 1025

const express = require('express');
var app = express();

app.get('/setAndGetFoo', async (req, res) => {
  try {
    const result = await redis.setAndGetFoo().then((value) => {
      res.send(value)
    })
  } catch (err) {
    console.log(err)
  }
})

var server = app.listen(port, function () {
   var host = server.address().address
   var port = server.address().port
   console.log("Example app listening at http://%s:%s", host, port)
})
