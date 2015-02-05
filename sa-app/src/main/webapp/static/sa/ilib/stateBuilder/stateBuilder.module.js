define(
    [
        'angular',
        './stateLabelResolve.factory',
        './stateBuilder.provider',
        'angularUiRouter'
    ],
    function stateBuilder(angular, stateLabelResolveService, $stateBuilderProvider) {
        /**
         *
         * @module sg.state-builder
         * @namespace sg
         */
        angular.module('sg.state-builder', ['ui.router'])
            .constant('MODULE_INFO', {
                name   : 'sg.state-builder',
                version: '0.0.2'
            })
            .factory('$stateLabelResolve', stateLabelResolveService)
            .provider('$stateBuilder', $stateBuilderProvider)
    }
);