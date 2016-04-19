var mysql = require('mysql');
var properties = require('./properties');
var connection = mysql.createConnection(properties.dbprops);

connection.connect(function (err) {
});
function matchPasswordForUser(username,password,callback){
    connection.query('SELECT password FROM users WHERE username="'+username+'"',function(err,rows,fields){
        if (!err) {
            callback(rows);
        }
        else
            console.log('Error while performing Query.' + err);
    });
}
function matchPasswordForFlat(flatID,password,callback){
    connection.query('SELECT password FROM flats WHERE id='+flatID,function(err,rows,fields){
        if (!err) {
            callback(rows);
        }
        else
            console.log('Error while performing Query.' + err);
    });
}
function signUserToFlat(userID,flatID,callback){
    var a = 'INSERT INTO flatsigns (userID,flatID)' +
        'VALUES ('+userID+','+flatID+')';
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
function moveOutUser(userID,flatID,callback){
    connection.query('DELETE FROM flatsigns WHERE userID='+userID+" and flatID="+flatID , function (err, rows, fields) {
        if (!err) {
            callback(rows);
        }
        else
            console.log('Error while performing Query.' + err);
    });
}
function getUserID(username,callback){
    connection.query('SELECT id FROM users WHERE username="'+username+'"', function (err, rows, fields) {
        if (!err) {
            callback(rows);
        }
        else
            console.log('Error while performing Query.' + err);
    });
}
function createUser(username, password,callback){
    var a = 'INSERT INTO users (username,password)' +
        'VALUES ("'+username+'","'+password+'")';
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
function changePassword(userID, newPassword,callback){
    var a = 'UPDATE users SET ' +
        'password="' + newPassword+ '"'+
        ' WHERE id=' + userID;
    connection.query(a, function (err, rows, fields) {
        if (!err) {
            callback();
            console.log("Updated user id=" + userID);
        }
        else
            console.log('Error while performing Query.' + err);

    });
}
function getUserName(userID,callback){
    connection.query('SELECT username FROM users WHERE id='+userID+'', function (err, rows, fields) {
        if (!err) {
            callback(rows);
        }
        else
            console.log('Error while performing Query.' + err);
    });
}
function isFlatMember(userID, flatID,callback){
    connection.query('SELECT id FROM flatsigns WHERE userID='+userID+' and flatID='+flatID, function (err, rows, fields) {
        if (!err) {
            callback(rows);
        }
        else
            console.log('Error while performing Query.' + err);
    });
}
function getUserFlats(userID,callback){
    connection.query('SELECT flatID FROM flatsigns WHERE userID='+userID, function (err, rows, fields) {
        if (!err) {
            callback(rows);
        }
        else
            console.log('Error while performing Query.' + err);
    });
}

exports.matchPasswordForUser = matchPasswordForUser;
exports.matchPasswordForFlat = matchPasswordForFlat;
exports.signUserToFlat = signUserToFlat;
exports.moveOutUser = moveOutUser;
exports.getUserID = getUserID;
exports.createUser = createUser;
exports.getUsername = getUserName;
exports.changePassword = changePassword;
exports.isFlatMember = isFlatMember;
exports.getUserFlats = getUserFlats;
