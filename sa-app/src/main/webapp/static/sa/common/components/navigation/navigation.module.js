define(
    [
        'angular',
        'angularUiRouterExtras'
    ],
    function (angular) {
        "use strict";
        return angular.module('sg.components.navigation', ['ct.ui.router.extras.previous'])
    }
);