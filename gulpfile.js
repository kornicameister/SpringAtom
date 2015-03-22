(function () {

    var gulp = require('gulp'),
        plugins = require('gulp-load-plugins')(),
        minimist = require('minimist'),
        browserSync = require('browser-sync');

    var knownOptions = {
            string : 'env',
            default: {env: process.env.NODE_ENV || require('./conf/env').DEV_MODE}
        },
        options = minimist(process.argv.slice(2), knownOptions);

    // tasks
    gulp.task('default', ['clean', 'vendor', 'scripts', 'styles', 'views', 'index']);
    gulp.task('clean', getTask('clean'));
    gulp.task('scripts', getTask('scripts'));
    gulp.task('views', getTask('views'));
    gulp.task('index', getTask('index'));
    gulp.task('styles', getTask('styles'));
    gulp.task('vendor.scripts', getTask('vendor/scripts'));
    gulp.task('vendor.font', getTask('vendor/fonts'));
    gulp.task('vendor.styles', getTask('vendor/styles'));
    gulp.task('vendor', function () {
        getTask('vendor/scripts')();
        getTask('vendor/fonts')();
        getTask('vendor/styles')();
    });
    gulp.task('browser-sync', function () {
        var distDir = require('./conf/paths').DIST_DIR;
        browserSync({
            server: {
                baseDir       : distDir,
                logLevel      : 'debug',
                logConnections: true,
                logFileChanges: true
            }
        });
    });
    gulp.task('watch', ['browser-sync'], function () {
        var paths = require('./conf/paths');

        gulp.watch(paths.SRC_DIR + '/**/*.js', ['scripts', browserSync.reload]);
        gulp.watch(paths.SRC_DIR + '/**/*.state.js', ['views', browserSync.reload]);
        gulp.watch(paths.SRC_DIR + '/**/*.tpl.html', ['views', browserSync.reload]);
        gulp.watch(paths.SRC_DIR + '/**/*.less', ['styles', browserSync.reload]);
        gulp.watch(paths.SRC_DIR + '/index.html', ['index', browserSync.reload]);
        gulp.watch(paths.VENDOR_LIB, ['vendor', browserSync.reload]);
    });
    // tasks

    function getTask(task) {
        return require('./gulp-tasks/' + task)(gulp, plugins, options);
    }

}());