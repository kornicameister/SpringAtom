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

Ext.define('SA.view.cmp.GridComponent', function () {
	return {
		extend             : 'Ext.panel.Panel',
		alias              : 'widget.infoPageGrid',
		bodyPadding        : 10,
		config             : {
			builderId: undefined,
			attribute: undefined,
			urls   : undefined,
			context: undefined
		},
		requires           : [
			'SA.component.ComponentUrls',
			'SA.component.ComponentContext'
		],
		constructor        : function (config) {
			var me = this,
				tableData = config['data'];

			delete config['data'];

			config = Ext.apply(config, {
				builderId: tableData['builderId'],
				title    : tableData['label'],
				context: Ext.create('SA.component.ComponentContext', tableData['value']),
				urls   : Ext.create('SA.component.ComponentUrls', tableData['urls'])
			}, config);

			me.callParent([config]);
		},
		getContext         : function () {
			return this.context.asObject();
		},
		getConfigurationUrl: function () {
			return this.urls.getConfigurationUrl();
		},
		getDataUrl         : function () {
			return this.urls.getDataUrl();
		},
		add                : function (grid) {
			grid = Ext.apply(grid, {
				margin: 5
			});
			this.callParent([grid]);
		}
	}
});