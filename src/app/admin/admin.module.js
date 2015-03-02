define(
    [
        'angular',
        'common/state/state.module',
        'common/security/security.module'
    ],
    function adminModule(angular) {
        "use strict";
        return angular.module('sg.app.admin', [
            'sg.state',
            'sg.security'
        ]);
    }
);