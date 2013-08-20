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
 * {@code SLocalePreferencesModel} is a placeholder for localized preferences of the specific locale.
 * Notify that although models such as:
 * - {@link SC.core.locale.SLocalePreferenceModel}
 * - {@link SC.core.locale.SLocaleModel}
 * are defined to hold data as {@link Ext.data.Model} this class does not use them internally as fields.
 * Nevertheless accessor for fields:
 * - locale
 * - preferences
 * are overridden to support returning these model
 * @extend Ext.data.Model
 */
Ext.define('SC.core.locale.SLocalePreferencesModel', function () {
    /**
     * Converts from <b>SC.core.locale.SLocalePreferencesModel.fields.preferences<b> to {@link SC.core.locale.SLocalePreferenceModel}
     * @param record record
     * @returns [SC.core.locale.SLocalePreferenceModel]
     */
    var toSLocalePreferenceModel = function (record) {
        var prefs = record.get('preferences'),
            converted = [];

        Ext.each(prefs, function (value) {
            var dd = Ext.create('SC.core.locale.SLocalePreferenceModel', value);
            Ext.log({
                msg  : 'Converting from object to SC.core.locale.SLocalePreferenceModel',
                level: 'info',
                dump : {
                    from: value,
                    to  : dd
                }
            });
            Ext.Array.push(converted, dd);
        });

        return converted;
    };
    /**
     * Converts from <b>SC.core.locale.SLocalePreferencesModel.fields.locale<b> to {@link SC.core.locale.SLocaleModel}
     * @param record record
     * @returns {SC.core.locale.SLocaleModel}
     */
    var toSLocaleModel = function (record) {
        var loc = record.get('locale'),
            locale = Ext.create('SC.core.locale.SLocaleModel', loc);
        return loc;
    };
    return {
        extend        : 'SC.core.model.SSequentialModel',
        requires      : [
            'SC.core.model.SSequentialModel',
            'SC.core.locale.SLocaleModel',
            'SC.core.locale.SLocalePreferenceModel'
        ],
        fields        : [
            { name: 'key', type: 'string' },
            { name: 'locale', type: 'object' },
            { name: 'preferences', type: 'object' }
        ],
        /**
         * proxy with session backend
         * @cfg proxy
         * @see Ext.data.proxy.SessionStorage
         */
        proxy         : {
            id  : 'sc-localePreferences',
            type: 'memory'
        },
        /**
         * @override
         * @returns {SC.core.locale.SLocaleModel}
         */
        getLocale     : function () {
            var me = this,
                locale = toSLocaleModel(me);
            return locale;
        },
        /**
         * @override
         * @returns [SC.core.locale.SLocalePreferenceModel]
         */
        getPreferences: function () {
            var me = this,
                preferences = toSLocalePreferenceModel(me);
            return preferences;
        }
    };
});

