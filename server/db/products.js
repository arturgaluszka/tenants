var mysql = require('mysql');
var properties = require('./properties');
var connection = mysql.createConnection(properties.dbprops);
var restproperties = require('./../rest/properties');

connection.connect(function (err) {
});

function addProduct(product, callback) {
    var a = 'INSERT INTO products (done,price,description,flat,user,creator,modificationDate)' +
        'VALUES (' + product.done + ',' + product.price + ',"' + product.description + '",' + product.flat + ',' + product.user +
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
function deleteFromMainList(id, callback) {
    connection.query('DELETE FROM products WHERE id=' + id, function (err, rows, fields) {
        if (!err) {
            callback(rows);
        }
        else
            console.log('Error while performing Query.' + err);
    });
}
function getProduct(id, callback) {
    connection.query('SELECT * FROM products WHERE id=' + id, function (err, rows, fields) {
        if (!err) {
            callback(rows);
        }
        else
            console.log('Error while performing Query.' + err);
    });
}
function getProducts(flatID, userID, filter, page, callback) {
    var pagesize = restproperties.restproperties.pageSize;
    var query = 'SELECT * FROM products WHERE flat=' + flatID;
    if (userID && userID!=0) {
        query += " AND user=" + userID;
    }
    if (filter == 2) {
        query += " AND user is null"
    } else if (filter == 3) {
        query += " AND user is not null"
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
function update(product, callback) {
    var date = new Date().getTime();
    var a = 'UPDATE products SET ' +
        'description="' + product.description + '", ' +
        'done=' + product.done + ',' +
        'price=' + product.price +','+
        'modificationDate=' + date +
        ' WHERE id=' + product.id;
    connection.query(a, function (err, rows, fields) {
        if (!err) {
            callback();
            console.log("Updated product id=" + product.id);
        }
        else
            console.log('Error while performing Query.' + err);

    });
}
function reserve(product,id, callback) {
    var date = new Date().getTime();
    var a = 'UPDATE products SET ' +
        'user=' + id +','+
        'modificationDate=' + date +
        ' WHERE id=' + product.id;
    connection.query(a, function (err, rows, fields) {
        if (!err) {
            callback();
            console.log("reserved product id=" + product.id);
        }
        else
            console.log('Error while performing Query.' + err);

    });
}
function getproductdate(id, userHandler) {
    connection.query('SELECT id,modificationDate from products WHERE id=' + id, function (err, rows, fields) {
        if (!err) {
            userHandler(rows[0]);
        }
        else
            console.log('Error while performing Query.' + err);
    });
}
function getproductuser(id, userHandler) {
    connection.query('SELECT id,user from products WHERE id=' + id, function (err, rows, fields) {
        if (!err) {
            userHandler(rows[0]);
        }
        else
            console.log('Error while performing Query.' + err);
    });
}
function unreserve(productID, callback) {
    var date = new Date().getTime();
    var a = 'UPDATE products SET ' +
        'user=' + 0 +','+
        'modificationDate=' + date +
        ' WHERE id=' + productID;
    connection.query(a, function (err, rows, fields) {
        if (!err) {
            callback();
            console.log("unreserved product id=" + productID);
        }
        else
            console.log('Error while performing Query.' + err);

    });
}
function readdProduct(product, callback) {
    var a = 'INSERT INTO products (id,done,price,description,flat,user,creator,modificationDate)' +
        'VALUES ('+product.id+', ' + product.done + ',' + product.price + ',"' + product.description + '",' + product.flat + ',' + product.user +
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


exports.addProduct = addProduct;
exports.deleteFromMainList = deleteFromMainList;
exports.getProduct = getProduct;
exports.getProducts = getProducts;
exports.update = update;
exports.getproductdate = getproductdate;
exports.reserve = reserve;
exports.getproductuser = getproductuser;
exports.unreserve = unreserve;
exports.readdProduct=readdProduct;
