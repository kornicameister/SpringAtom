/**
 * <b>sg.app.dashboard</b> is a submodule of the application module
 * providing dashboard capabilities allowing to access vital
 * information about the application state
 *
 * @name sg.app.dashboard
 * @namespace sg.app
 * @author kornicameister@gmail.com
 */
define(
    [
        'angular',
        'common/state/state.provider',
        // tabs
        './calendar/calendar.module.wrapper',
        './cars/cars.module.wrapper',
        './clients/clients.module.wrapper',
        './lifts/lifts.module.wrapper',
        './mechanics/mechanics.module.wrapper',
        './reports/reports.module.wrapper'
    ],
    function dashboardModule(angular) {
        "use strict";
        return angular.module('sg.app.dashboard', [
            'sg.state',
            // tabs //
            'sg.app.dashboard.calendar',
            'sg.app.dashboard.cars',
            'sg.app.dashboard.clients',
            'sg.app.dashboard.lifts',
            'sg.app.dashboard.mechanics',
            'sg.app.dashboard.reports'
        ]);
    }
);