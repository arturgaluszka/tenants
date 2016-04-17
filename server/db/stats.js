var mysql = require('mysql');
var properties = require('./properties');
var connection = mysql.createConnection(properties.dbprops);
var restproperties = require('./../rest/properties');

connection.connect(function (err) {
});

function addStatsField(userID,flatID, callback) {
    var a = 'INSERT INTO stats (userID,flatID)' +
        'VALUES (' +userID+','+flatID+ ')';
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
function setValue(userID,flatID,value, callback) {
    var a = 'UPDATE stats SET ' +
        'sum=' + value + ' ' +
        ' WHERE userID='+userID+" and flatID=" + flatID;
    connection.query(a, function (err, rows, fields) {
        if (!err) {
            callback();
        }
        else
            console.log('Error while performing Query.' + err);

    });
}
function getValue(userID,flatID, userHandler) {
    connection.query('SELECT id,sum from stats WHERE flatID=' + flatID+" and userID="+userID, function (err, rows, fields) {
        if (!err) {
            userHandler(rows[0]);
        }
        else
            console.log('Error while performing Query.' + err);
    });
}

exports.addStatsField=addStatsField;
exports.getValue=getValue;
exports.setValue=setValue;