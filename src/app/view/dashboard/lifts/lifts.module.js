define(
    'app/view/dashboard/lifts/lifts.module',
    [
        'angular',
        'angularUiRouterExtras',
        'common/state/state.module'
    ],
    function (angular) {
        "use strict";
        return angular.module('sg.app.view.dashboard.lifts', [
            'sg.state',
            'ct.ui.router.extras'
        ]);
    }
);