define(
    'app/view/home/home.module',
    [
        'angular',
        'common/state/state.module'
    ],
    function (angular) {
        "use strict";
        return angular.module('sg.app.view.home', [
            'sg.state'
        ]);
    }
);