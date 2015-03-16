define(
    'app/view/dashboard/reports/reports.module',
    [
        'angular',
        'angularUiRouterExtras',
        'common/state/state.module'
    ],
    function (angular) {
        "use strict";
        return angular.module('sg.app.view.dashboard.reports', [
            'sg.state',
            'ct.ui.router.extras'
        ]);
    }
);