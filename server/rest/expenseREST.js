var expensesdb = require('./../db/expenses');
function runREST(app) {
    app.get('/expenses/flats/:id', function (req, res) {
        expensesdb.getexpenses(req.params.id,function (row) {
            res.send(row);
        });

    });
    app.put('/expenses/:id', function (req, res) {
        var json = JSON.parse(req.body.obj);
        var id = req.params.id;
        expensesdb.getexpensedate(id,function(row){
            if(row== undefined){
                res.send(404);
            } else if( parseInt(row.modificationDate)>parseInt(json.modificationDate)){
                res.sendStatus(409);
            } else{
                expensesdb.updateexpense(json, function () {
                    res.send(200);
                });
            }
        });
    });
    app.post('/expenses/', function (req, res) {
        var json = JSON.parse(req.body.obj);
        expensesdb.newexpense(json, function (row) {
            console.log("Created new Expense with id=" + row[0]['LAST_INSERT_ID()']);
            json.id = row[0]['LAST_INSERT_ID()'];
            res.send(json);
        })
    });
    app.get('/expenses/:id', function (req, res) {
        expensesdb.getexpense(req.params.id, function (row) {
            if(row == undefined){
                res.send(404);
            } else if (row.length==0){
                res.send(404);
            } else{
                res.send(row);
            }

        });

    });
    app.delete('/expenses/:id',function(req,res){
        expensesdb.deleteexpense(req.params.id,function(){
            res.send(200);
        })
    });
}
exports.runREST = runREST;