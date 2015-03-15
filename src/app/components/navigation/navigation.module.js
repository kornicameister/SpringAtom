define(
    'app/components/navigation/navigation.module',
    [
        'angular',
        'angularUiRouterExtras',
        'common/navigation/navigation'
    ],
    function (angular) {
        "use strict";
        return angular.module('sg.app.components.navigation', [
            'ct.ui.router.extras.previous',
            'sg.navigation'
        ])
    }
);