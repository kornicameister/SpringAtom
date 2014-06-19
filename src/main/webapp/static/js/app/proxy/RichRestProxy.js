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

Ext.define('SA.proxy.RichRestProxy', {
	extend            : 'SA.proxy.RestProxy',
	alternateClassName: 'Ext.data.SRichProxy',
	alias             : 'proxy.richproxy',
	reader            : {
		type            : 'json',
		root            : 'data',
		successProperty : 'success',
		totalProperty   : 'total',
		messageProperty : 'message',
		/**
		 * Narrows the returned guiComponent values to simple objects containing
		 * internal values and display values. Display values will be used
		 * in converters, whereas internal values as actual records' values
		 * @param data un-parsed data
		 * @returns {Array} parsed data
		 */
		getData         : function (data) {
			var me = this,
				localData = [];

			Ext.each(data['data'], function (item) {
				var localValue = {};

				Ext.iterate(item, function (key, value) {
					localValue[key] = me.getNarrowedValue(value);
				});

				localData.push(localValue);
			});

			return Ext.apply(data, {
				data: localData
			});
		},
		getNarrowedValue: function (value) {
			return value['rawValue'];
		}
	}
});