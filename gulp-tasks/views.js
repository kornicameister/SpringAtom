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
            .pipe(plugins.ngHtml2js({
                moduleName   : 'springatom',
                declareModule: false
            }))
            .pipe(plugins.concat(paths.TARGET_VIEW_JS_FILE))
            .pipe(plugins.if(
                production,
                plugins.sourcemaps.init()
            ))
            .pipe(gulp.dest(distDir + '/js'))
            .pipe(plugins.if(
                production,
                plugins.uglify({
                    mangle          : true,
                    preserveComments: 'some'
                })
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
                gulp.dest(distDir + '/js')
            ))
            .pipe(plugins.notify('views task completed'))
    }
};