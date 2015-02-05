define(
    [
        './rootView.controller',
        './header/headerView.controller'
    ],
    function rootViewState() {
        "use strict";
        // TODO make it abstract and proceed
        return {
            name : 'sg',
            url  : '/',
            views: {
                // TODO change file and pckg name
                content   : {
                    templateUrl : 'common/rootView/rootView.tpl.html',
                    controller  : 'RootViewController',
                    controllerAs: 'rvctrl'
                },
                breadcrumb: {
                    templateUrl: 'common/rootView/breadcrumb/breadcrumb.tpl.html'
                },
                header    : {
                    templateUrl : 'common/rootView/header/headerView.tpl.html',
                    controller  : 'RootViewHeaderController',
                    controllerAs: 'hrctrl'
                },
                footer    : {
                    templateUrl: 'common/rootView/footer/footer.tpl.html'
                },
                navigation: {
                    templateUrl: 'common/rootView/navigation/navigation.tpl.html'
                }
            }
        };
    }
);