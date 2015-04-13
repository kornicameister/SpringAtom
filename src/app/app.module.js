angular
    .module('sg.app', [
        'sg.app.view',
        'sg.app.components',
        'sg.app.popups',
        'sg.app.vendor'
    ])
    .value('$anchorScroll', angular.noop)
    .config(configureStickyState)
    .config(configureCompileProvider)
    .config(configureUrlRouter)
    .config(configureGrowl);

function configureGrowl(growlProvider, $httpProvider) {
    $httpProvider.responseInterceptors.push(growlProvider.serverMessagesInterceptor);
}

function configureUrlRouter($urlRouterProvider) {
    // case insensitive urls
    $urlRouterProvider.rule(function ($injector, $location) {
        var path = $location.path(),
            normalized = path.toLowerCase();

        if (path !== normalized) {
            return normalized;
        }
    });
}

function configureCompileProvider($compileProvider, DEBUG_MODE) {
    $compileProvider.debugInfoEnabled(DEBUG_MODE);
}

function configureStickyState($stickyStateProvider, DEBUG_MODE) {
    $stickyStateProvider.enableDebug(DEBUG_MODE);
}