var identity = require('./identity.js')

const http = require('http')
const port = 1025

const express = require('express');
var app = express();

app.get('/secrets/:name', async (req, res) => {
  try {
    const result = await identity.getSecret(req.params.name).then((value) => {
      res.send(value);
    })
  } catch (err) {
    console.log(err)
  }
})

app.put('/secrets/:name', async (req, res) => { 
  try {
    const result = await identity.setSecret(req.params.name, req.query.value).then((value) => {
      res.send(value);
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
