define(
    [
        'angular',
        'common/utils',
        'common/state/state.module',
        'common/navigation/navigation.module',
        'common/data/fireData',
        'common/security/security.module',
        // views
        './abstract/abstract.module.wrapper',
        './home/home.module.wrapper',
        './about/about.module.wrapper',
        './dashboard/dashboard.module.wrapper',
        './admin/admin.module.wrapper'
    ],
    function app(angular, utils) {
        "use strict";

        /**
         * Application module of the <b>springatom</b>.
         * This module contains visible and functional parts of the application later
         * identified as:
         *
         * - controllers
         * - view specific (local) directives + controllers
         * - states
         * - local configurations
         *
         * In order to view components of the application (global controllers, views, states etc.) go to
         * module {@link sg.common}
         *
         * @module sg.app
         */
        angular.module('sg.app', [
            // core to run app
            'sg.navigation',
            'sg.state',
            'sg.fireData',
            'sg.security',
            // abstract root view
            'sg.app.index',
            // core view modules of the application
            'sg.app.home',
            'sg.app.about',
            'sg.app.dashboard',
            'sg.app.admin'
        ]);

        angular.module('sg.app')
            .config(['$stickyStateProvider', function ($stickyStateProvider) {
                $stickyStateProvider.enableDebug(utils.isDebug());
            }])
            .config(['$statePageTitleProvider', function ($statePageTitleProvider) {
                $statePageTitleProvider.setPageTitleExpression('sgAppPageTitle');
            }])
            .config(['$securityProvider', function ($securityProvider) {
                $securityProvider
                    .enableStateSecurity(false)
                    .otherwise('/sg/accessDenied')
            }])
            .config(['$fireDataProvider', function ($fireDataProvider) {
                $fireDataProvider.setFireUrl('https://springatom.firebaseio.com/');
            }]);

        return angular.module('sg.app');
    }
);