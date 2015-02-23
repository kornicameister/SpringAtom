define(
    [
        'common/state/state.module',
        './adminView.controller'
    ],
    function adminState(module) {
        "use strict";
        return module.config(['$stateHelperProvider', state]);

        function state($stateHelperProvider) {
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
                        controller  : 'AdminViewController',
                        controllerAs: 'vm',
                        templateUrl : 'app/admin/admin.tpl.html'
                    }
                }
            })
        }
    }
);