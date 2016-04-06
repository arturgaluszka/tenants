var usersREST = require('./../rest/usersREST');
var flatsREST = require('./../rest/flatsREST');
var productREST = require('./productREST');
var statsREST = require('./../rest/statsREST');

function runREST(app) {
    usersREST.runREST(app);
    flatsREST.runREST(app);
    productREST.runREST(app);
    statsREST.runREST(app);
    console.info('REST running');
}
exports.runREST = runREST;