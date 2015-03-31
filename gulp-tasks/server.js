module.exports = function (gulp) {
    var express = require('express'),
        refresh = require('gulp-livereload'),
        liveReload = require('connect-livereload'),
        gzipStatic = require('connect-gzip-static'),
        dpd = require('deployd'),
        liveReloadPort = 35729,
        serverPort = 3000;

    return function () {
        var distDir = require('../conf/paths').DIST_DIR,
            app = express(),
            server = require('http').createServer(app),
            io = require('socket.io')(server);

        dpd.attach(server, {
            socketIo: io,
            env     : 'development',
            db      : {
                host: 'localhost',
                port: 27017,
                name: 'SpringAtom3'
            }
        });

        app.use(liveReload({port: liveReloadPort}));
        app.use(gzipStatic(distDir));
        app.use(server.handleRequest);

        // start up
        app.listen(serverPort, function () {
            console.log('Listening on server.port=' + serverPort);
        });
        refresh.listen(liveReloadPort, function () {
            console.log('Listening on liveReload.port=' + liveReloadPort);
        });
        // start up

        require('./watch')(gulp, refresh);

        return app;
    }
};
