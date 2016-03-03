var usersREST = require('./../rest/usersREST');
var flatsREST = require('./../rest/flatsREST');
var expenseREST = require('./../rest/expenseREST');
var statsREST = require('./../rest/statsREST');

function runREST(app) {
    usersREST.runREST(app);
    flatsREST.runREST(app);
    expenseREST.runREST(app);
    statsREST.runREST(app);
    console.info('REST running');
}
exports.runREST = runREST;