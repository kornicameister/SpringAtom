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

(function SpringAtom() {
	"use strict";

	var app = angular.module('springatom', [
			"ui.bootstrap",
			'ngAnimate',
			'ngGrid',
			'springatom.nav',
			'springatom.component',
			'springatom.component.menu',
			'springatom.grids',
			'springatom.infopage',
			'springatom.wiz'
		]),
		generalConf = function configureApp($httpProvider) {
			$httpProvider.defaults.headers.common['SA-APP'] = 'SpringAtom';
		},
		urlHelperProvider = function URLHelperProvider() {
			var _urlParams = function (url) {
					if (!url || (url.indexOf("?") < 0 && url.indexOf("&") < 0)) {
						return {};
					}
					var params = url.substr(url.indexOf("?") + 1);
					return _urlDecode(params);
				},
				_urlDecode = function (string, overwrite) {
					var obj = {},
						pairs = string.split('&'),
						name,
						value;
					angular.each(pairs, function (pair) {
						pair = pair.split('=');
						name = decodeURIComponent(pair[0]);
						value = decodeURIComponent(pair[1]);
						obj[name] = overwrite || !obj[name] ? value : [].concat(obj[name]).concat(value);
					});
					return obj;
				},
				_isDebug = function (url) {
					url = url || window.location.href;
					var params = _urlParams(url);
					return params['debug']
				};

			this.$get = function () {
				return {
					urlParams: _urlParams,
					urlDecode: _urlDecode,
					isDebug  : _isDebug
				}
			}
		};

	app.config(['$httpProvider', generalConf])
		.provider('urlHelper', urlHelperProvider);

	(function initStringExtension() {
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
		}
	}());

}());