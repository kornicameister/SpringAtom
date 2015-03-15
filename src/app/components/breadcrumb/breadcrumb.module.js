define(
    'app/components/breadcrumb/breadcrumb.module',
    [
        'angular',
        'common/state/state.module'
    ],
    function breadcrumbModule(angular) {
        "use strict";
        /**
         * @module sg.app.components.breadcrumb
         */
        return angular.module('sg.app.components.breadcrumb', ['sg.state'])
    }
);