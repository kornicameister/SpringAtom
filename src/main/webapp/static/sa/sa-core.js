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

(function () {

    function initUXPath() {
        Ext.Loader.setConfig({
            enabled: true,
            paths  : {
                'Ext.ux' : 'static/ux',
                'SC.core': 'static/sa/core',
                'SA.app' : '/static/sa/application'
            }
        });
    }

    function initCore() {
        Ext.require(
            [
                'SC.core.SLocale',
                'SC.core.SLogger'
            ],
            function () {
                initLoader();
                initGlobals();
                initApp();
            });
    }

    function initLoader() {
        var loader = Ext.namespace('SC');
        loader.loadScript = function (cfg) {
            if (Ext.isObject(cfg)) {
                Ext.Loader.loadScript(cfg)
            } else {
                Ext.each(cfg, function (data) {
                    Ext.Loader.loadScript(data);
                });
            }
        }
    }

    function initGlobals() {
        var sa = Ext.namespace('SC');
        sa.logObjectCreated = function (obj) {
            SC.core.SLogger.trace(
                Ext.String.format('New object has been created, name={0}', obj.getName())
            );
        }
    }

    function initApp() {
        SC.loadScript('static/sa/application/sa-app.js');
    }

    initUXPath();
    initCore();
}());