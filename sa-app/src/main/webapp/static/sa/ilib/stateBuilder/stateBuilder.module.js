define(
    [
        'angular',
        './stateLabelResolve.factory',
        './stateBuilder.provider',
        'ngRouter'
    ],
    function stateBuilder(angular, stateLabelResolveService, $stateBuilderProvider) {
        /**
         *
         * @module sg.state-builder
         * @namespace sg
         */
        angular.module('stateBuilder', ['ui.router'])
            .constant('MODULE_INFO', {
                name   : 'sg.state-builder',
                version: '0.0.1'
            })
            .factory('$stateLabelResolve', stateLabelResolveService)
            .provider('$stateBuilder', $stateBuilderProvider)
    }
);