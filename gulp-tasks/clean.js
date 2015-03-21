module.exports = function (gulp, plugins) {

    var distDir = require('../conf/paths').DIST_DIR;

    return function () {
        return gulp.src(distDir, {read: false})
            .pipe(plugins.clean({force: true}))
            .pipe(plugins.notify('clean task completed'));
    }

};