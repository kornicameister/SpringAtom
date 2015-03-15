define(
    'app/components/header/header.module',
    [
        'angular',
        'app/popups/authentication/authentication.popup.module'
    ],
    function (angular) {
        "use strict";
        return angular.module('sg.app.components.header', [
            'sg.app.popups.authentication'
        ])
    }
);