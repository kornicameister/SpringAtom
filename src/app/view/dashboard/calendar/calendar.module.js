define(
    'app/view/dashboard/calendar/calendar.module',
    [
        'angular',
        'angularUiRouterExtras',
        'common/state/state.module'
    ],
    function (angular) {
        "use strict";
        return angular.module('sg.app.view.dashboard.calendar', [
            'sg.state',
            'ct.ui.router.extras'
        ]);
    }
);