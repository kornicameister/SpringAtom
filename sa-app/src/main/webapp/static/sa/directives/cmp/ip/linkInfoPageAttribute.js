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
    [
        'underscore'
    ],
    function textInfoPageAttribute(_) {
        var getTemplate = function getTemplate() {
                var array = [];
                array.push('<div class="col-lg-2 x-ip-attr-label">{{label}}</div>');
                array.push('<div class="col-lg-9">');
                array.push('<span ng-if="href !== \'???\'"><a href="{{href}}">{{linkLabel}}</a></span>');
                array.push('<span ng-if="href === \'???\'">{{linkLabel}}</span>');
                array.push('</div>');
                return array.join('');
            },
            getLinkLabel = function getLinkLabel(attr) {
                var value = attr.value;
                if (_.isString(value)) {
                    return value;
                }
                return value.label;
            },
            getHref = function getHref(value, $state) {
                if (_.isString(value)) {
                    return '???';
                }
                return $state.href('infopage', {
                    rel: value.rel,
                    id : value.context.id
                });
            };
        return {
            name      : 'linkInfopageAttribute',
            definition: function infopageAttributeDirective() {
                return {
                    restrict  : 'E',
                    scope     : {
                        attribute: '=attribute'
                    },
                    controller: function ($scope, $state) {
                        var attr = $scope.attribute;
                        $scope.label = attr.label;
                        $scope.href = getHref(attr.value, $state);
                        $scope.linkLabel = getLinkLabel(attr);
                    },
                    template  : getTemplate()
                }
            }
        };
    }
);
