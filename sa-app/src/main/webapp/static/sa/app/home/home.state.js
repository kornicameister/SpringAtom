define(
    [
        './home.module',
        'common/state/state.module'
    ],
    function (module) {
        "use strict";
        return module.config(['$stateHelperProvider', homeState]);

        function homeState($stateHelperProvider) {
            $stateHelperProvider.state({
                primary  : true,
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
        }
    }
);