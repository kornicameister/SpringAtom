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
 * Created by trebskit on 2014-09-06.
 */
define(
    [
        'common/filters/src/booleanFilter'
    ],
    function restValue() {
        return {
            name      : 'restValue',
            definition: function restValue($filter) {
                return {
                    restrict: 'A',
                    scope   : {
                        template      : '=tpl',
                        templateMethod: '&method',
                        value         : '=value'
                    },
                    link    : function (scope, element) {
                        var tpl = scope.template,
                            tplMethod = scope.templateMethod;
                        if (_.isUndefined(tpl) && !_.isObject(scope.value)) {
                            if (_.isBoolean(scope.value)) {
                                scope.value = $filter('booleanFilter')(scope.value);
                            }
                            element.text(scope.value);
                        } else {
                            scope.$watch('value', function (val, oldValue) {
                                if (val !== oldValue || !_.isEqual(val, oldValue)) {
                                    if (!_.isUndefined(tpl)) {
                                        element.text(val[tpl])
                                    } else {
                                        var tt = tplMethod(scope)(val);
                                        if (!_.isUndefined(tt)) {
                                            tt = angular.element(tt);
                                            element.append(tt);
                                        }
                                    }
                                }
                            }, true);
                        }
                    }
                }
            }
        }
    }
);