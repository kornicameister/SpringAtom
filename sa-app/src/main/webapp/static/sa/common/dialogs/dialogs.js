define(
    [
        'angular',
        'common/dialogs/login/def',
        'common/dialogs/newBrandModel/def'
    ],
    function dialogs(angular) {
        /**
         * <b>sg.common.dialogs</b> wraps all dialogs in a single module.
         * In order to use any dialogs defined this module can be defined as
         * a dependency of an another one
         *
         * @module sg.common.dialogs
         */
        return angular.module('sg.common.dialogs', [
            'sg.common.dialogs.login',
            'sg.common.dialogs.newBrandModel'
        ]);
    }
);