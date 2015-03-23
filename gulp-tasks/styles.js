module.exports = function (gulp, plugins, options) {
    var paths = require('../conf/paths'),
        path = require('path'),
        LessPluginCleanCSS = require('less-plugin-clean-css'),
        LessPluginAutoPrefix = require('less-plugin-autoprefix'),
        cleancss = new LessPluginCleanCSS({advanced: true}),
        autoprefix = new LessPluginAutoPrefix({browsers: ["last 2 versions"]});

    return function () {
        return gulp
            .src([
                paths.SRC_DIR + '/assets/less/**/*.less',
                paths.SRC_DIR + '/app/**/*.less',
                paths.SRC_DIR + '/common/**/*.less'
            ])
            .pipe(plugins.less({
                paths: [
                    path.join(__dirname, 'less', 'includes')
                ],
                plugins: [autoprefix, cleancss]
            }))
            .pipe(plugins.concat(paths.TARGET_CSS_FILE))
            .pipe(gulp.dest(paths.DIST_DIR + '/css'));
    }

};