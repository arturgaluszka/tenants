var express = require('express');
var router = express.Router();

//rest api
router.get('/', function(req, res, next) {
  res.render('restapi');
});

module.exports = router;