define(
    [
        'angular',
        'angularUiBootstrap',
        'common/security/security.module'
    ],
    function (angular) {
        return angular.module('sg.app.popups.authentication', [
            'sg.security',
            'ui.bootstrap'
        ]);
    }
);