define(
    'springatom',
    [
        'angular',
        'app/app.module',
        'common/common.module'
    ],
    function app(angular) {

        (function verifyAngularExists() {
            if (!(angular && window.angular)) {
                throw new Error('Angular has not been loaded so far, therefore there is no possibility to run the application');
            }
        }());

        angular
            .module('springatom', [
                'sg.app',
                'sg.common'
            ])
            .constant('ApplicationName', 'SpringAtom')
            .constant('Version', '0.0.1');

        return {
            init: function () {
                setTimeout(function bootstrapOnTimeout() {
                    var appName = 'springatom';
                    console.log('Bootstrapping application + ' + appName);
                    window.name = 'NG_DEFER_BOOTSTRAP';
                    angular.bootstrap(document, [appName]);
                }, 100);
            }
        }
    }
);
