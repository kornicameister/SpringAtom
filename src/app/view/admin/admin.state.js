angular.module('sg.app.view.admin').config(['$stateHelperProvider', function ($stateHelperProvider) {
    $stateHelperProvider.state({
        name     : 'sg.admin',
        url      : '/admin',
        navigator: [
            'sg.admin.language',
            'sg.admin.settings',
            'sg.admin.users'
        ],
        resolve  : {
            label: ['$translate', function ($translate) {
                return $translate('sg.admin')
            }]
        },
        views    : {
            'content@': {
                controller  : 'AdminController',
                controllerAs: 'vm',
                templateUrl : 'app/view/admin/admin.tpl.html'
            }
        }
    })
}]);

