var tododb = require('./../db/todo');
function runREST(app) {
    app.get('/todos/flats/:id', function (req, res) {
        tododb.gettodos(req.params.id,function (row) {
            res.send(row);
        });

    });
    app.put('/todos/:id', function (req, res) {
        var json = JSON.parse(req.body.obj);
        var id = req.params.id;
        tododb.gettododate(id,function(row){
            if(row== undefined){
                res.send(404);
            } else if( parseInt(row.modificationDate)>parseInt(json.modificationDate)){
                res.sendStatus(409);
            } else{
                tododb.updatetodo(json, function () {
                    res.send(200);
                });
            }
        });
    });
    app.post('/todos/', function (req, res) {
        var json = JSON.parse(req.body.obj);
        tododb.newtodo(json, function (row) {
            console.log("Created new todo with id=" + row[0]['LAST_INSERT_ID()']);
            json.id = row[0]['LAST_INSERT_ID()'];
            res.send(json);
        })
    });
    app.get('/todos/:id', function (req, res) {
        tododb.gettodo(req.params.id, function (row) {
            if(row == undefined){
                res.send(404);
            } else if (row.length==0){
                res.send(404);
            } else{
                res.send(row);
            }

        });

    });
    app.delete('/todos/:id',function(req,res){
        tododb.deletetodo(req.params.id,function(rows){
            if(rows.affectedRows==0){
                res.send(404);
            } else{
                res.send(200);
            }

        })
    });
}
exports.runREST = runREST;