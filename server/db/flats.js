var mysql = require('mysql');
var properties = require('./properties');
var connection = mysql.createConnection(properties.dbprops);

connection.connect(function (err) {
});

function createFlat(flatPassword,name, callback) {
    var a = 'INSERT INTO flats (password,name)' +
        'VALUES ("' + flatPassword + '","'+name+'")';
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
function getMembers(flatID, callback) {
    connection.query('SELECT userID FROM flatsigns WHERE flatID=' + flatID, function (err, rows, fields) {
        if (!err) {
            callback(rows);
        }
        else
            console.log('Error while performing Query.' + err);
    });
}
function changePassword(flatID, newPassword, callback) {
    var a = 'UPDATE flats SET ' +
        'password="' + newPassword + '"' +
        ' WHERE id=' + flatID;
    connection.query(a, function (err, rows, fields) {
        if (!err) {
            callback();
            console.log("Updated user id=" + flatID);
        }
        else
            console.log('Error while performing Query.' + err);

    });
}
function matchPasswordForFlat(flatID,callback){
    connection.query('SELECT password FROM flats WHERE id='+flatID,function(err,rows,fields){
        if (!err) {
            callback(rows);
        }
        else
            console.log('Error while performing Query.' + err);
    });
}
function getFlatID(name,callback){
    connection.query('SELECT id FROM flats WHERE name="'+name+'"', function (err, rows, fields) {
        if (!err) {
            callback(rows);
        }
        else
            console.log('Error while performing Query.' + err);
    });
}
function getFlatName(flatID,callback){
    connection.query('SELECT name FROM flats WHERE id='+flatID+'', function (err, rows, fields) {
        if (!err) {
            callback(rows);
        }
        else
            console.log('Error while performing Query.' + err);
    });
}

exports.createFlat = createFlat;
exports.getMembers = getMembers;
exports.changePassword = changePassword;
exports.matchPasswordForFlat=matchPasswordForFlat;
exports.getFlatID=getFlatID;
exports.getflatName=getFlatName;