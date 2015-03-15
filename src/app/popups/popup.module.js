define(
    [
        'angular',
        'app/popups/popup.dependencies.js',
        'app/popups/authentication/authentication.popup.module'
    ],
    function popupModule(angular) {
        "use strict";

        return angular.module('sg.app.popups', [
            'sg.app.popups.dependencies',
            'sg.app.popups.authentication'
        ])
    }
);