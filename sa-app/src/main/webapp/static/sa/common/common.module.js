define(
    [
        'angular',
        './security/security.module',
        './translations/translations',
        './components/components.module',
        './popups/popups.wrapper'
    ],
    function sgCommonModule(angular) {
        /**
         * <b>sg.common</b> is top level module for all global components:
         * - directives
         * - controllers
         * - views
         * - states
         *
         * that cannot be bound to specific functional part of the application
         *
         * @name sg.common module
         * @module sg.common
         * @requires sg.root-view
         */
        return angular.module('sg.common', [
            'sg.popups',
            'sg.components',
            'sg.security',
            'sg.translations'
        ]);
    }
);