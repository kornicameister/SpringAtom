module.exports = function (gulp, plugins, options) {
    var mainBowerFiles = require('main-bower-files'),
        path = require('path');

    return function () {
        var paths = require('../../conf/paths'),
            production = options.env === require('../../conf/env').PRODUCTION_MODE;
        return gulp
            .src(mainBowerFiles({
                base          : paths.VENDOR_LIB,
                filter        : /.*\.(eot|svg|ttf|woff|woff2)$/i,
                checkExistence: true,
                debugging     : !production
            }))
            .pipe(gulp.dest(paths.DIST_DIR + '/fonts'))
            .pipe(plugins.notify('vendor/fonts task completed'));
    }
};