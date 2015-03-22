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
                    templateUrl: 'app/view/home/home.tpl.html'
                }
            }
        });
        $urlRouterProvider.otherwise('/sg/home');
    }]);
