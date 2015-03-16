define(
    'app/app.module',
    [
        'angular',
        'common/utils',
        'common/state/state.module',
        'common/navigation/navigation.module',
        'common/data/fireData',
        'common/security/security.module',
        // components & popups
        'app/components/index',
        'app/popups/index',
        // view
        'app/view/index'
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
        return angular
            .module('sg.app', [
                // core to run app
                'sg.navigation',
                'sg.state',
                'sg.fireData',
                'sg.security',
                // core view modules of the application
                'sg.app.view',
                // popups and components
                'sg.app.components',
                'sg.app.popups'
            ])
            .config(['$stickyStateProvider', configureStickyStates])
            .config(['$statePageTitleProvider', configureStateTitle])
            .config(['$securityProvider', configureSecurity])
            .config(['$fireDataProvider', configureFireData]);

        function configureFireData($fireDataProvider) {
            $fireDataProvider.setFireUrl('https://sgatom.firebaseio.com/');
        }

        function configureSecurity($securityProvider) {
            $securityProvider
                .enableStateSecurity(false)
                .otherwise('/sg/accessDenied')
        }

        function configureStickyStates($stickyStateProvider) {
            $stickyStateProvider.enableDebug(utils.isDebug());
        }

        function configureStateTitle($statePageTitleProvider) {
            $statePageTitleProvider.setPageTitleExpression('sgAppPageTitle');
        }
    }
);