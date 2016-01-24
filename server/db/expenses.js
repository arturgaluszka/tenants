var mysql = require('mysql');
var properties = require('./properties');
var connection = mysql.createConnection(properties.dbprops);

connection.connect(function (err) {
});

function getexpenses(id,userHandler) {
    connection.query('SELECT id,price,description,done,flat,user FROM expenses WHERE flat='+id, function (err, rows, fields) {
        if (!err) {
            //console.log(rows);
            userHandler(rows);
        }
        else
            console.log('Error while performing Query.' + err);
    });
}

function updateexpense(json, userHandler) {
    var date = new Date().getTime();
    var a = 'UPDATE expenses SET ' +
        'price=' + json.price + ',' +
        'description="' + json.description + '",' +
        'done=' + json.done +','+
        'user="' + json.user +'",'+
        'modificationDate=' + date +''+
        ' WHERE id=' + json.id;
    console.log(a);
    connection.query(a, function (err, rows, fields) {
        if (!err) {
            userHandler();
            console.log("Updated expense id=" + json.id);
        }
        else
            console.log('Error while performing Query.' + err);

    });
}
function newexpense(json, userHandler) {
    var a = 'INSERT INTO expenses (price,description,done,flat,user)' +
        'VALUES (' + json.price + ',"' + json.description + '",' + json.done + ', ' + json.flat + ', "' + json.user + '")';
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

function getexpense(id, userHandler) {
    connection.query('SELECT * from expenses WHERE id=' + id, function (err, rows, fields) {
        if (!err) {
            //console.log(rows);
            userHandler(rows[0]);
        }
        else
            console.log('Error while performing Query.' + err);
    });
}

function deleteexpense(id, userHandler) {
    connection.query('DELETE FROM expenses WHERE id='+id, function (err, rows, fields) {
        if (!err) {
            console.log('Deleted expense id='+id);
            userHandler(rows);
        }
        else
            console.log('Error while performing Query.' + err);
    });
}

function getexpensedate(id,userHandler) {
    connection.query('SELECT id,modificationDate from expenses WHERE id=' + id, function (err, rows, fields) {
        if (!err) {
            userHandler(rows[0]);
        }
        else
            console.log('Error while performing Query.' + err);
    });
}

exports.getexpenses = getexpenses;
exports.updateexpense = updateexpense;
exports.newexpense = newexpense;
exports.getexpense = getexpense;
exports.deleteexpense = deleteexpense;
exports.getexpensedate = getexpensedate;