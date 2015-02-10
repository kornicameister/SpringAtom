/**
 * @name sg.app.components
 * @namespace sg.app
 * @author kornicameister@gmail.com
 */
define(
    [
        'angular',
        './components.module.wrapper'
    ],
    function (angular) {
        /**
         * @ngdoc module
         * @memberOf sg.app
         * @module sg.app.components
         * @description
         *  <b>sg.app.components</b> serves as a wrapping module
         *  for all components used strictly in view state to define
         *  entire visible part.
         */
        return angular.module('sg.app.components', [
            'sg.app.components.breadcrumb',
            'sg.app.components.header',
            'sg.app.components.footer',
            'sg.app.components.navigation'
        ]);
    }
);