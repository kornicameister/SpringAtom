define(
    [
        'angular',
        // popup modules
        './popup.module.dependencies',
        './authentication/authentication.popup.module'
    ],
    function popupModule(angular) {
        "use strict";

        return angular.module('sg.app.popups', [
            'sg.app.popups.dependencies',
            // actual popups start here
            'sg.app.popups.authentication'
        ])
    }
);