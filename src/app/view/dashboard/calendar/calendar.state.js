define(
    'app/view/dashboard/calendar/calendar.state',
    [
        'app/view/dashboard/calendar/calendar.module',
        'common/state/state.stateHelperProvider'
    ],
    function (module) {
        "use strict";
        return module.config(['$stateHelperProvider', state]);

        function state($stateHelperProvider) {
            $stateHelperProvider.state({
                name             : 'sg.dashboard.calendar',
                url              : '/calendar',
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
                        return $translate('sg.dashboard.calendar')
                    }]
                },
                deepStateRedirect: true,
                sticky           : true,
                views            : {
                    'calendar': {
                        template: 'Calendar'
                    }
                }
            })
        }
    }
);