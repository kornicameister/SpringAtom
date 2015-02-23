define(
    [
        'angular',
        'angularUiRouterExtras',
        '../../navigation/navigation'
    ],
    function (angular) {
        "use strict";
        return angular.module('sg.components.navigation', [
            'ct.ui.router.extras.previous',
            'sg.navigation'
        ])
    }
);