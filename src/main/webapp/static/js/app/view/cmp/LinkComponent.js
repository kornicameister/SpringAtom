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

Ext.define('SA.view.cmp.LinkComponent', function () {
	var getIcon = function (icon) {
		var iconCfg = undefined;
		if (icon) {
			if (icon['cls']) {
				iconCfg = {
					tag: 'i',
					cls: icon['cls']
				}
			} else if (icon['path']) {
				iconCfg = {
					tag: 'img',
					src: icon['path']
				}
			}
		}
		if (iconCfg) {
			return Ext.apply({
				xtype: 'component'
			}, iconCfg);
		}
		return {};
	};
	return {
		extend     : 'SA.view.cmp.GuiComponent',
		alias      : 'widget.linkComponent',
		href       : undefined,
		label      : undefined,
		constructor: function (config) {
			var me = this,
				data = config['data'] || {},
				meta = config['meta'],
				icon = undefined,
				localConfig = {};

			if (!meta) {
				Ext.Error.raise('Cannot create TextComponent if config[meta] is missing');
			}

			localConfig = Ext.apply({
				meta: meta
			}, localConfig);

			function createAutoEl() {
				localConfig = Ext.apply({
					autoEl: {
						tag: 'span',
						cls: 'x-ip-attr',
						cn : {
							tag : 'span',
							cls : 'x-ip-attr-label',
							html: meta['label'],
							cn  : [
								{
									tag: 'p',
									cls: 'x-ip-attr-value',
									cn : [
										{
											tag   : 'a',
											html  : data['linkLabel'],
											href  : data['value'],
											target: data['target']
										},
										getIcon(data['icon'])
									]
								}
							]
						}
					}
				}, localConfig);
			}

			createAutoEl();

			me.callParent([localConfig]);
		}
	}
});
