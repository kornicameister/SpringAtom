define(
    ['./abstract.module'],
    function rootViewState(module) {
        "use strict";
        return module.config(['$stateHelperProvider', function ($stateHelperProvider) {
            $stateHelperProvider.state({
                name    : 'sg',
                url     : '/sg',
                abstract: true,
                resolve : {
                    auth: ['securityService', function (securityService) {
                        return securityService.getAuth();
                    }]
                },
                views   : {
                    content: {}, // main content to be redefined by substates
                    footer : {}, // extra content in the footer, check footer.directive
                    nav    : {}  // extra content in the navigation, check navigation.directive
                },
                onEnter : function (auth) {

                }
            });
        }]);

    }
);