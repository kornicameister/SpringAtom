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

(function () {
    Ext.Loader.setConfig({
        enabled         : true,
        garbageCollect  : true,
        scriptChainDelay: true,
        paths           : {
            'SA': '/static/sa/application'
        }
    });
    Ext.application({
        id             : 'SA-01',
        name           : 'SA',
        appFolder      : 'static/sa/application',
        appProperty    : 'app',
        enableQuickTips: true,
        controllers    : [
            'SAController'
        ],
        views          : [
            'SADashboard',
            'dashboard.Panel',
            'dashboard.Navigator',
            'dashboard.navigator.Search',
            'dashboard.Container',
            'dashboard.navigator.TreeMenu'
        ],
        launch         : function () {
            Ext.state.Manager.setProvider(new Ext.state.LocalStorageProvider());
            Ext.create('SA.view.SADashboard');
        }
    });
}());
