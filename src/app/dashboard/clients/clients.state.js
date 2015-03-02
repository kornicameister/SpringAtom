define(
    [
        './clients.module.js'
    ],
    function (module) {
        "use strict";
        return module.config(['$stateHelperProvider', state]);

        function state($stateHelperProvider) {
            $stateHelperProvider.state({
                name             : 'sg.dashboard.clients',
                url              : '/clients',
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
                        return $translate('sg.dashboard.clients')
                    }]
                },
                deepStateRedirect: true,
                sticky           : true,
                views            : {
                    'clients': {
                        template: 'Clients'
                    }
                }
            })
        }
    }
);