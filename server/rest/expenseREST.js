function runREST(app) {
    //removeFromMainList
    app.delete('/products/:id/mainlist', function (req, res) {
            res.sendStatus(501);
    });
    //updateProduct
    app.put('/products/:id', function (req, res) {
        res.sendStatus(501);
    });
    //addProduct
    app.post('/products/', function (req, res) {
        res.sendStatus(501);
    });
    //reserveProduct
    app.post('/products/:id/userlist', function (req, res) {
        res.sendStatus(501);
    });
    //buyProduct
    app.post('/products/:id/purchase', function (req, res) {
        res.sendStatus(501);
    });
    //unreserveProduct
    app.delete('/products/:id/userlist', function (req, res) {
        res.sendStatus(501);
    });
    //cancelBuy
    app.delete('/products/:id/purchase', function (req, res) {
        res.sendStatus(501);
    });
}
exports.runREST = runREST;