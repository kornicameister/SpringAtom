module.exports = function () {
    var refresh = require('gulp-livereload'),
        express = require('express'),
        livereload = require('connect-livereload'),
        livereloadport = 35729,
        serverport = 5000,
        server = express(),
        paths = require('./conf/paths.json');

    server.use(livereload({port: livereloadport}));
    server.use(express.static(paths.DIST_DIR));

    server.listen(serverport, function () {
        console.log('Server listing at ' + serverport + ' port');
    });
    refresh.listen(livereloadport, function () {
        console.log('Refresh listing at ' + livereloadport + ' port');
    });

    return {
        server    : server,
        livereload: refresh
    };
};