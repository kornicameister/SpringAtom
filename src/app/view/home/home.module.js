angular
    .module('sg.app.view.home', [
        'sg.common.state',
        'sg.common.log'
    ])
    .run(['$log', function ($log) {
        $log.getInstance('sg.app.view.home').debug('sg.app.view.home initialized...');
    }]);