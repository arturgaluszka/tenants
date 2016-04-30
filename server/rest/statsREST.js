var statsDB = require('./../db/stats');
var archiveDB = require('./../db/archive');
var usersDB = require('./../db/users');
var authenticator = require('./../utils/authenticator');
function runREST(app) {
    //getStats
    app.get('/stats/user/:userID/flat/:flatID', function (req, res) {
        var authenticated = authenticator.authenticateUsingToken(req);
        if (authenticated) {
            authenticator.getLoggedUserID(req, function (id) {
                usersDB.isFlatMember(id, req.params.flatID, function (rows) {
                    if (rows.length>0 && rows[0].id != null) {
                        statsDB.getValue(req.params.userID, req.params.flatID, function (rows) {
                            var stats = rows;
                            stats.user = parseInt(req.params.userID);
                            stats.flat = parseInt(req.params.flatID);
                            res.send(stats);
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
        }
    });
}
exports.runREST = runREST;