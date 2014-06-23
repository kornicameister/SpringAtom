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


Ext.define('SA.component.ComponentUrls', function () {
	var findLink = function (urls, rel) {
		var filter = Ext.Array.filter(urls, function (item) {
			return item['rel'] === rel;
		})[0];
		if (filter['href']) {
			return filter['href'];
		}
		return filter;
	};
	return {
		config     : {
			configurationUrl: undefined,
			dataUrl         : undefined
		},
		constructor: function (config) {
			return this.initConfig({
				configurationUrl: findLink(config, 'configuration'),
				dataUrl         : findLink(config, 'data')
			})
		}
	}
});