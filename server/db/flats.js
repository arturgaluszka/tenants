var mysql = require('mysql');
var properties = require('./properties');
var connection = mysql.createConnection(properties.dbprops);

connection.connect(function (err) {
});

function createFlat(flatPassword,callback){
    var a = 'INSERT INTO flats (password)' +
        'VALUES ("'+flatPassword+'")';
    var b = 'SELECT LAST_INSERT_ID()';

    connection.query(a, function (err, rows, fields) {
        if (!err) {
            //userHandler(rows);
        }
        else
            console.log('Error while performing Query.' + err);
    });
    connection.query(b, function (err, rows, fields) {
        if (!err) {
            callback(rows);
        }
        else
            console.log('Error while performing Query.' + err);
    });
}
function getMembers(flatID, callback){
    connection.query('SELECT userID FROM flatsigns WHERE flatID='+flatID, function (err, rows, fields) {
        if (!err) {
            callback(rows);
        }
        else
            console.log('Error while performing Query.' + err);
    });
}

exports.createFlat = createFlat;
exports.getMembers = getMembers;