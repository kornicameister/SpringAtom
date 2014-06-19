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

Ext.define('SA.view.infopage.InfoPage', {
	extend     : 'Ext.tab.Panel',
	alias      : 'widget.infoPage',
	requires   : [
		'SA.view.infopage.InfoPagePanel'
	],
	defaultType: 'infopagePanel',
	defaults   : {
		bodyPadding: 10
	},
	config     : {
		attributes: undefined,
		panels    : undefined,
		url       : undefined,
		request   : undefined
	},
	tabPosition: 'left',
	cls        : 'x-info-page',
	activeTab  : 0,
	constructor: function (config) {
		var me = this;

		// add events
		me.addEvents({
			/**
			 * Fired after data has been loaded from {@link ns.InfoPage#url}
			 */
			dataloaded: true
		});

		var page = config['page'],
			id = page['id'],
			content = page['content'],
			url = config['url'],
			request = config['request'];

		Ext.apply(config, {
			id        : id,
			autoRender: true,
			autoShow  : true,
			defaults  : {
				collapsible: true,
				padding    : 10,
				border     : false,
				bodyPadding: 10
			},
			panels    : content,
			url       : url,
			attributes: (function extractAttributes() {
				var attributes = [];
				Ext.each(content, function (item) {
					item = item['content'];
					Ext.each(item, function (localItem) {
						attributes.push({
							type: localItem['displayAs'],
							path: localItem['path']
						});
					});
				});
				return attributes;
			}())
		});

		if (page['title']) {
			config['title'] = title;
		}

		delete config['page'];

		me.callParent(arguments);
	}
});
