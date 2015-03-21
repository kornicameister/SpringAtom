module.exports = function (gulp, plugins, options) {
    var mainBowerFiles = require('main-bower-files'),
        path = require('path');

    return function () {
        var paths = require('../../conf/paths'),
            production = options.env === require('../../conf/env').PRODUCTION_MODE;
        return gulp
            .src(mainBowerFiles({
                base          : paths.VENDOR_LIB,
                filter        : /.*\.css$/i,
                checkExistence: true,
                debugging     : !production
            }))
            .pipe(plugins.if(
                !production,
                plugins.tap(function (file, t) {
                    console.log(path.basename(file.path));
                })
            ))
            .pipe(plugins.if(
                production,
                plugins.sourcemaps.init()
            ))
            .pipe(plugins.concat(paths.TARGET_CSS_LIB_FILE))
            .pipe(gulp.dest(paths.DIST_DIR + '/css'))
            .pipe(plugins.if(
                production,
                plugins.minifyCss()
            ))
            .pipe(plugins.if(
                production,
                plugins.rename({
                    suffix: '.min'
                })
            ))
            .pipe(plugins.if(
                production,
                plugins.sourcemaps.write('../maps', {
                    addComment: true
                })
            ))
            .pipe(plugins.if(
                production,
                gulp.dest(paths.DIST_DIR + '/css')
            ))
            .pipe(plugins.notify('vendor/styles task completed'));
    }
};