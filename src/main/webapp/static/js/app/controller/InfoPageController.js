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

Ext.define('SA.controller.InfoPageController', {
	id              : 'infoPageController',
	extend          : 'Ext.app.Controller',
	views           : ['infopage.InfoPage'],
	refs            : [
		{ ref: 'page', selector: 'infoPage', autoCreate: false}
	],
	init            : function () {
		Ext.log('InfoPage controller initializing...');
		var me = this;
		me.control({
			'infoPage': {
				beforerender: me.onInfoPageRender
			}
		});
	},
	loadInfoPage    : function (config) {
		Ext.log('InfoPageController#loadInfoPage(config=' + config + ')');
		Ext.widget('infoPage', config);
	},
	onInfoPageRender: function () {
		Ext.Logger.info('onInfoPagerRender');
		var me = this,
			infoPage = me.getPage(),
			panels = new Ext.util.MixedCollection(false, function (item) {
				return item['position'];
			}),
			items = [];

		Ext.each(infoPage.getPanels(), function (value) {
			panels.add(value);
		});
		panels.sortByKey('ASC');

		panels.each(function (panel) {
			var title = panel['title'],
				layout = panel['layout'] === 'horizontal' ? 'hbox' : 'vbox';

			delete panel['title'];
			delete panel['layout'];

			panel = Ext.apply(panel, {
				title   : title,
				parent  : infoPage.id,
				request : infoPage.getRequest(),
				url     : infoPage.getUrl(),
				autoShow: items.length === 0
			});
			items.push(panel);
		});

		infoPage.add(items);
	}
});