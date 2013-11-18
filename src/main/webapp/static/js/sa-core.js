/*
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2013]
 *
 * [SpringAtom] is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * [SpringAtom] is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with [SpringAtom].  If not, see <http://www.gnu.org/licenses/gpl.html>.
 */

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

(function (SA, $) {
    if (!SA.core) {
        SA.core = {};
    }

    // private methods
    var priv = {
        initNotificiationSystem: function () {
            SA.core.ajax.loadLocalizedPreferences({
                keys    : {
                    'ok'    : 'button.ok',
                    'cancel': 'button.cancel'
                },
                pattern : false,
                reversed: true,
                callback: function (data) {
                    alertify.set({
                        labels: {
                            ok    : data['ok'][0],
                            cancel: data['cancel'][0]
                        }
                    });
                }
            });
        },
        initStrings            : function () {
            String.prototype.startsWith = function (str) {
                var length = str.length;
                return this.substring(0, length) === str;
            };
            String.prototype.endsWith = function (str) {
                var length = this.length;
                return this.substring(length - 1, length) === str;
            };
            String.prototype.removeFromBeginning = function (count) {
                return this.substring(count);
            };
            String.prototype.removeFromEnd = function (count) {
                var length = this.length;
                return this.substring(0, length - count);
            };
        },
        initJQueryExtension    : function () {
            $.fn.doesExist = function () {
                return $(this).length > 0;
            };
            $.urlParam = function (name, decode) {
                var results = new RegExp('[\\?&]' + name + '=([^&#]*)').exec(window.location.href);
                if (results == null) {
                    return '';
                }
                else {
                    var result = results[1] || 0;
                    if (decode === true) {
                        result = decodeURIComponent(result);
                    }
                    return result;
                }
            }
        }
    };

    // public methods
    SA.core.openModal = function (target) {
        var _target = $(target);
        if (SA.core.isEmpty(_target)) {
            _target.modal({
                overlayClose: true,
                opacity     : 20,
                overlayCss  : {
                    backgroundColor: "#246485"
                },
                onOpen      : function (dialog) {
                    dialog.overlay.fadeIn('slow', function () {
                        dialog.data.hide();
                        dialog.container.fadeIn('slow', function () {
                            dialog.data.slideDown('slow');
                        });
                    });
                },
                onClose     : function (dialog) {
                    dialog.data.fadeOut('slow', function () {
                        dialog.container.hide('slow', function () {
                            dialog.overlay.slideUp('slow', function () {
                                $.modal.close();
                            });
                        });
                    });
                }
            })
        } else {
            SA.core.showError("Could not locate target=" + target);
        }
    };
    SA.core.showSuccess = function (text) {
        alertify.success(
            '<span style="font-weight: bold">' + text + '</span>'
        );
    };
    SA.core.showError = function (text) {
        if (text instanceof jQuery) {
            if (text.length == 0) {
                return;
            }
            var msg = "";
            $.each(text, function (index, val) {
                msg += '<p>' + $(val).html() + "</p>";
            });
            text = msg;
        }
        alertify.error(
            '<span style="font-weight: bold">' + text + '</span>'
        );
    };
    SA.core.isEmpty = function (obj) {
        return true;
    }
    if (!SA.core.ajax) {
        SA.core.ajax = {};
    }

    /**
     * Loads localized preferences according to the passed configuration.
     * cfg is defined as follow:
     * <li>keys - an array of code to the localized preferences</li>
     * <li>pattern[default=true] - if set to false no pattern matching will be used</li>
     * <li>reversed[default=false] -
     *     if set to true requested keys must be passed as the object literal.
     *     In example:
     *     <pre>
     *      keys: { a:'key.1' }
     *     </pre>
     *     If so the values assigned to the properties in the literal will be send to the server
     *     and than swapped with the received values for the given keys.
     * </li>
     * <li>callback - the function to call in case of the successful retrieval</li>
     * <li>scope - in which to execute the callback</li>
     *
     * @param cfg configuration literal
     */
    SA.core.ajax.loadLocalizedPreferences = function (cfg) {
        var defaults = {
                keys    : ['*'],
                pattern : true,
                reversed: false,
                callback: undefined,
                scope   : undefined
            },
            reverser = function (options) {
                var tmp = [];
                $.each(options, function (key, value) {
                    tmp.push(value);
                });
                return tmp;
            };

        cfg = $.extend(defaults, cfg);

        $.ajax({
            url        : '/data/lang/read',
            type       : 'POST',
            contentType: "application/json",
            data       : JSON.stringify({
                keys   : (function () {
                    if (cfg['reversed']) {
                        return reverser(cfg['keys']);
                    }
                    return cfg['keys'];
                }()),
                pattern: cfg['pattern']
            }),
            dataType   : 'json',
            success    : function (data) {
                var preferences = data['preferences'];
                $.each(preferences, function (index, pref) {
                    var _key = pref['key'],
                        _message = pref['message'];
                    $.each(cfg['keys'], function (key, value) {
                        if (value === _key) {
                            cfg['keys'][key] = _message.split(',');
                        }
                    });
                });
                if (cfg['callback']) {
                    cfg['callback'].apply(cfg['scope'], [cfg['keys']]);
                }
            }
        });
    };

    var bundleCache = {

    };
    SA.core.getLocalizedMsg = function (key) {

        if (typeof bundleCache[key] !== 'undefined') {
            return bundleCache[key];
        }

        $.ajax({
            url        : '/data/lang/bundle/' + key,
            type       : 'GET',
            dataType   : 'json',
            contentType: "application/json",
            async      : false,
            success    : function (data) {
                bundleCache[key] = data['message'];
                return bundleCache[key];
            },
            error      : function (xhr, status, error) {
                SA.core.showError('Failed to getLocalizedMsg => ' + error);
            }
        });

        return bundleCache[key];
    };

    (function () {
        console.log('Initializing SA.core');
        priv.initNotificiationSystem();
        priv.initJQueryExtension();
        priv.initStrings();
        console.log('Initialized SA.core');
    }());

}(window.SA = window.SA || {}, jQuery));
