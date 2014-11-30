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
 * Created by trebskit on 2014-07-30.
 */

define(
    [
        // angular deps
        'directives/header/controller'
    ],
    function directive_userMenu(headerController) {
        return [
            {
                name      : 'dropdownMenu',
                definition: function dynamicDropdownMenu() {
                    return {
                        restrict   : 'E',
                        templateUrl: '/static/sa/directives/header/userMenu/view.html',
                        replace    : true,
                        controller : headerController,
                        link       : function link(scope, element, attrs) {
                            scope.btnLabel = attrs['btnlabel'];
                            scope.btnClass = attrs['btnclass'];
                        }
                    }
                }
            },
            {
                name      : 'dropdownMenuItem',
                definition: function menuItemDirective() {
                    return {
                        restrict  : 'E',
                        transclude: true
                    }
                }
            }
        ]

    }
);
