/**
 * <b>sg.app.view.dashboard</b> is a submodule of the application module
 * providing dashboard capabilities allowing to access vital
 * information about the application state
 *
 * @name sg.app.view.dashboard
 * @namespace sg.app
 * @author kornicameister@gmail.com
 */
define(
    'app/view/dashboard/dashboard.module',
    [
        'angular',
        'app/view/dashboard/calendar/calendar.index',
        'app/view/dashboard/cars/cars.index',
        'app/view/dashboard/clients/clients.index',
        'app/view/dashboard/lifts/lifts.index',
        'app/view/dashboard/mechanics/mechanics.index',
        'app/view/dashboard/reports/reports.index'
    ],
    function dashboardModule(angular) {
        "use strict";
        return angular.module('sg.app.view.dashboard', [
            'sg.app.view.dashboard.calendar',
            'sg.app.view.dashboard.cars',
            'sg.app.view.dashboard.clients',
            'sg.app.view.dashboard.lifts',
            'sg.app.view.dashboard.mechanics',
            'sg.app.view.dashboard.reports'
        ]);
    }
);