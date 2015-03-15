(function () {

    var gulp = require('gulp'),
        concat = require('gulp-concat'),
        uglify = require('gulp-uglify'),
        sourcemaps = require('gulp-sourcemaps'),
        ngAnnotate = require('gulp-ng-annotate'),
        stripDebug = require('gulp-strip-debug'),
        gutil = require('gulp-util'),
        clean = require('gulp-clean'),
        jshint = require('gulp-jshint'),
        less = require('gulp-less'),
        path = require('path'),
        rename = require('gulp-rename'),
        ngHtml2Js = require("gulp-ng-html2js"),
        minifyHtml = require("gulp-minify-html"),
        minifyCss = require('gulp-minify-css'),
        angularFilesort = require('gulp-angular-filesort'),
        inject = require('gulp-inject'),
        gulpMerge = require('gulp-merge'),
        livereload = require('gulp-livereload'),
        devServer = require('./server');

    var PATHS = require('./conf/paths.json'),
        TARGET_JS = 'springatom.js',
        TARGET_VIEW_JS = 'springatom.view.js',
        TARGET_LIB_JS = 'springatom.lib.js',
        TARGET_LIB_CSS = 'springatom.lib.css',
        TARGET_CSS = 'springatom.css';

    var DEV_MODE = 0,
        PROD_MODE = 1,
        MODE = DEV_MODE;

    gulp.task('default', ['build', 'lint']);

    // build related tasks
    gulp.task('build', function () {
        doClean();
        setTimeout(function () {
            buildLib();
            if (MODE === DEV_MODE) {
                buildViews();
                buildLess();
                buildSrc();
            } else {
                buildMinifiedViews();
                buildMinifiedLess();
                buildMinifiedSrc();
            }

            setTimeout(buildIndex, 2000);
        }, 666);

    });
    gulp.task('build.src', buildMinifiedSrc);
    gulp.task('build.less', buildMinifiedLess);
    gulp.task('build.views', buildMinifiedViews);
    gulp.task('build.index', buildIndex);
    gulp.task('build.lib', buildLib);
    gulp.task('build.lib.js', buildLibJsMinified);
    gulp.task('build.lib.css', buildLibCssMinified);
    gulp.task('build.font', buildFont);
    // build related tasks

    gulp.task('clean', doClean);
    gulp.task('watch', doWatch);
    gulp.task('lint', lint);

    gulp.task('prod', function () {
        MODE = PROD_MODE;
    });
    gulp.task('dev', function () {
        MODE = DEV_MODE;
    });

    gulp.task('test', doTest);
    gulp.task('deploy.koding', deployToKoding);

    function buildSrc() {
        gutil.log('Building src...');
        return gulpMerge(
            gulp.src([PATHS.SRC_DIR + '/**/app.js', PATHS.SRC_DIR + '/**/*.module.js'])
                .pipe(angularFilesort()),
            gulp.src([
                PATHS.SRC_DIR + '/**/*.js',
                '!' + PATHS.SRC_DIR + '/**/app.js',
                '!' + PATHS.SRC_DIR + '/**/*.module.js'
            ])
        ).pipe(concat({
                path: TARGET_JS,
                stat: {
                    mode: '0666'
                }
            }))
            .pipe(ngAnnotate())
            .pipe(stripDebug())
            .pipe(gulp.dest(PATHS.DIST_DIR + '/js')); // save non minified version
    }

    function buildMinifiedSrc() {
        gutil.log('Building minified src...');
        return buildSrc()
            .pipe(sourcemaps.init())
            .pipe(uglify({
                mangle          : true,
                preserveComments: 'some'
            }))
            .pipe(rename({
                suffix: '.min'
            }))
            .pipe(sourcemaps.write('../maps', {
                addComment: true
            }))
            .pipe(gulp.dest(PATHS.DIST_DIR + '/js'))
            .on('error', gutil.log);
    }

    function buildLess() {
        gutil.log('Building less...');
        return gulp
            .src('./src/assets/less/**/*.less')
            .pipe(less({
                paths: [path.join(__dirname, 'less', 'includes')]
            }))
            .pipe(concat(TARGET_CSS))
            .pipe(gulp.dest(PATHS.DIST_DIR + '/css')); // save non minified version
    }

    function buildMinifiedLess() {
        gutil.log('Building minified less...');
        return buildLess()
            .pipe(sourcemaps.init())
            .pipe(minifyCss({
                debug              : true,
                keepBreaks         : false,
                keepSpecialComments: 0
            }))
            .pipe(rename({
                suffix: '.min'
            }))
            .pipe(sourcemaps.write('../maps', {
                addComment: true
            }))
            .pipe(gulp.dest(PATHS.DIST_DIR + '/css'));
    }

    function buildViews() {
        gutil.log('Building views...');
        return gulp.src("./src/**/*.tpl.html")
            .pipe(minifyHtml({
                empty : true,
                spare : true,
                quotes: true
            }))
            .pipe(ngHtml2Js({
                moduleName    : 'sg.app',
                declareModule :false
            }))
            .pipe(concat(TARGET_VIEW_JS))
            .pipe(gulp.dest(PATHS.DIST_DIR + '/js'));
    }

    function buildMinifiedViews() {
        gutil.log('Building mifnied views...');
        return buildViews()
            .pipe(uglify({
                mangle          : true,
                preserveComments: 'some'
            }))
            .pipe(rename({
                suffix: '.min'
            }))
            .pipe(gulp.dest(PATHS.DIST_DIR + '/js'));
    }

    function buildIndex() {
        gutil.log('Building index...');

        var indexIgnorePath = PATHS.DIST_DIR.replace('.', '') + '/',
            getJsSrc = function getJsSrc() {
                var src = [],
                    targetJs = TARGET_JS.replace('.js',
                        MODE === PROD_MODE ? '.min.js' : '.js'
                    ),
                    targetViewJs = TARGET_VIEW_JS.replace('.js',
                        MODE === PROD_MODE ? '.min.js' : '.js');


                src.push(PATHS.DIST_DIR + '/js/' + targetJs);
                src.push(PATHS.DIST_DIR + '/js/' + targetViewJs);

                return src;
            },
            getCssSrc = function getCssSrc() {
                var src = [],
                    targetCss = TARGET_CSS.replace('.css',
                        MODE === PROD_MODE ? '.min.css' : '.css'
                    );

                src.push(PATHS.DIST_DIR + '/css/' + targetCss);

                return src;
            },
            getSrc = function getSrc() {
                return [].concat(getJsSrc(), getCssSrc());
            };

        var srcToInject = gulp
                .src(getSrc(), {read: false}),
            libJsToInject = gulp
                .src(PATHS.DIST_DIR + '/lib/min/springatom.lib.js'),
            libCssToInject = gulp
                .src(PATHS.DIST_DIR + '/lib/min/springatom.lib.css');

        var target = gulp.src('./src/index.html'),
            sources = gulpMerge(
                libJsToInject,
                libCssToInject,
                srcToInject
            );

        return target
            .pipe(inject(sources, {
                relative  : false,
                ignorePath: indexIgnorePath
            }))
            .pipe(gulp.dest(PATHS.DIST_DIR))
            .on('error', gutil.log);
    }

    function buildLibJs() {
        gutil.log('Building lib.js...');
        var paths = require('./conf/lib.json').js;
        return gulp.src(paths)
            .pipe(gulp.dest(PATHS.DIST_DIR + '/lib/js'));
    }

    function buildLibJsMinified() {
        gutil.log('Building lib.js minified...');
        return buildLibJs()
            .pipe(concat(TARGET_LIB_JS))
            .pipe(gulp.dest(PATHS.DIST_DIR + '/lib/min'));
    }

    function buildLibCss() {
        gutil.log('Building lib.css...');
        var cssFiles = require('./conf/lib.json').css;
        return gulp.src(cssFiles)
            .pipe(gulp.dest(PATHS.DIST_DIR + '/lib/css'));
    }

    function buildLibCssMinified() {
        gutil.log('Building lib.css minified...');
        return buildLibCss()
            .pipe(concat(TARGET_LIB_CSS))
            .pipe(gulp.dest(PATHS.DIST_DIR + '/lib/min'));
    }

    function buildLib() {
        gutil.log('Building lib...');
        buildLibCssMinified();
        buildLibJsMinified();
        buildFont();
    }

    function buildFont() {
        gutil.log('Building fonts...');
        var fonts = require('./conf/lib.json').fonts;
        return gulp
            .src(fonts)
            .pipe(gulp.dest(PATHS.DIST_DIR + '/lib/fonts'));
    }

    function doClean() {
        gutil.log('Clean...');
        return gulp
            .src(PATHS.DIST_DIR, {
                read: false
            })
            .pipe(clean({force: true}))
            .on('end', gutil.log);
    }

    function doWatch() {
        gulp.watch('./src/**/*.js', ['build.src']);
        gulp.watch('./src/**/*.less', ['build.less']);
        gulp.watch('./src/**/*.tpl.html', ['build.views']);
        gulp.watch('./src/index.html', ['build.index']);

        var server = devServer();

        gulp.watch([PATHS.DIST_DIR + './**']).on('change', server.livereload.changed);
    }

    function doTest() {

    }

    function lint() {
        gutil.log('Executing lint');
        return gulp.src(['./src/**/*.js'])
            .pipe(jshint())
            .pipe(jshint.reporter('default'))
            .pipe(jshint.reporter('fail'));
    }

    function deployToKoding() {
        return gulp
            .src(PATHS.DIST_DIR + '/**/*')
            .pipe(gulp.dest('../../Web'));
    }

}());