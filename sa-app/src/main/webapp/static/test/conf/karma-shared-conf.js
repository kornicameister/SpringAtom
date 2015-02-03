/**
 * Created by Tomasz on 2014-12-30.
 */
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
            {pattern: 'sa/**/*.js', included: false}
        ],
        /**
         {@type Array} of files that will be excluded in every test suit
         */
        globalToExclude = [
            'sa/main.js',       // dont run application
            'sa/vendor/**/*[tT]est.js', // dont run test in the libs
            'sa/vendor/**/*[sS]pec.js',
            'sa/vendor/jquery/test/**'
        ],
        defaultBrowsers = ['ChromeCanary', 'IE'];

    return {
        basePath        : '../',
        port            : 9876,
        colors          : true,
        captureTimeout  : 60000,
        frameworks      : [
            'jasmine',
            'requirejs'
        ],
        reporters       : ['progress', 'coverage', 'junit'],
        preprocessors   : {
            'sa/**/*.js': ['coverage']
        },
        coverageReporter: {
            type: 'html',
            dir : 'test/results/coverage'
        },
        junitReporter   : {
            outputFile: 'test/results/test-results.xml',
            suite     : ''
        },
        // override set of settings from the passed conf literal
        browsers        : conf.browsers || defaultBrowsers,
        logLevel        : conf.logLevel || 'INFO',
        singleRun       : conf.singleRun || false,
        autoWatch       : conf.autoWatch || true,
        exclude         : updateArray(globalToExclude, conf.exclude || []),
        files           : updateArray(globalFilesToInclude, conf.files || [])
    }
};