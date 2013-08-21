/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2013]                   *
 *                                                                                                *
 * [SpringAtom] is free software: you can redistribute it and/or modify                           *
 * it under the terms of the GNU General Public License as published by                           *
 * the Free Software Foundation, either version 3 of the License, or                              *
 * (at your option) any later version.                                                            *
 *                                                                                                *
 * [SpringAtom] is distributed in the hope that it will be useful,                                *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of                                 *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                                  *
 * GNU General Public License for more details.                                                   *
 *                                                                                                *
 * You should have received a copy of the GNU General Public License                              *
 * along with [SpringAtom].  If not, see <http://www.gnu.org/licenses/gpl.html>.                  *
 **************************************************************************************************/

Ext.define('SA.view.SADashboard', {
    extend       : 'Ext.container.Viewport',
    requires     : [
        'Ext.container.Viewport',
        'SC.core.mixing.SLocalizedComponent',
        'SA.view.dashboard.SADashboardPanel'
    ],
    mixins       : {
        localized: 'SC.core.mixing.SLocalizedComponent'
    },
    getLazyConfig: function () {
        return {
            id    : 'sa-viewport',
            layout: 'border',
            items : {
                id        : 'sa-dashboard',
                xtype     : 'panel',
                title     : '${sa-dashboard.title}$',
                region    : 'center',
                layout    : 'border',
                bodyBorder: false,
                defaults  : {
                    collapsible : false,
                    split       : false,
                    animCollapse: false,
                    bodyPadding : 5
                },
                items     : [
                    {
                        id    : 'sa-dashboard-header',
                        xtype : 'box',
                        region: 'north',
                        title : '${sa-dashboard-header.title}$'
                    },
                    {
                        id          : 'sa-dashboard-options',
                        title       : '${sa-dashboard-options.title}$',
                        region      : 'west',
                        width       : 200,
                        minWidth    : 150,
                        maxWidth    : 400,
                        layout      : {
                            type   : 'accordion',
                            animate: true
                        },
                        split       : true,
                        collapsible : true,
                        animCollapse: true,
                        collapsed   : true,
                        items       : [
                            {
                                id        : 'sa-dashboard-options-navigation',
                                title     : '${sa-dashboard-options-navigation.title}$',
                                autoScroll: true,
                                border    : false,
                                iconCls   : 'nav'
                            },
                            {
                                id        : 'sa-dashboard-options-settings',
                                title     : '${sa-dashboard-options-settings.title}$',
                                border    : false,
                                autoScroll: true,
                                iconCls   : 'settings'
                            }
                        ]
                    },
                    {
                        id    : 'sa-dashboard-panel',
                        xtype : 'dasboardpanel',
                        region: 'center'
                    }
                ]
            }
        };
    },
    initComponent: function () {
        var me = this,
            localize = me['mixins']['localized'];
        localize.applyLocalization({
            container: me,
            cfg      : me.getLazyConfig(),
            paths    : {
                'items > title'                            : '${sa-dashboard.title}$',
                'items > items > [0] > title'              : '${sa-dashboard-header.title}$',
                'items > items > [1] > title'              : '${sa-dashboard-options.title}$',
                'items > items > [1] > items > [0] > title': '${sa-dashboard-options-navigation.title}$',
                'items > items > [1] > items > [1] > title': '${sa-dashboard-options-settings.title}$'
            }
        });
        this.callParent(arguments);
    }
}, SC.logObjectCreated);
