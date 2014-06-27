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

	var localConfig = undefined;

	function buildExtraParamsObject(context, builderId, fields) {
		return Ext.apply(context.asObject(), {
			builderId : builderId,
			attributes: (function collectAttributes() {
				var local = [];
				Ext.each(fields, function (val) {
					local.push({
						path: val['name'],
						type: val['type']
					})
				});
				return Ext.Object.toQueryString(local);
			}())
		})
	}

	function createColModel(rawColModel) {
		var colModel = [];

		Ext.each(rawColModel, function (col) {
			var tmpCol = Ext.copyTo({}, col, 'dataIndex,text,sortable,xtype,width,tooltip'),
				displayAs = col['displayAs'];
			tmpCol = Ext.applyIf(tmpCol, {
				minWidth        : 75,
				enableColumnHide: false
			});
			colModel.push(tmpCol);
		});
		return colModel;
	}

	function renderer(loader, response, active) {
		// local data
		var urls = localConfig['urls'],
			context = localConfig['context'],
			builderId = localConfig['builderId'];

		// config from response data
		var bean = Ext.JSON.decode(response.responseText),
			metaData = bean['metaData'],
			store = Ext.create('SA.store.DynamicGridStore', {
				fields: metaData['fields'],
				proxy : {
					type       : 'richproxy',
					url        : urls.getDataUrl(),
					extraParams: buildExtraParamsObject(context, builderId, metaData['fields'])
				}
			});

		delete metaData['fields'];
		var gridConfig = Ext.apply(metaData, {
			xtype      : 'grid',
			store      : store,
			columns    : createColModel(metaData['content']),
			viewConfig : {
				forceFit: true
			},
			listeners  : {
				render: function (grid) {
					grid.store.load();
				}
			},
			defaults   : {
				stateful: true
			},
			dockedItems: [
				{
					xtype      : 'pagingtoolbar',
					store      : store,   // same store GridPanel is using
					dock       : 'bottom',
					displayInfo: true
				}
			]
		});
		gridConfig = Ext.applyIf(gridConfig, {
			enableLocking: true
		});
		delete gridConfig['content'];
		Ext.iterate(gridConfig, function (key, prop) {
			if (prop == undefined || prop === -1) {
				delete gridConfig[key];
			}
		});
		try {
			this.target.add(gridConfig);
		} catch (error) {
			Ext.Msg.alert('Failure in grid rendering', error.stack);
		}
		return true;
	}

	function getLoaderConfig() {
		return {
			url     : localConfig['urls'].getConfigurationUrl(),
			loadMask: true,
			autoLoad: true,
			renderer: renderer
		}
	}

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
			'SA.store.DynamicGridStore',
			'SA.component.ComponentUrls',
			'SA.component.ComponentContext'
		],
		constructor        : function (config) {
			var me = this,
				tableData = config['data'];

			delete config['data'];

			config = Ext.apply(config, {
				builderId: tableData['builderId'],
				context: Ext.create('SA.component.ComponentContext', tableData['value']),
				urls   : Ext.create('SA.component.ComponentUrls', tableData['urls'])
			}, config);

			if (tableData['title']) {
				config['title'] = tableData['label'];
			}

			localConfig = config;
			config['loader'] = getLoaderConfig();

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