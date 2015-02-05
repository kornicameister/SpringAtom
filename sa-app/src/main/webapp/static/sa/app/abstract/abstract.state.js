define(
    [
        './header/header.controller'
    ],
    function rootViewState() {
        "use strict";
        return {
            name    : 'sg',
            abstract: true,
            views: {
                content   : {}, // empty to be redefined by substates
                header    : {
                    templateUrl : 'app/abstract/header/header.tpl.html',
                    controller  : 'HeaderController',
                    controllerAs: 'headerCtrl'
                },
                breadcrumb: {
                    templateUrl : 'app/abstract/breadcrumb/breadcrumb.tpl.html'
                },
                footer    : {
                    templateUrl: 'app/abstract/footer/footer.tpl.html'
                },
                navigation: {
                    templateUrl: 'app/abstract/navigation/navigation.tpl.html'
                }
            }
        };
    }
);