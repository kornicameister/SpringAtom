define(
    [
        './home.module',
        'common/navigation/navigation.provider'
    ],
    function (module) {
        "use strict";
        return module.config(['$stateHelperProvider', '$urlRouterProvider', '$navigationProvider', function ($stateHelperProvider,
                                                                                                              $urlRouterProvider,
                                                                                                              $navigationProvider) {
            $stateHelperProvider.state({
                name : 'sg.home',
                url  : '/home',
                resolve: {
                    label: ['$translate', function ($translate) {
                        return $translate('sg.home')
                    }],
                },
                views: {
                    'content@': {
                        template: '<h1>TEST</h1>'
                    }
                }
            });

            $navigationProvider.navigation('sg.home', [
                'sg.home',
                'sg.about',
                'sg.dashboard'
            ]);

            $urlRouterProvider.otherwise('/sg/home');
        }]);

    }
);