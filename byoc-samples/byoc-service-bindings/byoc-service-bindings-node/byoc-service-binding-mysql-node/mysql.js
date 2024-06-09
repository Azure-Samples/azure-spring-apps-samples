const mysql = require('mysql');

var config =
{
    host: process.env.AZURE_DATASOURCE_HOST,
    database: process.env.AZURE_DATASOURCE_DATABASE,
    user: process.env.SPRING_DATASOURCE_USERNAME,
    password: process.env.SPRING_DATASOURCE_PASSWORD,
    port: 3306,
    ssl: true
}

const conn = new mysql.createConnection(config)

conn.connect(
    function (err) { 
    if (err) { 
        console.log("!!! Cannot connect !!! Error: " + err);
        throw err;
    }
    else
    {
       console.log("Connection established.")
    }
})

/*
 Pre create table in your database
 DROP TABLE IF EXISTS user;
 CREATE TABLE user (id serial PRIMARY KEY, name VARCHAR(50), email VARCHAR(50));
 */

function all(res){ 
    conn.query('SELECT * FROM user', 
        function (err, results, fields) {
            if (err) throw err;
            else console.log('Selected ' + results.length + ' row(s).');
            console.log(results);
            res.send(results)
        })
}

function add(name, email){
    conn.query('INSERT INTO user (name, email) VALUES (?, ?);', [name, email], 
    function (err, results, fields) {
                if (err) throw err;
        console.log('Inserted ' + results.affectedRows + ' row(s).');
        })
}

module.exports = { all, add }