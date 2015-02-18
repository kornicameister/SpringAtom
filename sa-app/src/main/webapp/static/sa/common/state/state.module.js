define(
    [
        'angular',
        './stateLabelResolve.factory',
        './state.provider',
        './state.pageTitle.provider',
        'angularUiRouter',
        'angularUiRouterExtras'
    ],
    function stateBuilder(angular, stateLabelResolveService, $stateHelperProvider, $statePageTitleProvider) {
        /**
         *
         * @module sg.state
         * @namespace sg
         */
        return angular.module('sg.state', ['ui.router', 'ct.ui.router.extras.core'])
            .constant('MODULE_INFO', {
                name   : 'sg.state',
                version: '0.0.2'
            })
            .factory('$stateLabelResolve', stateLabelResolveService)
            .provider('$stateHelper', $stateHelperProvider)
            .provider('$statePageTitle', $statePageTitleProvider)
            .run(['$statePageTitle', '$stateHelper', '$rootScope', function ($statePageTitle,
                                                                             $stateHelper,
                                                                              $rootScope) {
                "use strict";

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
            }])
    }
);