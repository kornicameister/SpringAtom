module.exports = function(gulp){
    var browserSync = require('browser-sync');
    return function(){
        var paths = require('../conf/paths');

        gulp.watch(paths.SRC_DIR + '/**/*.js', ['scripts', browserSync.reload]);
        gulp.watch(paths.SRC_DIR + '/**/*.state.js', ['views', browserSync.reload]);
        gulp.watch(paths.SRC_DIR + '/**/*.tpl.html', ['views', browserSync.reload]);
        gulp.watch(paths.SRC_DIR + '/**/*.less', ['styles', browserSync.reload]);
        gulp.watch(paths.SRC_DIR + '/index.html', ['index', browserSync.reload]);
        return gulp.watch(paths.VENDOR_LIB, ['vendor', browserSync.reload]);
    }
};