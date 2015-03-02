define(
    [
        './mechanics.module.js'
    ],
    function (module) {
        "use strict";
        return module.config(['$stateHelperProvider', state]);

        function state($stateHelperProvider) {
            $stateHelperProvider.state({
                name             : 'sg.dashboard.mechanics',
                url              : '/mechanics',
                navigator        : [
                    'sg.dashboard.calendar',
                    'sg.dashboard.cars',
                    'sg.dashboard.clients',
                    'sg.dashboard.mechanics',
                    'sg.dashboard.lifts',
                    'sg.dashboard.reports'
                ],
                resolve          : {
                    label: ['$translate', function ($translate) {
                        return $translate('sg.dashboard.mechanics')
                    }]
                },
                deepStateRedirect: true,
                sticky           : true,
                views            : {
                    'mechanics': {
                        template: 'Mechanics'
                    }
                }
            })
        }
    }
);