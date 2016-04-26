var productsDB = require('./../db/products');
var usersDB = require('./../db/users');
var archiveDB = require('./../db/archive');
var statsDB = require('./../db/stats');
var authenticator = require('./../utils/authenticator');
function runREST(app) {
    //getflatproducts
    app.get('/products/flat/:id/user/:userID/filter/:filter/page/:page', function (req, res) {
        var flatID = req.params.id;
        var userID = req.params.userID;
        var filter = req.params.filter;
        var page = req.params.page;
        var authenticated = authenticator.authenticateUsingToken(req);
        if (authenticated) {
            authenticator.getLoggedUserID(req, function (id) {
                usersDB.isFlatMember(id, flatID, function (rows) {
                    if (rows[0].id != null) {
                        productsDB.getProducts(flatID, userID, filter, page, function (rows) {
                            res.send(rows);
                        })
                    } else {
                        res.sendStatus(403);
                    }
                });
            });
        } else {
            res.sendStatus(401)
        }
    });
    //removeFromMainList
    app.delete('/products/:id/mainlist', function (req, res) {
        var productID = req.params.id;
        var authenticated = authenticator.authenticateUsingToken(req);
        if (authenticated) {
            authenticator.getLoggedUserID(req, function (id) {
                productsDB.getProduct(productID, function (rows) {
                    if (rows.length == 0) {
                        res.sendStatus(404);
                    } else {
                        if (rows[0].creator == id) {
                            productsDB.deleteFromMainList(productID, function (rows) {
                                res.sendStatus(200);
                            });
                        } else {
                            res.sendStatus(403)
                        }
                    }
                });
            });
        } else {
            res.sendStatus(401)
        }
    });
    //updateProduct
    app.put('/products/:id', function (req, res) {
        var product = JSON.parse(req.body.product);
        var authenticated = authenticator.authenticateUsingToken(req);
        if (authenticated) {
            authenticator.getLoggedUserID(req, function (id) {
                if (product.creator == id) {
                    productsDB.getproductdate(req.params.id, function (row) {
                        if (row == undefined) {
                            res.sendStatus(404);
                        } else if (row.modificationDate > product.modificationDate) {
                            res.sendStatus(409);
                        } else {
                            productsDB.update(product, function (rows) {
                                res.sendStatus(200)
                            });
                        }
                    });
                } else {
                    res.sendStatus(403);
                }
            });
        } else {
            res.sendStatus(401)
        }
    });
    //addProduct
    app.post('/products/', function (req, res) {
        var json = JSON.parse(req.body.product);
        var authenticated = authenticator.authenticateUsingToken(req);
        if (authenticated) {
            authenticator.getLoggedUserID(req, function (id) {
                usersDB.isFlatMember(id, json.flat, function (rows) {
                    if (rows[0].id != null) {
                        productsDB.addProduct(json, function (rows) {
                            res.send("" + rows[0]['LAST_INSERT_ID()'])
                        });
                    } else {
                        res.sendStatus(403);
                    }
                });
            });
        } else {
            res.sendStatus(401)
        }
    });
    //reserveProduct
    app.post('/products/:id/userlist', function (req, res) {
        var product = JSON.parse(req.body.product);
        var authenticated = authenticator.authenticateUsingToken(req);
        if (authenticated) {
            authenticator.getLoggedUserID(req, function (id) {
                usersDB.isFlatMember(id, product.flat, function (rows) {
                    if (rows[0].id != null) {
                        productsDB.getproductuser(product.id, function (rows) {
                            if(rows == undefined){
                                res.sendStatus(404);
                            } else if(rows.user != null && rows.user!=0){
                                res.sendStatus(409)
                            } else{
                                productsDB.reserve(product,id,function(rows){
                                    res.sendStatus(200);
                                });
                            }
                        });

                    } else {
                        res.sendStatus(403);
                    }
                });
            });
        } else {
            res.sendStatus(401)
        }
    });
    //buyProduct
    app.post('/products/:id/purchase', function (req, res) {
        var product = JSON.parse(req.body.product);
        var authenticated = authenticator.authenticateUsingToken(req);
        if (authenticated) {
            authenticator.getLoggedUserID(req, function (id) {
                if(product.user!=id && product.user!=0 && product.user!= undefined){
                    res.sendStatus(403);
                } else {
                    productsDB.getproductdate(req.params.id, function (row) {
                        if (row == undefined) {
                            res.sendStatus(404);
                        } else if (row.modificationDate > product.modificationDate) {
                            res.sendStatus(409);
                        } else {
                            if(product.done==0){
                                productsDB.deleteFromMainList(product.id,function(rows){
                                    product.user=id;
                                    archiveDB.addProduct(product,function(rows){
                                        statsDB.getValue(0,product.flat,function(rows){
                                            statsDB.setValue(0,product.flat,rows.sum+product.price,function(rows){

                                            });
                                        });
                                        statsDB.getValue(product.user,product.flat,function(rows){
                                            statsDB.setValue(product.user,product.flat,rows.sum+product.price,function(rows){
                                                    res.sendStatus(200);
                                            });
                                        });
                                    });
                                });
                            } else {
                                res.sendStatus(409);
                            }
                        }
                    });
                }
            });
        } else {
            res.sendStatus(401)
        }
    });
    //unreserveProduct
    app.delete('/products/:id/userlist', function (req, res) {
        var authenticated = authenticator.authenticateUsingToken(req);
        if (authenticated) {
            authenticator.getLoggedUserID(req, function (id) {
                productsDB.getproductuser(req.params.id, function (rows) {
                    if(rows == undefined){
                        res.sendStatus(404);
                    } else if(rows.user == null || rows.user==0){
                        //TODO: now unreserving unreserved product is ok
                        res.sendStatus(200);
                    }else if(rows.user!=id){
                        res.sendStatus(403);
                    } else{
                        productsDB.unreserve(req.params.id,function(rows){
                            res.sendStatus(200);
                        });
                    }
                });
            });
        } else {
            res.sendStatus(401)
        }
    });
    //cancelBuy
    app.delete('/products/:id/purchase', function (req, res) {
        var authenticated = authenticator.authenticateUsingToken(req);
        if (authenticated) {
            authenticator.getLoggedUserID(req, function (id) {
                archiveDB.getproductuser(req.params.id, function (rows) {
                    if(rows == undefined){
                        res.sendStatus(404);
                    } else if(rows.user == null || rows.user==0){
                        res.sendStatus(409);
                    }else if(rows.user!=id){
                        res.sendStatus(403);
                    } else{
                        // get product
                        archiveDB.getProduct(req.params.id,function(rows){
                            var product = rows[0];
                            product.done=0;
                            productsDB.readdProduct(product,function(rows){
                                archiveDB.removeProduct(req.params.id,function(rows){
                                    statsDB.getValue(0,product.flat,function(rows){
                                        statsDB.setValue(0,product.flat,rows.sum-product.price,function(rows){

                                        })
                                    });
                                    statsDB.getValue(product.user,product.flat,function(rows){
                                        statsDB.setValue(product.user,product.flat,rows.sum-product.price,function(rows){
                                            res.sendStatus(200);
                                        })
                                    });
                                });
                            })
                        });
                    }
                });
            });
        } else {
            res.sendStatus(401)
        }
    });
}
exports.runREST = runREST;