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
                'SC.core': 'static/sa/core'
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
                initTypes();
                initLoader();
                initGlobals();
            });
    }

    function initTypes() {
        Ext.data.Types.OARRAY = {
            type   : 'oarray',
            convert: function (v, data) {
                console.log(v);
                console.log(data);
                return new Ext.Array.from(data);
            }
        }
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
        };
        sa.Locales = function (key) {
            var records = Ext.getStore('SLocaleStore').getRange(),
                value = undefined;

            Ext.each(records, function (record) {
                return Ext.each(record.getPreferences(), function (pref) {
                    if (pref.get('key').equals(key)) {
                        value = pref.get('value');
                        return false;
                    }
                    return true;
                });
            });

            if (!Ext.isDefined(value)) {
                value = 'no_locale_found';
            }

            return Ext.String.format('{0}', value);
        };
    }

    function initJS() {
        String.prototype.equals = function (str) {
            return this == str;
        }
    }

    initJS();
    initUXPath();
    initCore();
}());