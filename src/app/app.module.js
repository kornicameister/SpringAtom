angular
    .module('sg.app', [
        'sg.app.view',
        'sg.app.components',
        'sg.app.popups',
        'angular-growl'
    ])
    .value('$anchorScroll', angular.noop)
    .config(['$locationProvider', function ($locationProvider) {
        $locationProvider.html5Mode(true);
    }])
    .config(['$stickyStateProvider', 'DEBUG_MODE', function ($stickyStateProvider, DEBUG_MODE) {
        $stickyStateProvider.enableDebug(DEBUG_MODE);
    }])
    .config(['$compileProvider', 'DEBUG_MODE', function ($compileProvider, DEBUG_MODE) {
        $compileProvider.debugInfoEnabled(DEBUG_MODE);
    }]);