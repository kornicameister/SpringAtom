define(
    [
        'angular',
        './home.state'
    ],
    function (angular, homeState) {
        "use strict";

        return angular.module('sg.app.home', [])
            .config(['$stateBuilderProvider','$urlRouterProvider', registerModuleStates]);

        function registerModuleStates($stateBuilderProvider, $urlRouterProvider) {
            $stateBuilderProvider.state(homeState);
            
            $urlRouterProvider.otherwise('/sg/home');
        }
    }
);