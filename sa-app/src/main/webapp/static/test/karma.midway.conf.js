var conf = require('./conf/karma-shared-conf');

module.exports = function (config) {

    var newConfig = conf({
        files    : [
            {pattern: 'test/node_modules/ng-midway-tester/src/ngMidwayTester.js', included: false},
            {pattern: 'test/libs/angular-mocks/angular-mocks.js', included: false},
            {pattern: 'test/libs/angular-scenario/angular-scenario.js', included: false},
            {pattern: 'test/src/midway/**/*Test.js', included: false},
            'test/test-main.js'
        ],
        exclude  : [
            'test/node_modules/ng-midway-tester/test/**'
        ],
        logLevel: config.LOG_INFO,
        singleRun: false,
        browsers : ['ChromeCanary']
    });

    config.set(newConfig);

};
