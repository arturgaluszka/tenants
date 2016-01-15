var mysql = require('mysql');
var properties = require('./properties');
var connection = mysql.createConnection(properties.dbprops);

connection.connect(function (err) {
});

function gettodos(id,userHandler) {
    connection.query('SELECT id,priority,message,flat,user FROM todo WHERE flat='+id, function (err, rows, fields) {
        if (!err) {
            //console.log(rows);
            userHandler(rows);
        }
        else
            console.log('Error while performing Query.' + err);
    });
}

function updatetodo(json, userHandler) {
    var date = new Date().getTime();
    var a = 'UPDATE todo SET ' +
        'priority="' + json.priority + '",' +
        'message="' + json.message + '",' +
        'user="' + json.user +'",'+
        'modificationDate=' + date +''+
        ' WHERE id=' + json.id;
    //console.log(a);
    connection.query(a, function (err, rows, fields) {
        if (!err) {
            userHandler();
            console.log("Updated todo id=" + json.id);
        }
        else
            console.log('Error while performing Query.' + err);

    });
}
function newtodo(json, userHandler) {
    var a = 'INSERT INTO todo (priority,message,flat,user)' +
        'VALUES ("' + json.priority + '","' + json.message + '",' + json.flat + ', "' + json.user + '")';
    var b = 'SELECT LAST_INSERT_ID()';
    //console.log(a);
    //console.log(b);
    connection.query(a, function (err, rows, fields) {
        if (!err) {
            //userHandler(rows);
        }
        else
            console.log('Error while performing Query.' + err);
    });
    connection.query(b, function (err, rows, fields) {
        if (!err) {
            userHandler(rows);
        }
        else
            console.log('Error while performing Query.' + err);
    });
}

function gettodo(id, userHandler) {
    connection.query('SELECT * from todo WHERE id=' + id, function (err, rows, fields) {
        if (!err) {
            //console.log(rows);
            userHandler(rows[0]);
        }
        else
            console.log('Error while performing Query.' + err);
    });
}

function deletetodo(id, userHandler) {
    connection.query('DELETE FROM todo WHERE id='+id, function (err, rows, fields) {
        if (!err) {
            console.log('Deleted todo id='+id);
            userHandler(rows);
        }
        else
            console.log('Error while performing Query.' + err);
    });
}

function gettododate(id,userHandler) {
    connection.query('SELECT id,modificationDate from todo WHERE id=' + id, function (err, rows, fields) {
        if (!err) {
            userHandler(rows[0]);
        }
        else
            console.log('Error while performing Query.' + err);
    });
}

exports.gettodos = gettodos;
exports.updatetodo = updatetodo;
exports.newtodo = newtodo;
exports.gettodo = gettodo;
exports.deletetodo = deletetodo;
exports.gettododate = gettododate;