define(
    [
        'angular',
        './user/user.all'
    ],
    function resourcesModule(angular) {
        "use strict";
        return angular.module('sg.resources', ['sg.resources.user']);
    }
);