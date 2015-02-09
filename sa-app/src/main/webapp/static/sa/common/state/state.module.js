define(
    [
        'angular',
        './stateLabelResolve.factory',
        './state.provider',
        'angularUiRouter'
    ],
    function stateBuilder(angular, stateLabelResolveService, $stateBuilderProvider) {
        /**
         *
         * @module sg.state
         * @namespace sg
         */
        return angular.module('sg.state', ['ui.router'])
                .constant('MODULE_INFO', {
                name: 'sg.state',
                    version: '0.0.2'
                })
                .factory('$stateLabelResolve', stateLabelResolveService)
                .provider('$stateBuilder', $stateBuilderProvider)
    }
);