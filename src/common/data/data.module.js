angular
    .module('sg.common.data', [
        'parse-angular',
        'parse-angular.enhance',
        'sg.common.log'
    ])
    .constant('KEY_1', 'oa6pXlEwUVkEa7KsmZpNqmALFKLf3cvXL4mJTSLA')
    .constant('KEY_2', 'KL8eEg9ndVzaTHg2S65BLrR39SAZ9jSOOjcRJNlF')
    .run(['logger', 'KEY_1', 'KEY_2', function (logger, KEY_1, KEY_2) {
        Parse.initialize(KEY_1, KEY_2);
        logger('sg.common.data').info('sg.common.data is up and running');
    }]);