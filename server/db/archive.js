var mysql = require('mysql');
var properties = require('./properties');
var connection = mysql.createConnection(properties.dbprops);
var restproperties = require('./../rest/properties');

connection.connect(function (err) {
});

function addProduct(product, callback) {
    var a = 'INSERT INTO archive (id,done,price,description,flat,user,creator,modificationDate)' +
        'VALUES ('+product.id+',' + 1 + ',' + product.price + ',"' + product.description + '",' + product.flat + ',' + product.user +
        ',' + product.creator + ',' + new Date().getTime() + ')';
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
function removeProduct(id, callback) {
    connection.query('DELETE FROM archive WHERE id=' + id, function (err, rows, fields) {
        if (!err) {
            callback(rows);
        }
        else
            console.log('Error while performing Query.' + err);
    });
}
function getProduct(id, callback) {
    connection.query('SELECT * FROM archive WHERE id=' + id, function (err, rows, fields) {
        if (!err) {
            callback(rows);
        }
        else
            console.log('Error while performing Query.' + err);
    });
}
function getProducts(flatID, userID, filter, page, callback) {
    var pagesize = restproperties.restproperties.pageSize;
    var query = 'SELECT * FROM archive WHERE flat=' + flatID;
    if (userID && userID!=0) {
        query += " AND user=" + userID;
    }
    query += " LIMIT " + pagesize * page;
    connection.query(query, function (err, rows, fields) {
        if (!err) {
            callback(rows.slice((page - 1) * pagesize, page * pagesize));
        }
        //else
        //    console.log('Error while performing Query.' + err);
    });
}
function getproductuser(id, userHandler) {
    connection.query('SELECT id,user from archive WHERE id=' + id, function (err, rows, fields) {
        if (!err) {
            userHandler(rows[0]);
        }
        else
            console.log('Error while performing Query.' + err);
    });
}
exports.addProduct=addProduct;
exports.removeProduct=removeProduct;
exports.getProduct = getProduct;
exports.getProducts = getProducts;
exports.getproductuser= getproductuser;