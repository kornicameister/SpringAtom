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

Ext.define('SA.controller.GridController', function () {
	function buildExtraParamsObject(context, builderId, fields) {
		return Ext.apply(context, {
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

	return {
		id                  : 'gridController',
		extend              : 'Ext.app.Controller',
		requires            : [
			'SA.model.Persistable',
			'SA.store.DynamicGridStore'
		],
		views               : ['cmp.GridComponent'],
		refs                : [
			{ ref: 'ipGrid', selector: 'infoPageGrid', autoCreate: false}
		],
		init                : function () {
			Ext.log('GridController initializing...');
			var me = this;
			me.control({
				'infoPageGrid': {render: me.onInfoPageGridRender}
			});
		},
		onInfoPageGridRender: function (grid) {
			Ext.log({level: 'debug', msg: 'Grid rendered, commencing loading configuration and data'});
			var context = grid.getContext(),
				builderId = grid.getBuilderId(),
				cfgUrl = grid.getConfigurationUrl(),
				dataUrl = grid.getDataUrl();

			Ext.log({level: 'debug', msg: 'Loading configuration from href :: ' + cfgUrl['href']});

			Ext.Ajax.request({
				url    : cfgUrl,
				method : 'GET',
				success: function (response) {
					var bean = Ext.JSON.decode(response.responseText),
						metaData = bean['metaData'],
						store = Ext.create('SA.store.DynamicGridStore', {
							fields: metaData['fields'],
							proxy : {
								type       : 'richproxy',
								url: dataUrl,
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
						grid.add(gridConfig);
					} catch (error) {
						Ext.Msg.alert('Failure in grid rendering', error.stack);
					}
				}
			});

		}
	}
});