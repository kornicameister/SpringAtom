define(
    [
        'angular',
        'ilib/stateBuilder/stateBuilder.module',
        // module deps
        './header/headerView.controller',
        './rootView.controller',
        './rootView.state'
    ],
    function (angular,
              stateBuilderModule,
              rootViewHeaderController,
              rootViewCtrl,
              rootViewState) {
        "use strict";
        return angular.module('sg.root-view', ['sg.state-builder'])
            .constant('MODULE_INFO', {
                name   : 'sg.root-view',
                version: '0.0.1'
            })
            // TODO add names to included files in order to loose up the need of defining them here
            .controller('RootViewHeaderController', rootViewHeaderController)
            .controller('RootViewController', rootViewCtrl)
            .config(['$stateBuilderProvider', '$urlRouterProvider', registerRootState]);

        function registerRootState($stateBuilderProvider, $urlRouterProvider) {
            $stateBuilderProvider.state(rootViewState);
            $urlRouterProvider.otherwise(rootViewState.url);
        }
    }
);