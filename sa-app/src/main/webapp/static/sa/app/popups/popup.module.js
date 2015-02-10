define(
    [
        'angular',
        'angularUiRouterExtras',
        'angularUiBootstrap'
    ],
    function popupModule(angular) {
        "use strict";

        return angular.module('sg.app.popups', ['ui.bootstrap.modal', 'ct.ui.router.extras.sticky'])
    }
);