var usersDB = require('./../db/users');

function authenticateUsingToken(req) {
    var token = req.headers.authorization;
    var encrypted = req.connection.encrypted;
    if(encrypted){
        if(token.substring(0,1)==1 && token.substring(1) != ""){
            return true;
        } else {
            console.log("authentication failed for token="+token);
            return false;
        }
    } else {
        console.log("authentication failed for token="+token);
        return false;
    }
}
function authenticateUsingUsernameAndPassword(username,password,successCallback,failureCallback){
    usersDB.matchPasswordForUser(username,password,function(row){
        if(row[0].password==password){
            successCallback(createValidToken(username));
        } else {
            failureCallback();
        }
    });
}
function createValidToken(username){
    return '1'+username;
}
function createInvalidToken(username){
    return '0'+username;
}
function getLoggedUserID(req,callback){
    var token = req.headers.authorization;
    usersDB.getUserID(token.substring(1),function(rows){
        callback(rows[0].id);
    });

}
exports.authenticateUsingToken = authenticateUsingToken;
exports.authenticateUsingUsernameAndPassword=authenticateUsingUsernameAndPassword;
exports.getLoggedUserID = getLoggedUserID;