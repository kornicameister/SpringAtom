define(
    [
        'angular',
        // module deps
        './abstract.state',
        // angular deps
        'common/state/state.module'
    ],
    function (angular,
              abstractViewState) {
        "use strict";

        return angular.module('sg.app.index', [])
            .config(['$stateBuilderProvider', registerRootState]);

        function registerRootState($stateBuilderProvider) {
            $stateBuilderProvider.state(abstractViewState);
        }
    }
);