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
 * Created by trebskit on 2014-08-15.
 */
define(
    [
        'underscore'
    ],
    function ext(_) {
        String.prototype.startsWith = function (str) {
            var length = str.length;
            return this.substring(0, length) === str;
        };
        String.prototype.endsWith = function (str) {
            var length = this.length;
            return this.substring(length - 1, length) === str;
        };
        String.prototype.removeFromBeginning = function (count) {
            return this.substring(count);
        };
        String.prototype.removeFromEnd = function (count) {
            var length = this.length;
            return this.substring(0, length - count);
        };
        String.prototype.format = function (args) {
            var newStr = this;
            for (var key in args) {
                //noinspection JSUnfilteredForInLoop
                newStr = newStr.replace('{' + key + '}', args[key]);
            }
            return newStr;
        };

        // adding using underscore as mixin
        _.mixin({
            capitalize: function (string) {
                return string.charAt(0).toUpperCase() + string.substring(1).toLowerCase();
            },
            asBoolean : function (string) {
                switch (string.toLowerCase()) {
                    case "true":
                    case "yes":
                    case "1":
                        return true;
                    case "false":
                    case "no":
                    case "0":
                    case null:
                        return false;
                    default:
                        return Boolean(string);
                }
            }
        });
    }
);
