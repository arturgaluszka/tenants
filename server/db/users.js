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
    connection.query('SELECT id FROM flatsigns WHERE userID='+userID, function (err, rows, fields) {
        if (!err) {
            callback(rows);
        }
        else
            console.log('Error while performing Query.' + err);
    });
}
//function getexpenses(id,userHandler) {
//    connection.query('SELECT id,price,description,done,flat,user FROM expenses WHERE flat='+id, function (err, rows, fields) {
//        if (!err) {
//            //console.log(rows);
//            userHandler(rows);
//        }
//        else
//            console.log('Error while performing Query.' + err);
//    });
//}
//
//function updateexpense(json, userHandler) {
//    var date = new Date().getTime();
//    var a = 'UPDATE expenses SET ' +
//        'price=' + json.price + ',' +
//        'description="' + json.description + '",' +
//        'done=' + json.done +','+
//        'user="' + json.user +'",'+
//        'modificationDate=' + date +''+
//        ' WHERE id=' + json.id;
//    console.log(a);
//    connection.query(a, function (err, rows, fields) {
//        if (!err) {
//            userHandler();
//            console.log("Updated expense id=" + json.id);
//        }
//        else
//            console.log('Error while performing Query.' + err);
//
//    });
//}
//function newexpense(json, userHandler) {
//    var a = 'INSERT INTO expenses (price,description,done,flat,user)' +
//        'VALUES (' + json.price + ',"' + json.description + '",' + json.done + ', ' + json.flat + ', "' + json.user + '")';
//    var b = 'SELECT LAST_INSERT_ID()';
//    //console.log(a);
//    //console.log(b);
//    connection.query(a, function (err, rows, fields) {
//        if (!err) {
//            //userHandler(rows);
//        }
//        else
//            console.log('Error while performing Query.' + err);
//    });
//    connection.query(b, function (err, rows, fields) {
//        if (!err) {
//            userHandler(rows);
//        }
//        else
//            console.log('Error while performing Query.' + err);
//    });
//}
//
//function getexpense(id, userHandler) {
//    connection.query('SELECT * from expenses WHERE id=' + id, function (err, rows, fields) {
//        if (!err) {
//            //console.log(rows);
//            userHandler(rows[0]);
//        }
//        else
//            console.log('Error while performing Query.' + err);
//    });
//}
//
//function deleteexpense(id, userHandler) {
//    connection.query('DELETE FROM expenses WHERE id='+id, function (err, rows, fields) {
//        if (!err) {
//            console.log('Deleted expense id='+id);
//            userHandler(rows);
//        }
//        else
//            console.log('Error while performing Query.' + err);
//    });
//}
//
//function getexpensedate(id,userHandler) {
//    connection.query('SELECT id,modificationDate from expenses WHERE id=' + id, function (err, rows, fields) {
//        if (!err) {
//            userHandler(rows[0]);
//        }
//        else
//            console.log('Error while performing Query.' + err);
//    });
//}
//function getdonecostsum(id,userhandler){
//    connection.query('SELECT sum(price) FROM expenses WHERE done=1 and flat='+id,function(err,rows,fields){
//        if(!err){
//            userhandler(rows[0]);
//        } else{
//            console.log("Error while performing Query."+ err);
//        }
//    });
//}

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

//
//exports.getexpenses = getexpenses;
//exports.updateexpense = updateexpense;
//exports.newexpense = newexpense;
//exports.getexpense = getexpense;
//exports.deleteexpense = deleteexpense;
//exports.getexpensedate = getexpensedate;
//exports.getdonecostsum = getdonecostsum;