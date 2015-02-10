define(
    [
        './home.module'
    ],
    function (module) {
        "use strict";
        return module.config(['$stateBuilderProvider', '$urlRouterProvider', function ($stateBuilderProvider,
                                                                                       $urlRouterProvider) {
            $stateBuilderProvider.state({
                name : 'sg.home',
                url  : '/home',
                label: ['$translate', function ($translate) {
                    return $translate('sg.home')
                }],
                views: {
                    'content@': {
                        template: '<h1>TEST</h1>'
                    }
                }
            });

            $urlRouterProvider.otherwise('/sg/home');
        }]);

    }
);