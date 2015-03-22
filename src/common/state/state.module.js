angular.module('sg.common.state', [
    'sg.common.utils',
    'sg.common.log',
    'ui.router.compat',
    'ct.ui.router.extras'
]);
(function () {
    angular.module('sg.common.state')
        .run(['$state', 'DEBUG_MODE', '$log', '$timeout', listStatesIfDebug])
        .run(['$stateHelper', '$rootScope', 'logger', sgStateRun]);

    function listStatesIfDebug($state, DEBUG_MODE, $log, $timeout) {
        if (DEBUG_MODE) {
            $timeout(function () {
                var logger = $log.getInstance('sg.common.state'),
                    allStates = $state.get(),
                    logMsg = [];

                _.forEach(allStates, function (state) {
                    logMsg.push(_.format('\t-State [name:{n}, url:{u}]\n', {
                        n: state.name,
                        u: state.url
                    }));
                });

                logger.debug(_.format('All states\n{states}', {states: logMsg.join('')}));
            }, 1000)
        }
    }

    function sgStateRun($stateHelper, $rootScope, loggerFactory) {
        var logger = loggerFactory('sg.common.state');
        $rootScope.$on('$stateChangeSuccess', function (event, toState) {
            logger.debug(_.format('sgStateRun(state={s})', {s: toState.name}));
            $stateHelper.getStateLabel(toState).then(function (label) {
                $rootScope.PAGE_TITLE = label;
                if (!$rootScope.$$phase) {
                    $rootScope.$apply();
                }
            })
        });
    }
}());