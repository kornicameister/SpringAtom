define(
    [
        'angular',
        'common/state/state.module',
        'common/navigation/navigation.module',
        './abstract/abstract.module.wrapper',
        './home/home.module.wrapper',
        './about/about.module.wrapper'
    ],
    function app(angular) {
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
            'sg.navigation',
            'sg.state',
            'sg.app.index',
            // core view modules of the application
            'sg.app.home',
            'sg.app.about'
        ]);

        angular.module('sg.app').config(['$statePageTitleProvider', function ($statePageTitleProvider) {
            "use strict";
            $statePageTitleProvider.setPageTitleExpression('sgAppPageTitle');
        }]);

        return angular.module('sg.app');
    }
);