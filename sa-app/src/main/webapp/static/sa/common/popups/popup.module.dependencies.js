define(
    [
        'angular',
        'common/state/state.module',
        'angularUiBootstrap'
    ],
    function angular(angular){
        return angular.module('sg.popups.dependencies', [
            'sg.state',
            'ui.bootstrap.modal',
            'ui.bootstrap.tpls'
        ]);
    }
);