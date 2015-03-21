angular
    .module('springatom', [
        'sg.app',
        'sg.common.translations'
    ])
    .constant('ApplicationName', 'SpringAtom')
    .constant('Build', '0.0.1')
    .constant('Version', '0.0.1');