/**
 * Created by Tomasz on 2015-01-07.
 */
define(
    [
        'angular',
        'common/directives/directives',
        'common/filters/filters',
        'common/dialogs/dialogs'
    ],
    function components(angular) {
        /**
         * <b>sg.components</b> is top level module for all global components:
         * - directives
         * - controllers
         * - views
         * - states
         *
         * that cannot be bound to specific functional part of the application
         *
         * @module sg.common
         */
        return angular.module('sg.common', [
            'sg.common.directives',
            'sg.common.filters',
            'sg.common.dialogs'
        ]);
    }
);