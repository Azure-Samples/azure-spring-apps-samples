const mongoose = require('mongoose');

mongoose.connect(process.env.SPRING_DATA_MONGODB_URI, {useNewUrlParser: true})

mongoose.connection.on("error", function(error) {
  console.log(error)
})

mongoose.connection.on("open", function() {
  console.log("Connected to MongoDB database.")
})

const userSchema = new mongoose.Schema({
  name: String
});
const User = mongoose.model('User', userSchema);

function all(res){ 
  User.find(function (err, users) {
    if (err) return console.error(err);
    console.log(users);
    res.send(users)
  })
}

function add(name){
  var myObj = new User({ name: name });
  console.log(myObj);
  myObj.save(function (err) {
    if (err) return console.error(err);
  });
}

module.exports = { all, add }