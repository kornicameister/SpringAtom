define(
    [
        'angular',
        'angularUiRouterExtras',
        'common/state/state.provider'
    ],
    function (angular) {
        "use strict";
        return angular.module('sg.app.dashboard.lifts', [
            'sg.state',
            'ct.ui.router.extras'
        ]);
    }
);