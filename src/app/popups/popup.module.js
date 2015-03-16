define(
    'app/popups/popup.module',
    [
        'angular',
        'app/popups/popup.dependencies',
        'app/popups/authentication/index'
    ],
    function popupModule(angular) {
        "use strict";

        return angular.module('sg.app.popups', [
            'sg.app.popups.dependencies',
            'sg.app.popups.authentication'
        ])
    }
);