const data = require('./mongo.js')

const http = require('http')
const port = 1025

const express = require('express');
var app = express();

app.get('/all', (req, res) => {
  try {
    data.all(res);
  } catch (err) {
    console.log(err)
  }
})

app.post('/add', (req, res) => {
  try {
    res.send(data.add(req.query.name))
  } catch (err) {
    console.log(err)
  }
})

var server = app.listen(port, function () {
   var host = server.address().address
   var port = server.address().port
   console.log("Example app listening at http://%s:%s", host, port)
})
