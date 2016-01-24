var mysql = require('mysql');
var properties = require('./properties');
var connection = mysql.createConnection(properties.dbprops);

connection.connect(function (err) {
});

function getwalletstate(id,userHandler) {
    connection.query('SELECT id,flat,current,modificationDate,user FROM wallet WHERE flat='+id, function (err, rows, fields) {
        if (!err) {
            //console.log(rows);
            userHandler(rows[0]);
        }
        else
            console.log('Error while performing Query.' + err);
    });
}

function updatewallet(json, userHandler) {
    var date = new Date().getTime();
    var a = 'UPDATE wallet SET ' +
        'current=' + json.current + ',' +
        'user="' + json.user +'",'+
        'modificationDate=' + date +''+
        ' WHERE id=' + json.id;
    //console.log(a);
    connection.query(a, function (err, rows, fields) {
        if (!err) {
            userHandler();
            console.log("Updated wallet id=" + json.id);
        }
        else
            console.log('Error while performing Query.' + err);

    });
}
function getwalletdate(id,userHandler) {
    connection.query('SELECT id,modificationDate from wallet WHERE id=' + id, function (err, rows, fields) {
        if (!err) {
            userHandler(rows[0]);
        }
        else
            console.log('Error while performing Query.' + err);
    });
}



exports.getwalletstate = getwalletstate;
exports.updatewallet = updatewallet;
exports.getwalletdate = getwalletdate;
