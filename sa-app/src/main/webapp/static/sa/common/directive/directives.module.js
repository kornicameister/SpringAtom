define(
    [
        'angular',
        'lodash',
        './controllerBoundDirective',
        './unwrapPromise.directive'
    ],
    function directiveModule(angular, _, controllerBoundDirective, unwrapPromiseDirective) {
        "use strict";
        var module = angular.module('sg.directives', []);

        module.factory('controllerBoundDirective', _.constant(controllerBoundDirective));
        module.directive('sgUnwrapPromise', unwrapPromiseDirective);

        return module;
    }
);