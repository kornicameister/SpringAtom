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
 * @class SC.core.locale.SLocalePreferencesModel
 * {@code SLocalePreferencesModel} is a placeholder for localized preferences of the specific locale
 * @extend Ext.data.Model
 */
Ext.define('SC.core.locale.SLocalePreferencesModel', {
    extend                        : 'Ext.data.Model',
    requires                      : [
        'Ext.data.Model',
        'SC.core.locale.SLocaleModel',
        'SC.core.locale.SLocalePreferenceModel'
    ],
    fields                        : [
        { name: 'key', type: 'string'},
        { name: 'locale'},
        { name: 'preferences'}
    ],
    proxy                         : {
        id    : 'sc-localePreferences',
        type  : 'localstorage',
        reader: {
            type: 'json'
        }
    },
    /**
     * @constructor
     * Creates new SLocalePreferencesModel and translates given (in cfg) locale and preferences to:
     * - locale >> {@link SC.core.locale.SLocaleModel}
     * - preferences >> {@link SC.core.locale.SLocalePreferenceModel} with {@link Ext.Array.toValueMap}
     * @param cfg configuration appropriate for {@link Ext.data.Model}
     * @cfg cfg.locale object literal appropriate for {@link SC.core.locale.SLocaleModel}
     * @cfg cfg.preferences array of object literals appropriate for {@link SC.core.locale.SLocalePreferenceModel}
     */
    constructor                   : function (cfg) {
        var me = this,
            locale = cfg['locale'],
            preferences = cfg['preferences'],
            pref = [];

        me.callParent([cfg]);
        me['data']['locale'] = (Ext.create('SC.core.locale.SLocaleModel', locale));
        Ext.each(preferences, function (item) {
            Ext.Array.push(pref, Ext.create('SC.core.locale.SLocalePreferenceModel', item));
        });
        me['data']['preferences'] = Ext.Array.toValueMap(pref, me.getKeyOfSLocalePreferenceModel);
    },
    /**
     * @private
     * getTag method for {@link Ext.Array.toValueMap}.
     * Key is {@link SC.core.locale.SLocalePreferenceModel#fields}/key
     *
     * @param rec instance of {@link SC.core.locale.SLocalePreferenceModel}
     * @returns String
     */
    getKeyOfSLocalePreferenceModel: function (rec) {
        return rec.get('key');
    },
    /**
     * Resolves preference for given key
     *
     * @param key one of the keys defined for preferences of @this class
     * @returns String
     */
    getPreference                 : function (key) {
        var me = this,
            preferences = me['data']['preferences'],
            value = preferences[key];
        if (!Ext.isDefined(value)) {
            Ext.Error.raise(Ext.String.format('Failed to retrieve localized preference for cmp={0} with key={1}', me.get('cmp'), key));
        }
        return value;
    }
}, SC.logObjectCreated);

