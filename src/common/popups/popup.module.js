define(
    [
        'angular',
        // popup modules
        './popup.module.dependencies',
        './authentication/authentication.popup.module'
    ],
    function popupModule(angular) {
        "use strict";

        return angular.module('sg.popups', [
            'sg.popups.dependencies',
            // actual popups start here
            'sg.popups.authentication'
        ])
    }
);