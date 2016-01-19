var expenserest = require('./../rest/expenseREST');
var todorest = require('./../rest/todoREST');
var walletrest= require('./../rest/walletREST');
function runREST(app) {
    expenserest.runREST(app);
    todorest.runREST(app);
    walletrest.runREST(app);

}
exports.runREST = runREST;