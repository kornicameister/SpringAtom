define(
    'app/view/dashboard/cars/cars.module',
    [
        'angular',
        'angularUiRouterExtras',
        'common/state/state.index'
    ],
    function (angular) {
        "use strict";
        return angular.module('sg.app.view.dashboard.cars', [
            'sg.state',
            'ct.ui.router.extras'
        ]);
    }
);