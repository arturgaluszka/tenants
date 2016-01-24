/**
 * Created by Dante on 2016-01-24.
 */
var walletdb = require('./../db/walletHistory');
function runREST(app) {
    app.put('/walletHistory/:id', function (req, res) {
        var json = JSON.parse(req.body.obj);
        var id = req.params.id;
        walletdb.getwalletdate(id,function(row){
            if(row== undefined){
                res.send(404);
            } else if( parseInt(row.modificationDate)>parseInt(json.modificationDate)){
                res.sendStatus(409);
            } else{
                walletdb.updatewallet(json, function () {
                    res.send(200);
                });
            }
        });
    });
    app.get('/walletHistory/flat/:id', function (req, res) {
        walletdb.getwalletstate(req.params.id, function (row) {
            if(row == undefined){
                res.send(404);
            } else if (row.length==0){
                res.send(404);
            } else{
                res.send(row);
            }

        });

    });

}
exports.runREST = runREST;