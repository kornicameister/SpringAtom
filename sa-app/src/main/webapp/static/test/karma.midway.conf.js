var conf = require('./conf/karma-shared-conf');

module.exports = function (config) {

    config.set(conf({
        files    : [
            {pattern: 'test/libs/angular-mocks/angular-mocks.js', included: false},
            {pattern: 'test/libs/angular-scenario/angular-scenario.js', included: false},
            {pattern: 'test/src/midway/**/*Test.js', included: false},
            'test/test-main.js'
        ],
        logLevel : config.LOG_INFO,
        singleRun: false,
        browsers : ['ChromeCanary']
    }));

};
