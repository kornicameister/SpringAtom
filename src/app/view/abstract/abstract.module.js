define(
    'app/view/abstract/abstract.module',
    [
        'angular',
        'common/state/state.module'
    ],
    function (angular) {
        "use strict";
        return angular.module('sg.app.view.index', [
            'sg.state'
        ])
    }
);