/*
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2013]
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

Ext.onReady(function () {

	Ext.namespace('SA.infopage').loadInfoPage = function (config) {

		function doLoad() {
			if (SA.getApplication) {
				var el = config['el'].getAttribute('id');
				config = config['config'];
				SA.getApplication().getController('InfoPageController').loadInfoPage(Ext.apply({
					id      : el,
					renderTo: el,
					height  : Ext.get(el).getHeight()
				}, config));
			} else {
				setTimeout(doLoad, 1000);
			}
		}

		setTimeout(doLoad, 1000);
	};

});