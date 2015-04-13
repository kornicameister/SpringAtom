module.exports = function (gulp, plugins, options) {

    var paths = require('../conf/paths'),
        distDir = paths.DIST_DIR,
        jsSrcDir = paths.SRC_DIR + '/',
        gulpMerge = plugins.merge;

    return function appSrc() {
        var streams = {
                extensions: gulp.src(jsSrcDir + '/extensions.js'),
                src       : gulpMerge(
                    gulp.src(jsSrcDir + '/app/**/*.js'),
                    gulp.src(jsSrcDir + '/common/**/*.js'),
                    gulp.src(jsSrcDir + '/springatom.js') // load main file at the end
                )
                    .pipe(plugins.wrap('//<%= file.path %>\n\n(function(angular){\n"use strict";\n<%= contents %>\n})(window.angular);'))
                    .pipe(plugins.ngAnnotate())
                    .pipe(plugins.angularFilesort())
            },
            production = options.env === require('../conf/env').PRODUCTION_MODE;

        return gulpMerge(streams.extensions, streams.src)
            .pipe(plugins.if(
                production,
                plugins.sourcemaps.init()
            ))
            .pipe(plugins.concat(paths.TARGET_JS_FILE))
            .pipe(gulp.dest(distDir + '/js'))
            .pipe(plugins.if(
                production,
                plugins.stripDebug()
            ))
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
            .pipe(plugins.notify('scripts task completed'));
    }

};