define(
    [],
    function rootViewState() {
        "use strict";
        return {
            name    : 'sg',
            abstract: true,
            views: {
                content   : {}, // empty to be redefined by sub states
                header    : {
                    templateUrl: 'app/abstract/header/header.tpl.html'
                },
                breadcrumb: {
                    templateUrl: 'app/abstract/breadcrumb/breadcrumb.tpl.html'
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