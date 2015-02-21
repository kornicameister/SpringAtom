define(
    [
        'angular',
        '../../security/security.module'
    ],
    function (angular) {
        return angular.module('sg.popups.authentication', ['sg.security']);
    }
);