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
        'SA.view.dashboard.SADashboardPanel'
    ],
    initComponent: function () {
        Ext.apply(this,
            {
                id    : 'sa-viewport',
                layout: 'border',
                items : {
                    id        : 'sa-dashboard',
                    xtype     : 'panel',
                    title     : 'localized',
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
                            title : 'localized'
                        },
                        {
                            id          : 'sa-dashboard-options',
                            title       : 'localized',
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
                                    title     : 'localized',
                                    autoScroll: true,
                                    border    : false,
                                    iconCls   : 'nav'
                                },
                                {
                                    id        : 'sa-dashboard-options-settings',
                                    title     : 'localized',
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
            }
        );
        this.callParent(arguments);
    }
}, SA.logObjectCreated);
