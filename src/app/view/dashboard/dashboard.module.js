angular.module('sg.app.view.dashboard', [
    'sg.app.view.dashboard.calendar',
    'sg.app.view.dashboard.cars',
    'sg.app.view.dashboard.clients',
    'sg.app.view.dashboard.lifts',
    'sg.app.view.dashboard.mechanics',
    'sg.app.view.dashboard.reports',
    // allows to append module specific translations
    'pascalprecht.translate'
]);