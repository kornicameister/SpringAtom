define(
    [
        './home.module',
        'common/navigation/navigation.provider'
    ],
    function (module) {
        "use strict";
        return module.config(['$stateBuilderProvider', '$urlRouterProvider', '$navigationProvider', function ($stateBuilderProvider,
                                                                                                              $urlRouterProvider,
                                                                                                              $navigationProvider) {
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

            $navigationProvider.navigation('sg.home', [
                'sg.about',
                'sg.dashboard'
            ]);

            $urlRouterProvider.otherwise('/sg/home');
        }]);

    }
);