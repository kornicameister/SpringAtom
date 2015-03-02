var conf = require('./conf/karma-shared-conf');

module.exports = function (config) {

    config.set(conf({
        files    : [
            {pattern: 'sa/vendor/angular-mocks/angular-mocks.js', included: false},
            {pattern: 'sa/vendor/angular-scenario/angular-scenario.js', included: false},
            {pattern: 'test/src/unit/**/*Spec.js', included: false},
            'test/test-main.js'
        ],
        logLevel : config.LOG_DEBUG,
        singleRun: false,
        browsers : ['PhantomJS']
    }));

};
