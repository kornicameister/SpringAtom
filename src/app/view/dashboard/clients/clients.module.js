define(
    'app/view/dashboard/clients/clients.module',
    [
        'angular',
        'angularUiRouterExtras',
        'common/state/state.module'
    ],
    function (angular) {
        "use strict";
        return angular.module('sg.app.view.dashboard.clients', [
            'sg.state',
            'ct.ui.router.extras'
        ]);
    }
);