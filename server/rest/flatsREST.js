var flatsDB = require('./../db/flats');
var authenticator = require('./../utils/authenticator');
var usersDB = require('./../db/users');
var statsDB = require('./../db/stats');
function runREST(app) {
    app.post('/flats/', function (req, res) {
        var authenticated = authenticator.authenticateUsingToken(req);
        if (authenticated) {
            var password = req.body.password;
            flatsDB.createFlat(password, function (rows) {
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
        var authenticated = authenticator.authenticateUsingToken(req);
        if (authenticated) {
            authenticator.getLoggedUserID(req, function (id) {
                usersDB.isFlatMember(id, req.params.flatID, function (rows) {
                    if (rows[0].id != null) {
                        flatsDB.getMembers(req.params.flatID, function (rows) {
                            res.send(rows.map(function (row) {
                                return row.userID;
                            }));
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
            flatDB.getflatName(flatID,function(rows){
                if (rows.length > 0) {
                    res.send(rows[0].name);
                } else {
                    res.sendStatus(404);
                }
            });
        } else {
            res.sendStatus(401);
        }
    });
}
exports.runREST = runREST;