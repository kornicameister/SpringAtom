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
			.controller('RootViewHeaderController', rootViewHeaderController)
            .controller('RootViewController', rootViewCtrl)
            .config(['$stateBuilder', registerRootState]);

        function registerRootState($stateBuilder) {
            $stateBuilder.state(rootViewState);
        }
    }
);