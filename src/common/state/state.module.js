angular.module('sg.common.state', [
    'sg.common.utils',
    'sg.common.log',
    'ui.router',
    'ct.ui.router.extras'
]);
(function () {
    angular.module('sg.common.state')
        .value('$anchorScroll', angular.noop)
        .run(['$state', 'DEBUG_MODE', '$log', '$timeout', listStatesIfDebug])
        .run(['$statePageTitle', '$stateHelper', '$rootScope', sgStateRun]);

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

    /**
     *
     * @param $statePageTitle
     * @param $stateHelper
     * @param $rootScope
     */
    function sgStateRun($statePageTitle, $stateHelper, $rootScope) {
        if ($statePageTitle.isPageTitleChangeEnabled()) {

            var expression = $statePageTitle.getPageTitleExpression();

            $rootScope[expression] = $statePageTitle.getDefaultPageTitle() || SA_DEFAULT_PAGE_TITLE;

            $rootScope.$on('$stateChangeSuccess', function (event, toState) {
                $stateHelper.getStateLabel(toState).then(function (label) {
                    $rootScope[expression] = label;
                    if (!$rootScope.$$phase) {
                        $rootScope.$apply();
                    }
                })
            });
        }
    }
}());