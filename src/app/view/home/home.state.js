angular.module('sg.app.view.home')
    .config(['$stateHelperProvider', '$urlRouterProvider', function ($stateHelperProvider, $urlRouterProvider) {
        $stateHelperProvider.state({
            name     : 'sg.home',
            url      : '/home',
            navigator: [
                'sg.home',
                'sg.dashboard',
                'sg.about'
            ],
            resolve  : {
                label: ['$translate', function ($translate) {
                    return $translate('sg.home')
                }]
            },
            views    : {
                'content@': {
                    template: '<h1>TEST</h1>'
                }
            }
        });
        $urlRouterProvider.otherwise('/sg/home');
    }]);
