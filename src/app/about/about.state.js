define(
    [
        './about.module',
        'common/state/state.module'
    ],
    function (module) {
        "use strict";
        return module.config(['$stateHelperProvider', aboutState]);

        function aboutState($stateHelperProvider) {
            $stateHelperProvider.state({
                name     : 'sg.about',
                url      : '/about',
                navigator: [
                    'sg.home',
                    'sg.dashboard',
                    'sg.about'
                ],
                resolve  : {
                    label: ['$translate', function ($translate) {
                        return $translate('sg.about')
                    }]
                },
                views    : {
                    'content@': {
                        template: '<h1>SpringAtom</h1>'
                    }
                }
            });
        }
    }
);