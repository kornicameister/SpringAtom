define(
    [
        'angular',
        'common/state/state.module'
    ],
    function breadcrumbModule(angular) {
        "use strict";
        /**
         * @module sg.app.components.breadcrumb
         */
        return angular.module('sg.components.breadcrumb', ['sg.state'])
    }
);