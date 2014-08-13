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
        'classes/cmp/abstractComponent',
        'jsface'
    ],
    function attribute(AbstractComponent) {
        /**
         * Defines InfoPageAttribute class. Used in {@link app.factory()}
         * @return {*|class} InfoPageAttribute
         */
        var defClass = function _defClass() {

            var getValueProperty = function _getValueProperty(value, prop) {
                if (!value) {
                    return undefined;
                }
                return value.hasOwnProperty(prop) ? value[prop] : undefined;
            };

            return {
                constructor: function (config, value) {
                    this.path = config['path'];
                    this.value = value;
                    this.$class.$super.call(this, config);
                },
                getPath    : function _getPath() {
                    return this.path;
                },
                getIcon    : function _getIcon() {
                    return getValueProperty(this.value, 'icon');
                },
                getType    : function _getType() {
                    return getValueProperty(this.value, 'type');
                },
                getValue   : function _getValue() {
                    return getValueProperty(this.value, 'value');
                },
                getRawValue: function _getRawValue() {
                    return getValueProperty(this.value, 'rawValue');
                }
            }
        };

        return Class(AbstractComponent, defClass);
    }
);
