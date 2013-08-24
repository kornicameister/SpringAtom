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

Ext.define('SA.view.calendar.Panel', {
    extend       : 'Ext.panel.Panel',
    alias        : 'widget.calendar_panel',
    requires     : [
        'Ext.layout.container.Card'
    ],
    statics      : {
        VIEW_MODE: {
            DAY  : 0,
            MONTH: 1,
            YEAR : 2
        },
        VIEW_TEXT: {
            DAY  : 'Day',
            MONTH: 'Month',
            YEAR : 'Year'
        }
    },
    /**
     * @cfg layoutConfig
     * @private
     */
    layoutConfig : {
        layoutOnCardChange: true,
        deferredRender    : true
    },
    currentView  : 0,
    startDate    : new Date(),
    initComponent: function () {
        var me = this;
        me.callParent([arguments]);
    }
});