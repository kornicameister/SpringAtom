define(
    'app/view/about/about.module',
    [
        'angular',
        'common/state/state.index'
    ],
    function (angular) {
        "use strict";
        return angular.module('sg.app.view.about', [
            'sg.state'
        ]);
    }
);