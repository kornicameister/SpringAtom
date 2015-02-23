module.exports = function (conf) {

    /**
     * Utility function to append {@code append} array to the {@code original} array
     */
    function updateArray(original, append) {
        var appendLength = append.length;

        for (var it = 0; it < appendLength; it++) {
            original.push(append[it]);
        }

        return original;
    }

    var /**
         {@type Array} of files that will be included in every test suit
         */
        globalFilesToInclude = [
            'sa/vendor/angular/angular.js',
            {pattern: 'sa/app/**/*.html', included: true},
            {pattern: 'sa/common/**/*.html', included: true},
            {pattern: 'sa/app/**/*.less', included: false},
            {pattern: 'sa/common/**/*.less', included: false},
            {pattern: 'sa/**/*.js', included: false}
        ],
        /**
         {@type Array} of files that will be excluded in every test suit
         */
        globalToExclude = [
            'sa/main.js',       // dont run application
            'sa/vendor/**/*[tT]est.js', // dont run test in the libs
            'sa/vendor/**/*[sS]pec.js',
            'sa/vendor/src/*.js',
            'sa/vendor/jquery/[test|src|build|speed]/**'
        ],
        defaultBrowsers = ['ChromeCanary', 'IE'];

    return {
        basePath             : '../',
        port: 8666,
        colors               : true,
        captureTimeout       : 60000,
        frameworks           : [
            'jasmine',
            'requirejs'
        ],
        reporters            : ['progress', 'coverage', 'junit'],
        preprocessors        : {
            'sa/app/*.js'        : ['coverage'],
            'sa/common/*.js'     : ['coverage'],
            'sa/app/**/*.html'   : ['ng-html2js'],
            'sa/common/**/*.html': ['ng-html2js']
        },
        coverageReporter     : {
            type: 'html',
            dir : 'test/results/coverage'
        },
        junitReporter        : {
            outputFile: 'test/results/test-results.xml',
            suite     : ''
        },
        ngHtml2JsPreprocessor: {
            moduleName     : 'templates',
            prependPrefix  : '',
            cacheIdFromPath: function (filepath) {
                filepath = filepath.replace('sa/', '');
                console.log('Loading >>> ' + filepath);
                return filepath;
            }
        },
        // override set of settings from the passed conf literal
        browsers             : conf.browsers || defaultBrowsers,
        logLevel             : conf.logLevel || 'INFO',
        singleRun            : conf.singleRun || false,
        autoWatch            : conf.autoWatch || true,
        exclude              : updateArray(globalToExclude, conf.exclude || []),
        files                : updateArray(globalFilesToInclude, conf.files || [])
    }
};