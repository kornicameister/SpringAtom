define(
    'common/state/state.module',
    [
        'angular',
        'angularUiRouter',
        'angularUiRouterExtras'
    ],
    function (angular) {
        return angular.module('sg.state', ['ui.router', 'ct.ui.router.extras'])
            .run(['$statePageTitle', '$stateHelper', '$rootScope', function ($statePageTitle,
                                                                             $stateHelper,
                                                                             $rootScope) {
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
            }]);
    }
);