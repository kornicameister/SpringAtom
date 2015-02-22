define(
    [
        './lifts.module.js'
    ],
    function (module) {
        "use strict";
        return module.config(['$stateHelperProvider', state]);

        function state($stateHelperProvider) {
            $stateHelperProvider.state({
                name             : 'sg.dashboard.lifts',
                url              : '/lifts',
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
                        return $translate('sg.dashboard.lifts')
                    }]
                },
                deepStateRedirect: true,
                sticky           : true,
                views            : {
                    'lifts': {
                        template: 'Lifts'
                    }
                }
            })
        }
    }
);