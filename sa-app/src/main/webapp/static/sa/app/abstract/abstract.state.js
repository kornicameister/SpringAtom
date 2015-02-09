define(
    [],
    function rootViewState() {
        "use strict";
        return {
            name    : 'sg',
            url     : '/sg',
            abstract: true,
            views: {
                content   : {}, // main content to be redefined by substates
                footer    : {}, // extra content in the footer, check footer.directive
                nav       : {}  // extra content in the navigation, check navigation.directive
            }
        };
    }
);