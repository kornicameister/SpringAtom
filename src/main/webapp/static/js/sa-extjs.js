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

Ext.Ajax.request((function () {

	function collectControllers() {
		var local = [];
		local.push('InfoPageController');
		local.push('GridController');
		return local;
	}

	function initApp(config) {
		config['name'] = 'SA';
		config = Ext.applyIf({
			appFolder      : '/app/static/js/app',
			appProperty    : '',
			enableQuickTips: true,
			controllers    : collectControllers(),
			launch         : function () {
				Ext.log(config['longName'] || 'SpringAtom' + ' is starting...');
				Ext.state.Manager.setProvider(new Ext.state.CookieProvider({ expires: new Date(new Date().getTime() + (10006060247))}));
			}
		}, config);
		Ext.log({
			level: 'debug',
			msg  : 'Application configuration => s' + Ext.JSON.encode(config)
		});
		Ext.application(config);
	}

	return {
		url    : '/app/info/meta',
		method : 'POST',
		success: function (response) {
			initApp(Ext.decode(response.responseText));
		},
		failure: function (response, opts) {
			console.log('server-side failure with status code ' + response.status);
		}
	}

}()));
