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
//
//function matchPasswordForUser(username,password,callback){
//    connection.query('SELECT password FROM users WHERE username="'+username+'"',function(err,rows,fields){
//        if (!err) {
//            callback(rows);
//        }
//        else
//            console.log('Error while performing Query.' + err);
//    });
//}
//function matchPasswordForFlat(flatID,password,callback){
//    connection.query('SELECT password FROM flats WHERE id='+flatID,function(err,rows,fields){
//        if (!err) {
//            callback(rows);
//        }
//        else
//            console.log('Error while performing Query.' + err);
//    });
//}
//function signUserToFlat(userID,flatID,callback){
//    var a = 'INSERT INTO flatsigns (userID,flatID)' +
//        'VALUES ('+userID+','+flatID+')';
//    var b = 'SELECT LAST_INSERT_ID()';
//
//    connection.query(a, function (err, rows, fields) {
//        if (!err) {
//            //userHandler(rows);
//        }
//        else
//            console.log('Error while performing Query.' + err);
//    });
//    connection.query(b, function (err, rows, fields) {
//        if (!err) {
//            callback(rows);
//        }
//        else
//            console.log('Error while performing Query.' + err);
//    });
//}
//function moveOutUser(userID,flatID,callback){
//    connection.query('DELETE FROM flatsigns WHERE userID='+userID+" and flatID="+flatID , function (err, rows, fields) {
//        if (!err) {
//            callback(rows);
//        }
//        else
//            console.log('Error while performing Query.' + err);
//    });
//}
//function getUserID(username,callback){
//    connection.query('SELECT id FROM users WHERE username="'+username+'"', function (err, rows, fields) {
//        if (!err) {
//            callback(rows);
//        }
//        else
//            console.log('Error while performing Query.' + err);
//    });
//}
//function createUser(username, password,callback){
//    var a = 'INSERT INTO users (username,password)' +
//        'VALUES ("'+username+'","'+password+'")';
//    var b = 'SELECT LAST_INSERT_ID()';
//
//    connection.query(a, function (err, rows, fields) {
//        if (!err) {
//            //userHandler(rows);
//        }
//        else
//            console.log('Error while performing Query.' + err);
//    });
//    connection.query(b, function (err, rows, fields) {
//        if (!err) {
//            callback(rows);
//        }
//        else
//            console.log('Error while performing Query.' + err);
//    });
//}
//function changePassword(userID, newPassword,callback){
//    var a = 'UPDATE users SET ' +
//        'password="' + newPassword+ '"'+
//        ' WHERE id=' + userID;
//    connection.query(a, function (err, rows, fields) {
//        if (!err) {
//            callback();
//            console.log("Updated user id=" + userID);
//        }
//        else
//            console.log('Error while performing Query.' + err);
//
//    });
//}
//function getUserName(userID,callback){
//    connection.query('SELECT username FROM users WHERE id='+userID+'', function (err, rows, fields) {
//        if (!err) {
//            callback(rows);
//        }
//        else
//            console.log('Error while performing Query.' + err);
//    });
//}
//function isFlatMember(userID, flatID,callback){
//    connection.query('SELECT id FROM flatsigns WHERE userID='+userID+' and flatID='+flatID, function (err, rows, fields) {
//        if (!err) {
//            callback(rows);
//        }
//        else
//            console.log('Error while performing Query.' + err);
//    });
//}
//
//exports.matchPasswordForUser = matchPasswordForUser;
//exports.matchPasswordForFlat = matchPasswordForFlat;
//exports.signUserToFlat = signUserToFlat;
//exports.moveOutUser = moveOutUser;
//exports.getUserID = getUserID;
//exports.createUser = createUser;
//exports.getUsername = getUserName;
//exports.changePassword = changePassword;
//exports.isFlatMember = isFlatMember;

exports.addProduct = addProduct;
exports.deleteFromMainList = deleteFromMainList;
exports.getProduct = getProduct;
exports.getProducts = getProducts;
exports.update = update;
exports.getproductdate = getproductdate;
exports.reserve = reserve;
exports.getproductuser = getproductuser;
exports.unreserve = unreserve;
