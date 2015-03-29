module.exports = function (gulp, plugins, options) {

    var PATHS = require('../conf/paths'),
        indexIgnorePath = 'build',
        production = options.env === require('../conf/env').PRODUCTION_MODE;

    var injection = plugins.merge(
        gulp.src(PATHS.DIST_DIR + '/js/lib.js'),
        gulp.src(PATHS.DIST_DIR + '/js/' + (production ? 'app.min.js' : 'app.js')),
        gulp.src(PATHS.DIST_DIR + '/js/' + (production ? 'view.min.js' : 'view.js')),
        gulp.src(PATHS.DIST_DIR + '/css/' + (production ? 'lib.min.css' : 'lib.css')),
        gulp.src(PATHS.DIST_DIR + '/css/app.css')
    );

    return function () {
        return gulp
            .src(PATHS.SRC_DIR + '/index.html')
            .pipe(plugins.inject(injection, {
                addRootSlash: false,
                relative    : false,
                ignorePath  : indexIgnorePath
            }))
            .pipe(plugins.minifyHtml())
            .pipe(gulp.dest(PATHS.DIST_DIR))
            .pipe(plugins.notify('index task completed'));
    }

};