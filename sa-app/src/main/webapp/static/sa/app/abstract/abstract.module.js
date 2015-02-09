define(
    [
        'angular',
        // module deps
        './abstract.state'
    ],
    function (angular,
              abstractViewState) {
        "use strict";

        return angular.module('sg.app.index', ['sg.state'])
            .config(['$stateBuilderProvider', registerRootState]);

        function registerRootState($stateBuilderProvider) {
            $stateBuilderProvider.state(abstractViewState);
        }
    }
);