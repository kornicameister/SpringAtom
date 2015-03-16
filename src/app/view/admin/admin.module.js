define(
    'app/view/admin/admin.module',
    [
        'angular',
        'common/state/state.module',
        'common/security/security.module'
    ],
    function adminModule(angular) {
        "use strict";
        return angular.module('sg.app.view.admin', [
            'sg.state',
            'sg.security'
        ]);
    }
);