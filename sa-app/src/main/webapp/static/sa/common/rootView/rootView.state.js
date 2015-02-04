define(
    [
        './rootView.controller',
		'./header/headerView.controller'
    ],
    function rootViewState() {
        "use strict";
        return {
            name        : 'sg',
            abstract    : true,
            templateUrl : './rootView.tpl.html',
            controller  : 'RootViewController',
            controllerAs: 'rvctrl',
            views       : {
                content   : {
                    
                },
                header    : {
					templateUrl	: './header/headerView.tpl.html',
					controller	: 'RootViewHeaderController',
					controllerAs: 'hrctrl'
				},
                footer    : {
					templateUrl	: './footer/footerView.tpl.html'
                },
                navigation: {
                    
                }
            }
        };
    }
);