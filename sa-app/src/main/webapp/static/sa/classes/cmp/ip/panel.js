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
        'classes/cmp/component',
        'classes/cmp/ip/attribute',
        'jsface'
    ],
    function attribute(Component, InfoPageAttribute) {
        var unwrapIcon = function unwrapIcon(icon) {
                if (icon) {
                    return icon['iconCls'];
                }
                return undefined;
            },
            unwrapAttributes = function unwrapAttributes(attributes, data) {
                var local = [];
                if (!attributes || attributes.length < 1) {
                    return local;
                }
                angular.forEach(attributes, function (attr) {
                    var valueOfAttr = data[attr['path']];
                    local.push(new InfoPageAttribute(attr, valueOfAttr));
                });
                return local;
            };

        return Class(Component, function () {
            return {
                constructor  : function (config, data) {
                    this.icon = unwrapIcon.call(this, config['iconCfg']);
                    this.label = config['label'];
                    this.position = config['position'];
                    this.attributes = unwrapAttributes.call(this, config['content'], data);
                    this.$class.$super.call(this, config);    // Invoke parent's constructor
                },
                /**
                 * Returns position of the panel
                 * @return {number}
                 */
                getPosition  : function () {
                    return this.position;
                },
                /**
                 * Returns label of the panel
                 * @return {string}
                 */
                getLabel     : function () {
                    return this.label;
                },
                /**
                 * Returns icon CSS class
                 * @return {string}
                 */
                getIcon      : function () {
                    return this.icon;
                },
                /**
                 * Returns attributes of the panel
                 * @return {attributes|*}
                 */
                getAttributes: function () {
                    return this.attributes;
                }
            }
        });
    }
);
