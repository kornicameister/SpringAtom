/*
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2014]
 *
 * [SpringAtom] is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * [SpringAtom] is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with [SpringAtom].  If not, see <http://www.gnu.org/licenses/gpl.html>.
 */

define(
    [
        'angular',
        'common/utils',
        'app/root',
        'common/common'
    ],
    function app(angular, utils, appRoot, commonRoot) {

        (function applyGlobalDefaults(window) {
            console.log('Applying global defaults used in configuration');
            window.DEBUG_CONFIGURATION_HELPER = utils.isDebug();
        }(window));

        (function verifyAngularExists() {
            if (!(angular && window.angular)) {
                throw new Error('Angular has not been loaded so far, therefore there is no possibility to run the application');
            }
        }());

        function bootstrapOnTimeout() {
            var appName = utils.getModuleName(app);
            console.log('Bootstrapping application + ' + appName);
            window.name = 'NG_DEFER_BOOTSTRAP';
            angular.bootstrap(document, [appName]);
        }

        var appName = 'springatom',
            app = angular
                .module(appName, [
                    utils.getModuleName(appRoot),
                    utils.getModuleName(commonRoot)
                ])
                .constant('appName', appName);

        return {
            init: function () {
                setTimeout(bootstrapOnTimeout, 100);
            }
        }
    }
);
