define(
    [
        'angular',
        'angularLocalStorage',
        '../state/state.module'
    ],
    function navigationModule(angular) {
        "use strict";
        return angular.module('sg.navigation', ['LocalStorageModule', 'sg.state']);
    }
);