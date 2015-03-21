module.exports = function (gulp, plugins, options) {
    return function () {

        var paths = require('../conf/paths'),
            production = options.env === require('../conf/env').PRODUCTION_MODE,
            distDir = paths.DIST_DIR;

        return gulp.src(paths.SRC_DIR + '/**/*.tpl.html')
            .pipe(plugins.minifyHtml({
                empty : true,
                spare : true,
                quotes: true
            }))
            .pipe(gulp.dest(distDir))
            .pipe(plugins.notify('views task completed'))
    }
};