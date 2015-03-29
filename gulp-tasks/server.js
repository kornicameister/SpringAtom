module.exports = function () {
    return function () {
        var distDir = require('../conf/paths').DIST_DIR;

        return require('browser-sync')({
            server: {
                baseDir       : distDir,
                logLevel      : 'debug',
                logConnections: true,
                logFileChanges: true
            }
        });
    }
};
