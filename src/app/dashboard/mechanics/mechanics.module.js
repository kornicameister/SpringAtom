define(
    [
        'angular',
        'angularUiRouterExtras',
        'common/state/state.provider'
    ],
    function (angular) {
        "use strict";
        return angular.module('sg.app.dashboard.mechanics', [
            'sg.state',
            'ct.ui.router.extras'
        ]);
    }
);