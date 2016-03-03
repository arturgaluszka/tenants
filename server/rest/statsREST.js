function runREST(app) {
    //getStats
    app.get('/stats/user/:userID/flat/:flatID', function (req, res) {
        res.sendStatus(501);
    });
    //getArchivalProductsForUser
    app.get('/archive/user/:userID/flat/:flatID', function (req, res) {
        res.sendStatus(501);
    });
    //getArchivalProductsForFlat
    app.get('/archive/flat/:flatID', function (req, res) {
        res.sendStatus(501);
    });
}
exports.runREST = runREST;