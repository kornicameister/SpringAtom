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
        'config/module',
        'config/directives',
        'config/states',
        'config/localStorage',
        'config/rest',
        'config/filters',
        'config/lang',
        'config/ext',
        'jsface'
    ],
    function app(module, directives, states, localStorage, rest, filters) {

        // load parts of the application
        states.configure();
        directives.configure();
        localStorage.configure();
        rest.configure();
        filters.configure();
        // load parts of the application

        return {
            init: function () {
                setTimeout(function () {
                    console.log('Bootstrapping application + ' + module.name);
                    window.name = 'NG_DEFER_BOOTSTRAP';
                    angular.bootstrap(document, [module.name])
                }, 100);
            }
        }
    }
);
