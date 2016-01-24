/**
 * Created by Dante on 2016-01-24.
 */
var mysql = require('mysql');
var properties = require('./properties');
var connection = mysql.createConnection(properties.dbprops);

connection.connect(function (err) {
});

function getwalletHistorystate(id, userHandler) {
    connection.query('SELECT id,user,typeOfOperation,amount,dateOfOperation FROM walletHistory WHERE flat=' + id, function (err, rows, fields) {
        if (!err) {
            //console.log(rows);
            userHandler(rows[0]);
        }
        else
            console.log('Error while performing Query.' + err);
    });
}

function updatewalletHistory(json, userHandler) {
    var date = new Date().getTime();
    var a = 'UPDATE walletHistory SET ' +
        'typeOfOperation=' + json.typeOfOperation + ',' +
        'amount=' + json.amount + ',' +
        'user="' + json.user + '",' +
        'dateOfOperation=' + date + '' +
        ' WHERE id=' + json.id;
    //console.log(a);
    connection.query(a, function (err, rows, fields) {
        if (!err) {
            userHandler();
            console.log("Updated walletHistory id=" + json.id);
        }
        else
            console.log('Error while performing Query.' + err);

    });
}
function getwalletHistorydate(id, userHandler) {
    connection.query('SELECT id,modificationDate from walletHistory WHERE id=' + id, function (err, rows, fields) {
        if (!err) {
            userHandler(rows[0]);
        }
        else
            console.log('Error while performing Query.' + err);
    });
}


exports.getwalletHistorystate = getwalletHistorystate;
exports.updatewalletHistory = updatewalletHistory;
exports.getwalletHistorydate = getwalletHistorydate;
