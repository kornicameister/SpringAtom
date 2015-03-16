define(
    'app/view/dashboard/mechanics/mechanics.module',
    [
        'angular',
        'angularUiRouterExtras',
        'common/state/state.module'
    ],
    function (angular) {
        "use strict";
        return angular.module('sg.app.view.dashboard.mechanics', [
            'sg.state',
            'ct.ui.router.extras'
        ]);
    }
);