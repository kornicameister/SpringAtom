define(
    [
        'angular',
        'common/state/state.module',
        // app views
        './abstract/abstract.module',
        './home/home.module'
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
        return angular.module('sg.app', [
            'sg.state',
            'sg.app.index',
            'sg.app.home'
        ]);
    }
);