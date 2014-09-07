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

/**
 * Created by trebskit on 2014-08-11.
 */
define(
    [
        // angular deps
        'services/navigation'
    ],
    function navigatorDirective() {
        return {
            name      : 'navigator',
            definition: function ($log) {
                $log.log('navigator-directive in progress...');

                var cmpTemplate = function computeTemplate() {
                        var array = [];
                        array.push('<ul class="nav nav-pills nav-stacked">');
                        array.push('<li ng-repeat="action in data">');
                        array.push('<a id="{{action.id}}" ui-sref="{{action.name}}" role="{{action.mode}}">');
                        array.push('<i ng-show="action.iconClass" ng-class="action.iconClass"></i>');
                        array.push('{{action.label}}');
                        array.push('</a></li></ul>');
                        return array.join(' ');
                    },
                    getTemplate = function getTemplate() {
                        return cmpTemplate();
                    };

                return {
                    restrict: 'E',
                    scope   : {
                        data: '=ngModel'
                    },
                    template: getTemplate
                }
            }
        }
    }
);
