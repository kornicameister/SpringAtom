var conf = require('./conf/karma-shared-conf');

module.exports = function (config) {

    config.set(conf({
        files    : [
            {pattern: 'test/bower_components/angular-mocks/angular-mocks.js', included: false},
            {pattern: 'test/bower_components/angular-scenario/angular-scenario.js', included: false},
            {pattern: 'test/src/e2e/**/*Test.js', included: false},
            'test/test-main.js'
        ],
        logLevel : config.LOG_INFO,
        singleRun: true,
        browsers : ['ChromeCanary']
    }));

};
