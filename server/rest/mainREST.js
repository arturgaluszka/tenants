var expenserest = require('./../rest/expenseREST');
function runREST(app) {
    expenserest.runREST(app);
}
exports.runREST = runREST;