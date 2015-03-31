module.exports = function (gulp, refresh) {
    var paths = require('../conf/paths');

    // set up watchers
    gulp.watch(paths.SRC_DIR + '/**/*.js', ['scripts']);
    gulp.watch(paths.SRC_DIR + '/**/*.state.js', ['views']);
    gulp.watch(paths.SRC_DIR + '/**/*.tpl.html', ['views']);
    gulp.watch(paths.SRC_DIR + '/**/*.less', ['styles']);
    gulp.watch(paths.SRC_DIR + '/index.html', ['index']);
    gulp.watch(paths.VENDOR_LIB, ['vendor']);
    // set up watchers

    // on changing dist dir, refresh the page
    return gulp.watch(paths.DIST_DIR + '/**').on('change', refresh.changed);
};
