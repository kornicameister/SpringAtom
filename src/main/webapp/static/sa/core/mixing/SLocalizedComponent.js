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

Ext.define('SC.core.mixing.SLocalizedComponent', function () {
    var patternOpen = '${',
        patternClose = '}$',
        getClazz = function (container) {
            var clazzName = Ext.getClassName(container);
            if (!Ext.isDefined(clazzName)) {
                Ext.Error.raise({
                    msg : 'Clazz name not retrieved',
                    dump: {
                        clazzName: clazzName,
                        container: container,
                    }
                });
            }
            return  clazzName;
        },
        getPreferences = function (clazzName) {
            return SC.core.SLocale.getPreferences(clazzName);
        },
        resolvePath = function (path) {
            path = path.split('>');
            path = (function () {
                var pPath = [];
                Ext.each(path, function (value) {
                    Ext.Array.push(pPath, value.trim());
                });
                return pPath;
            }());
            return path;
        },
        update = function (node, path, value) {
            var matcher = /\[\d+\]/gi,
                cloned = Ext.clone(node);
            do {
                var splice = path.shift();
                if (!matcher.test(splice)) {
                    node = node[splice];
                } else {
                    var start = splice.indexOf('['),
                        end = splice.lastIndexOf(']');
                    splice = splice.substring(start + 1, end);
                    node = node[parseInt(splice)];
                }
            } while (path.length > 1);
            node[path[0]] = value;
        },
        applyValue = function (path, value, object) {
            return update(object, path, value);
        },
        apply = function (cfg, paths, preferences) {
            Ext.each(preferences, function (pref) {
                var key = pref.get('key'),
                    value = pref.get('value'),
                    patternKey = Ext.String.format('{0}{1}{2}', patternOpen, key, patternClose),
                    path = resolvePath(Ext.Object.getKey(paths, patternKey));
                applyValue(path, value, cfg);
            });
            return cfg;
        };
    return {
        /**
         * @cfg pattern to be replaces againts keys in localization
         */
        applyLocalization: function (config) {
            var container = config['container'],
                cfg = config['cfg'],
                paths = config['paths'],
                clazzName = getClazz(container),
                preferences = getPreferences(clazzName);
            Ext.applyIf(container, apply(cfg, paths, preferences));
        }
    }
});