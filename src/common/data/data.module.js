angular
    .module('sg.common.data', [
        'parse-angular',
        'parse-angular.enhance',
        'sg.common.log'
    ])
    .constant('KEY_1', 'oa6pXlEwUVkEa7KsmZpNqmALFKLf3cvXL4mJTSLA')
    .constant('KEY_2', 'KL8eEg9ndVzaTHg2S65BLrR39SAZ9jSOOjcRJNlF')
    .run(['KEY_1', 'KEY_2', function (KEY_1, KEY_2) {
        Parse.initialize(KEY_1, KEY_2);
    }]);