module.exports = function (config) {
    config.set({
        basePath        : '',
        port            : 9876,
        colors          : true,
        frameworks      : [
            'jasmine',
            'requirejs'
        ],
        files           : [
            {
                pattern : 'libs/**/*.js',
                included: false
            },
            {
                pattern : 'sa/**/*.js',
                included: false
            },
            {
                pattern : 'test/**/*Test.js',
                included: false
            },
            'test/test-main.js'
        ],
        exclude         : [
            'sa/main.js',
            'libs/**/*Test.js',
            'libs/**/*Spec.js',
            'libs/jquery/test/**'
        ],

        // test results reporter to use
        // possible values: 'dots', 'progress'
        // available reporters: https://npmjs.org/browse/keyword/karma-reporter
        reporters       : ['progress', 'coverage', 'junit'],

        // preprocess matching files before serving them to the browser
        // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
        preprocessors   : {
            'sa/**/*.js'  : ['coverage'],
            'test/**/*.js': ['coverage']
        },

        // coverage reporter conf
        coverageReporter: {
            type: 'html',
            dir : '../test_results/coverage'
        },
        junitReporter   : {
            outputFile: '../test_results/test-results.xml',
            suite     : ''
        },


        // level of logging
        // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
        logLevel        : config.LOG_INFO,

        // enable / disable watching file and executing tests whenever any file changes
        autoWatch       : true,

        // start these browsers
        // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
        browsers        : [
            //'Chrome',
            'ChromeCanary',
            //'Firefox',
            'IE'
        ],

        // kills browser after given time
        captureTimeout  : 60000,

        // Continuous Integration mode
        // if true, Karma captures browsers, runs the tests and exits
        singleRun       : true
    });
};
