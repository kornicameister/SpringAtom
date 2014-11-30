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
 * Created by trebskit on 2014-08-14.
 */
define(
    function textInfoPageAttribute() {
        var getTemplate = function getTemplate() {
            var array = [];
            array.push('<div class="col-lg-2 x-ip-attr-label">{{label}}</div>');
            array.push('<div class="col-lg-9">');
            array.push('<span>{{value}}</span>');
            array.push('<icon ng-show="icon" configuration="icon"></icon>');
            array.push('</div>');
            return array.join('');
        };
        return {
            name      : 'textInfopageAttribute',
            definition: function infopageAttributeDirective() {
                return {
                    restrict: 'E',
                    scope   : {
                        attribute: '=attribute'
                    },
                    link    : function (scope) {
                        var attr = scope.attribute;
                        scope.label = attr.getLabel ? attr.getLabel() : attr.label;
                        scope.value = attr.getValue ? attr.getValue() : attr.value;
                        scope.icon = attr.getIcon ? attr.getIcon() : attr.icon;
                    },
                    template: getTemplate()
                }
            }
        };
    }
);
