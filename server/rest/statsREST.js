<<<<<<< HEAD
var flatsDB = require('./../db/flats');
var authenticator = require('./../utils/authenticator');
var usersDB = require('./../db/users');
var statsDB = require('./../db/stats');
function runREST(app) {
    app.post('/flats/', function (req, res) {
        var authenticated = authenticator.authenticateUsingToken(req);
        if (authenticated) {
            var password = req.body.password;
            var name = req.body.name;
            flatsDB.createFlat(password,name, function (rows) {
                var newID = rows[0]['LAST_INSERT_ID()'];
                statsDB.addStatsField(0,newID,function(rows){

                });
                res.send(newID.toString());
            });
        } else {
            res.sendStatus(401);
        }
    });
    app.get('/flats/:flatID/users', function (req, res) {
=======
var statsDB = require('./../db/stats');
var archiveDB = require('./../db/archive');
var usersDB = require('./../db/users');
var authenticator = require('./../utils/authenticator');
function runREST(app) {
    //getStats
    app.get('/stats/user/:userID/flat/:flatID', function (req, res) {
>>>>>>> e1a3dda09abc260a54f0847185cef34790a24c01
        var authenticated = authenticator.authenticateUsingToken(req);
        if (authenticated) {
            authenticator.getLoggedUserID(req, function (id) {
                usersDB.isFlatMember(id, req.params.flatID, function (rows) {
<<<<<<< HEAD
                    if (rows[0].id != null) {
                        flatsDB.getMembers(req.params.flatID, function (rows) {
                            res.send(rows.map(function (row) {
                                return row.userID;
                            }));
                        })
=======
                    if (rows.length>0 && rows[0].id != null) {
                        statsDB.getValue(req.params.userID, req.params.flatID, function (rows) {
                            var stats = rows;
                            stats.user = parseInt(req.params.userID);
                            stats.flat = parseInt(req.params.flatID);
                            res.send(stats);
                        });
>>>>>>> e1a3dda09abc260a54f0847185cef34790a24c01
                    } else {
                        res.sendStatus(403);
                    }
                });
            });
<<<<<<< HEAD

=======
>>>>>>> e1a3dda09abc260a54f0847185cef34790a24c01
        } else {
            res.sendStatus(401)
        }
    });
<<<<<<< HEAD
    app.put('/flats/:id/password', function (req, res) {
        var flatID = req.params.id;
        var oldPassword = req.body.oldPassword;
        var newPassword = req.body.newPassword;
        flatsDB.matchPasswordForFlat(flatID, function (rows) {
            if(rows.length==0){
                res.send(404);
            }
            if (oldPassword == rows[0].password) {
                flatsDB.changePassword(flatID,newPassword,function(rows){
                    res.sendStatus(200);
                });
            } else {
                res.sendStatus(401);
            }
        });
    });
    app.get('/flats/name/:name', function (req, res) {
        var authenticated = authenticator.authenticateUsingToken(req);
        if(authenticated) {
            var flatname = req.params.name;
            flatsDB.getFlatID(flatname,function(rows){
                if (rows.length > 0) {
                    res.send(rows[0].id.toString());
                } else {
                    res.sendStatus(404);
                }
            });
        } else {
            res.sendStatus(401);
        }
    });
    app.get('/flats/:id', function (req, res) {
        var authenticated = authenticator.authenticateUsingToken(req);
        if(authenticated) {
            var flatID = req.params.id;
            flatsDB.getflatName(flatID,function(rows){
                if (rows.length > 0) {
                    res.send(rows[0].name);
                } else {
                    res.sendStatus(404);
                }
            });
        } else {
            res.sendStatus(401);
=======
    //getArchivalProducts
    app.get('/archive/flat/:id/user/:userID/filter/:filter/page/:page', function (req, res) {
        var flatID = req.params.id;
        var userID = req.params.userID;
        var filter = req.params.filter;
        var page = req.params.page;
        var authenticated = authenticator.authenticateUsingToken(req);
        if (authenticated) {
            authenticator.getLoggedUserID(req, function (id) {
                usersDB.isFlatMember(id, flatID, function (rows) {
                    if (rows.length>0 && rows[0].id != null) {
                        archiveDB.getProducts(flatID, userID, filter, page, function (rows) {
                            res.send(rows);
                        });
                    } else {
                        res.sendStatus(403);
                    }
                });
            });
        } else {
            res.sendStatus(401)
>>>>>>> e1a3dda09abc260a54f0847185cef34790a24c01
        }
    });
}
exports.runREST = runREST;