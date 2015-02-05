define(
    [
        'angular',
        './translate/translate.module.js',
        './rootView/rootView.module',
        './security/security.module'
    ],
    function sgCommonModule(angular) {
        /**
         * <b>sg.components</b> is top level module for all global components:
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
            'sg.root-view',
            'sg.security',
            'sg.translate'
        ]);
    }
);