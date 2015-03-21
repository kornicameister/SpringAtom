module.exports = function (gulp, plugins, options) {
    var mainBowerFiles = require('main-bower-files'),
        path = require('path');

    return function () {
        var paths = require('../../conf/paths'),
            production = options.env === require('../../conf/env').PRODUCTION_MODE;
        return gulp
            .src(mainBowerFiles({
                base          : paths.VENDOR_LIB,
                filter        : /.*\.js$/i,
                checkExistence: true,
                debugging     : !production
            }))
            .pipe(plugins.if(
                !production,
                plugins.tap(function (file, t) {
                    console.log(path.basename(file.path));
                })
            ))
            .pipe(plugins.concat(paths.TARGET_JS_LIB_FILE))
            .pipe(gulp.dest(paths.DIST_DIR + '/js'))
            .pipe(plugins.notify('vendor/scripts task completed'));
    }
};