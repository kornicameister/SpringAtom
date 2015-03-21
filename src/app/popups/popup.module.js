angular.module('sg.app.popups.dependencies', [
    'sg.common.state',
    'ui.bootstrap.modal',
    'ui.bootstrap.tpls'
]);
angular.module('sg.app.popups', [
    'sg.app.popups.dependencies',
    'sg.app.popups.authentication'
]);