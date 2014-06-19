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

Ext.define('SA.view.infopage.InfoPagePanel', function () {
	var getHeader = function (cfg) {
			var iconCfg = cfg['iconCfg'],
				title = cfg['title'],
				value = undefined;
			if (Ext.isDefined(iconCfg['iconCls'])) {
				value = {
					iconCls: iconCfg['iconCls']
				};
			} else if (Ext.isDefined(iconCfg['iconPath'])) {
				value = {
					icon: iconCfg['iconPath']
				};
			}
			return Ext.applyIf({
				header: {
					title     : title,
					titleAlign: 'center'
				}
			}, value || {});
		},
		getLayout = function (cfg) {
			var layoutCfg = cfg['layoutCfg'],
				layout = cfg['layout'];
			if (Ext.isDefined(layoutCfg['type'])) {
				return Ext.applyIf({
					type : 'vbox',
					pack : 'center',
					align: 'stretch'
				}, layoutCfg);
			}
			delete cfg['layoutCfg'];
			delete cfg['layout'];
			return {
				layout: layout
			}
		},
		collectParamsForLoader = function (request, attributes, parent) {
			return {
				id        : request['id'],
				domain    : request['domain'],
				version   : request['version'] || -1,
				attributes: (function () {
					var collection = [];
					Ext.each(attributes, function (value) {
						collection.push({
							path: value['path'],
							type: value['displayAs']
						});
					});
					return collection;
				}()),
				pageId    : parent
			};
		};
	return {
		extend     : 'Ext.panel.Panel',
		alias      : 'widget.infopagePanel',
		config     : {
			dynamicProperties: undefined,
			position         : undefined,
			rawAttributes    : undefined
		},
		uses       : [
			'SA.view.cmp.GridComponent',
			'SA.view.cmp.LinkComponent',
			'SA.view.cmp.TextComponent'
		],
		statics    : {
			TYPES       : {
				tableRequest    : 'infoPageGrid',
				linkGuiComponent: 'linkComponent',
				textGuiComponent: 'textComponent'
			},
			DEFAULT_TYPE: 'textComponent'
		},
		constructor: function (config) {
			var me = this,
				parent = config['parent'],
				attributes = config['content'],
				request = config['request'],
				url = config['url'];

			// clear from config
			delete config['request'];
			delete config['url'];
			delete config['content'];

			var rawAttributes = (function () {
				var collection = new Ext.util.MixedCollection();
				Ext.each(attributes, function (value) {
					collection.add(value['path'], value);
				});
				return collection;
			}());

			config = Ext.applyIf(getHeader(config), config);
			config = Ext.applyIf({
				rawAttributes: rawAttributes,
				title        : config['label'],
				loader       : {
					url        : url,
					loadMask   : true,
					autoLoad   : true,
					autoRender : true,
					ajaxOptions: {
						method  : 'POST',
						jsonData: collectParamsForLoader(request, attributes, parent)
					},
					'renderer' : function infoPageRender(loader, response) {
						Ext.log('Received message with success, msg = ' + response);
						var rawText = response['responseText'],
							attrs = new Ext.util.MixedCollection(),
							me = loader.target;

						try {
							var bean = Ext.JSON.decode(rawText),
								data = bean['data'];
						} catch (error) {
							Ext.log({
								level: 'error',
								msg  : error
							})
						}

						Ext.iterate(data, function (key, value) {
							attrs.add(key, value);
						});

						rawAttributes.eachKey(function (path, attribute) {
							var dataAttribute = attrs.get(path),
								type = undefined;
							if (!dataAttribute) {
								type = "textGuiComponent";
							} else {
								type = dataAttribute['type'];
							}
							var xtype = me.getXType(type);
							if (!xtype) {
								Ext.Error.raise(Ext.String.format('Failed to determine xtype fot type={0}', type));
							}

							me.add({
								xtype: xtype,
								meta : attribute,
								data : dataAttribute
							});

						}, me);
					}
				}
			}, config);

			me.addEvents({
				'contentload': true
			});

			me.callParent([config]);
		},
		/**
		 * Returns {@link Ext.AbstractComponent.xtype} of the gui component that will handle rendering of the data
		 * @param type type from request
		 * @returns {string} the xtype
		 */
		getXType   : function (type) {
			if (!type) {
				return SA.view.infopage.InfoPagePanel.DEFAULT_TYPE;
			}
			type = SA.view.infopage.InfoPagePanel.TYPES[type];
			if (!type) {
				type = SA.view.infopage.InfoPagePanel.DEFAULT_TYPE;
			}
			return type;
		}
	}
});
