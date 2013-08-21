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


/**
 * @class SC.locale.Locale
 * @description SC.locale.Locale serves all locale-based tasks
 */
Ext.define('SC.core.SLocale', function () {
    var loadAvailableLocales = function () {
            var me = this;
            Ext.Ajax.request({
                url    : '/app/lang/available',
                method : 'GET',
                success: function (response) {
                    var text = response.responseText,
                        data = Ext.decode(text),
                        locales = [];
                    Ext.each(data, function (dd) {
                        var locale = Ext.create('SC.core.locale.SLocaleModel', dd);
                        if (locale.get('set')) {
                            me.setLocale(locale.get('tag'));
                        }
                        Ext.Array.push(locales, locale);
                    });
                    me.setAvailableLocales(locales);
                    SC.core.SLogger.debug(locales);
                },
                failure: function (response) {
                    var obj = Ext.decode(response.responseText);
                    SC.core.SLogger.error(obj);
                }
            });
        },
        onLocalesLoad = function (store, data, successful) {
            if (!Ext.isDefined(successful)) {
                successful = false;
            }
            if (successful) {
                SC.core.SLogger.debug('Loaded successful, available locales found');
            } else {
                SC.core.SLogger.error('Loaded unsuccessful', "Failed to load data");
            }
        },
        loadLocalization = function (localeStore) {
            Ext.Ajax.request({
                url    : '/app/lang/read',
                method : 'get',
                async  : false,
                success: function (response) {
                    var decoded = Ext.decode(response.responseText);

                    if (Ext.isObject(decoded)) {
                        decoded = [decoded];
                    }

                    Ext.each(decoded, function (rec) {
                        console.log(localeStore.add([decodeRecord(rec)]));
                    });
                }
            });
        },
        decodeRecord = function (object) {
            if (!Ext.isDefined(object) || object === null) {
                return [];
            }
            var key = 'paramName',
                locale = 'locale',
                preferences = 'preferences',
                wrapper = {};
            Ext.iterate(object, function (node, value) {
                if (node === key) {
                    wrapper[key] = value;
                } else if (node === locale) {
                    wrapper[locale] = value;
                } else if (node === preferences) {
                    wrapper[preferences] = (function () {
                        var tmp = [];
                        Ext.each(value, function (item) {
                            Ext.Array.push(tmp, item);
                        });
                        return tmp;
                    }());
                }
            });
            return wrapper;
        },
        initEventsListeners = function () {
            var me = this;
            me.addListener('localesLoad', onLocalesLoad, me);
        };
    return {
        singleton  : true,
        requires   : [
            'Ext.util.Observable',
            'Ext.data.Store',
            'SC.core.SLogger',
            'SC.core.locale.SLocaleStore'
        ],
        mixins     : {
            observable: 'Ext.util.Observable'
        },
        config     : {
            availableLocales: undefined,
            locale          : 'pl_PL'
        },
        constructor: function (cfg) {
            var me = this;

            cfg = {} || cfg;

            me.mixins.observable.constructor.call(me, cfg);
            me.addEvents(
                'localesLoad',
                'localesChanged'
            );

            Ext.create('SC.core.locale.SLocaleStore', {
                listeners: {
                    load: {
                        scope: me,
                        fn   : onLocalesLoad
                    }
                }
            });

            me.initConfig({
                locale          : 'pl_PL',
                availableLocales: []
            });

            initEventsListeners.apply(me);
            loadAvailableLocales.apply(me);
            loadLocalization.apply(me, [Ext.getStore('SLocaleStore')]);

            me.callParent([cfg]);
        }
    }
});