var usersDB = require('./../db/users');
var authenticator = require('./../utils/authenticator');
function runREST(app) {
    app.post('/login', function (req, res) {
        var username = req.body.username;
        var password = req.body.password;
        authenticator.authenticateUsingUsernameAndPassword(
            username,
            password,
            function (token) {
                res.send(token);
            },
            function (token) {
                res.send(token);
            });
    });
    app.post('/users/:userID/flat/', function (req, res) {
        var authenticated = authenticator.authenticateUsingToken(req);
        if (authenticated) {
            usersDB.matchPasswordForFlat(req.body.flatID, req.body.flatPassword, function (rows) {
                if (rows[0].password == req.body.flatPassword) {
                    usersDB.signUserToFlat(req.params.userID, req.body.flatID, function (rows) {
                        //res.send((rows[0]['LAST_INSERT_ID()']).toString());
                        res.sendStatus(200);
                    });
                } else {
                    res.sendStatus(403);
                }
            });
        } else {
            res.sendStatus(401);
        }
    });
    app.delete('/users/:userID/flat/:flatID', function (req, res) {
        var authenticated = authenticator.authenticateUsingToken(req);
        if (authenticated) {
            usersDB.moveOutUser(req.params.userID, req.params.flatID, function (rows) {
                if (rows.affectedRows == 0) {
                    res.sendStatus(404);
                } else {
                    res.sendStatus(200);
                }
            });
        } else {
            res.sendStatus(401);
        }
    });
    app.get('/users/name/:name', function (req, res) {
        var authenticated = authenticator.authenticateUsingToken(req);
        if(authenticated) {
            var username = req.params.name;
            usersDB.getUserID(username, function (rows) {
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
    app.get('/users/:id', function (req, res) {
        var authenticated = authenticator.authenticateUsingToken(req);
        if(authenticated) {
            var userID = req.params.id;
            userDB.getUsername(userID,function(rows){
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
    app.post('/users/', function (req, res) {
        var username = req.body.username;
        var password = req.body.password;
        usersDB.createUser(username,password,function(rows){
            res.send((rows[0]['LAST_INSERT_ID()']).toString());
        });

    });
    app.put('/users/:id/password', function (req, res) {
        var userID = req.params.id;
        var oldPassword = req.body.oldPassword;
        var newPassword = req.body.newPassword;
        usersDB.getUsername(userID,function(rows){
            if(rows[0].username.length>0){
                usersDB.matchPasswordForUser(rows[0].username,oldPassword,function(rows){
                    if(oldPassword==rows[0].password){
                        usersDB.changePassword(userID,newPassword,function(rows){
                            res.sendStatus(200);
                        });
                    } else{
                        res.sendStatus(401);
                    }
                });
            } else{
                res.sendStatus(404);
            }
        });
    });
    app.get('/users/:id/flats', function (req, res) {
        var authenticated = authenticator.authenticateUsingToken(req);
        if(authenticated) {
            var userID = req.params.id;
            authenticator.getLoggedUserID(req, function (id) {
                if(id==userID){
                    usersDB.getUserFlats(userID,function(rows){
                        res.send(rows.map(function (row) {
                            return row.id;
                        }));
                    });
                }else{
                    res.sendStatus(403)
                }
            });
        } else {
            res.sendStatus(401);
        }
    });
}
exports.runREST = runREST;