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
    var locales = new Ext.util.MixedCollection(),
        onLocalesLoad = function (store, data, successful) {
            if (!Ext.isDefined(successful)) {
                successful = false;
            }
            if (successful) {
                SC.core.SLogger.debug('Loaded successful, available locales found');
            } else {
                SC.core.SLogger.error('Loaded unsuccessful', "Failed to load data");
            }
        };
    return {
        singleton                 : true,
        requires                  : [
            'Ext.util.Observable',
            'Ext.data.Store',
            'SC.core.SLogger',
            'SC.core.locale.SLocaleStore'
        ],
        mixins                    : {
            observable: 'Ext.util.Observable'
        },
        config                    : {
            store           : undefined,
            availableLocales: undefined,
            locale          : 'pl_PL'
        },
        constructor               : function (cfg) {
            var me = this;

            cfg = {} || cfg;

            me.mixins.observable.constructor.call(me, cfg);
            me.addEvents(
                'localesLoad',
                'localesChanged'
            );

            me.initConfig({
                store           : Ext.create('SC.core.locale.SLocaleStore', {
                    listeners: {
                        load: {
                            scope: me,
                            fn   : onLocalesLoad
                        }
                    }
                }),
                locale          : 'pl_PL',
                availableLocales: []
            });
            me.initEventsListeners();
            me.loadAvailableLocales();

            me.callParent([cfg]);
        },
        loadAvailableLocales      : function () {
            var me = this;

            Ext.Ajax.request({
                url    : '/app/lang/available',
                method : 'GET',
                success: function (response) {
                    var obj = Ext.decode(response.responseText);
                    SC.core.SLogger.debug(obj);
                },
                failure: function (response) {
                    var obj = Ext.decode(response.responseText);
                    SC.core.SLogger.error(obj);
                }
            });
        },
        loadLocalizedUiPreferences: function (cmp) {
            var me = this,
                localeStore = me.getStore(),
                pref = localeStore.findRecord('key', cmp);
            if (!Ext.isDefined(pref) || pref === null) {
                SC.core.SLogger.debug(Ext.String.format('SLocalePreferencesModel not found for key={0}, retrieving...', cmp));
                Ext.Ajax.request({
                    url    : '/app/lang/read',
                    params : {
                        key : cmp,
                        lang: me.getLocale()
                    },
                    async  : false,
                    success: function (response) {
                        var object = Ext.decode(response.responseText),
                            _pref = Ext.create('SC.core.locale.SLocalePreferencesModel', object);
                        if (!Ext.isDefined(_pref)) {
                            Ext.Error.raise(Ext.String.format('Failed to retrieve localized settings for key={0}', cmp));
                        } else {
                            SC.core.SLogger.debug(Ext.String.format('SLocalePreferencesModel retrieved from server for key={0}', cmp));
                            pref = _pref;
                        }
                    }
                });
                localeStore.add(pref);
                localeStore.sync();
                return pref;
            }
            SC.core.SLogger.debug(Ext.String.format('SLocalePreferencesModel retrieved from client for key={0}', cmp));
            return pref;
        },
        initEventsListeners       : function () {
            var me = this;

            me.addListener('localesLoad', onLocalesLoad, me);
        }
    }
});