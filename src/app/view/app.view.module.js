angular
    .module('sg.app.view', [
        'sg.app.view.index',
        'sg.app.view.home',
        'sg.app.view.about',
        'sg.app.view.admin',
        'sg.app.view.dashboard',
        // sg.common.log
        'sg.common.log'
    ])
    .run(['$log', function ($log) {
        $log.getInstance('sg.app.view').debug('sg.app.view initialized...');
    }]);