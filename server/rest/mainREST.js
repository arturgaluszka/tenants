var expenserest = require('./../rest/expenseREST');
var todorest = require('./../rest/todoREST');
function runREST(app) {
    expenserest.runREST(app);
    todorest.runREST(app);

}
exports.runREST = runREST;