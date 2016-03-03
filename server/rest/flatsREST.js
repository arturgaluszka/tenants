var flatsDB = require('./../db/flats');
var authenticator = require('./../utils/authenticator');
var usersDB = require('./../db/users');
function runREST(app) {
    app.post('/flats/', function (req, res) {
        var authenticated = authenticator.authenticateUsingToken(req);
        if(authenticated) {
            var password = req.body.password;
            flatsDB.createFlat(password,function(rows){
                res.send((rows[0]['LAST_INSERT_ID()']).toString());
            });
        } else {
            res.sendStatus(401);
        }
    });
    app.get('/flats/:flatID/users',function(req, res){
        var authenticated = authenticator.authenticateUsingToken(req);
        if(authenticated){
            authenticator.getLoggedUserID(req,function(id){
                usersDB.isFlatMember(id,req.params.flatID,function(rows){
                    if(rows[0].id!=null){
                        flatsDB.getMembers(req.params.flatID,function(rows){
                            res.send(rows.map(function(row){return row.userID;}));
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
}
exports.runREST = runREST;