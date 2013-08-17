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

    var sa = 'SA',
        saLogger = sa + '.logger',
        saLoader = sa + '.loader',
        saUtil = sa + '.util',
        saCore = sa + '.core',
        saLocale = sa + '.locale';

    function initNamespaces() {
        Ext.ns(sa);
        Ext.ns(saLogger);
        Ext.ns(saUtil);
        Ext.ns(saLoader);
        Ext.ns(saLocale);
    }

    function initLocale() {
        /**
         * @class SA.locale.Locale
         * @description SA.locale.Locale serves all locale-based tasks
         */
        Ext.define(Ext.String.format('{0}.Locale', saLocale), function (LOCALE) {
            var locales = new Ext.util.MixedCollection(),
                onLocaledLoad = function (data) {

                };
            return {
                requires           : [
                    'Ext.util.Observable',
                    'Ext.data.Store'
                ],
                mixins             : {
                    observable: 'Ext.util.Observable'
                },
                config             : {
                    url   : '/app/remote/locale',
                    locale: 'pl_PL'
                },
                constructor        : function (cfg) {
                    var me = this;

                    cfg = {} || cfg;

                    me.mixins.observable.constructor.call(me, cfg);
                    me.addEvents(
                        'localesLoad',
                        'localesChanged'
                    );

                    me.initConfig(cfg);
                    me.initEventsListeners();

                    me.callParent([cfg]);
                },
                initEventsListeners: function () {
                    var me = this;

                    me.addListener('localesLoad', onLocalesLoad, me);
                }
            }
        });
    }

    Ext.define('SA.core.Logger', function (LOGGER) {
        var pScope = undefined,
            pSentLogs = function () {
                var logs = LOGGER.pScope.logs,
                    sent = logs.filterBy(function (log, key) {
                        return log.isSent();
                    }),
                    notSent = logs.filterBy(function (log, key) {
                        return !log.isSent();
                    });

                if (notSent.getCount() == 0) {
                    return;
                }

                sent.each(function (item, key) {
                    logs.removeAtKey(key);
                });

                var writer = Ext.create('Ext.data.writer.Json'),
                    extracted = [];
                notSent.each(function (item) {
                    Ext.Array.push(extracted, writer.getRecordData(item));
                });

                Ext.Ajax.request({
                    url     : LOGGER.pScope.url,
                    jsonData: Ext.encode(extracted),
                    success : function (response) {
                        var text = response.responseText;
                        console.log(text);
                        logs.each(function (log, key) {
                            logs.replace(key, log.setSent(true))
                        });
                    }
                })
            };
        return {
            logs       : undefined,
            url        : undefined,
            singleton  : true,
            logId      : undefined,
            constructor: function (config) {
                config = {} || config;
                if (!Ext.isDefined(config.url)) {
                    config.url = '/app/remote/log'
                }
                config.logs = new Ext.util.MixedCollection();
                config.logId = 0;

                LOGGER.pScope = this;
                Ext.apply(LOGGER.pScope, config);
            },
            addLog     : function (log) {
                var scope = LOGGER.pScope,
                    logs = scope.logs,
                    logId = scope.logId;
                logs.add(logId.toString(), log);
                scope.logId = logId + 1;
            },
            getLogs    : function () {
                var scope = LOGGER.pScope,
                    logs = scope.logs;
                return logs;
            },
            removeLogs : function () {
                var scope = LOGGER.pScope,
                    logs = scope.logs;
                logs.clear();
            },
            removeLog  : function (logId) {
                var scope = LOGGER.pScope,
                    logs = scope.logs;
                if (!Ext.isString(logId)) {
                    logId = logId.toString();
                }
                logs.removeAtKey(logId);
            },
            postLogs   : function (count) {
                pSentLogs();
            }
        }
    });

    Ext.define('SA.core.LogLevel', {
        singleton: true,
        static   : {
            INFO : 'info',
            DEBUG: 'debug',
            TRACE: 'trace',
            ERROR: 'error'
        }
    });

    Ext.define('SA.core.Log', {
        extend : 'Ext.data.Model',
        fields : [
            {name: 'msg', type: 'string'},
            {name: 'appender', type: 'string', defaultValue: 'org.agatom.springatom.remote.Core'},
            {name: 'exception', type: 'string', defaultValue: ''},
            {name: 'level', type: 'string', defaultValue: SA.core.LogLevel.static.INFO},
            {name: 'sent', type: 'boolean', defaultValue: false}
        ],
        isSent : function () {
            return this.get('sent');
        },
        setSent: function (sent) {
            sent = true || sent;
            this.set('sent', sent);
            return this;
        }
    });

    Ext.define('SA.core.UILocalizedSettings', {
        extends: 'Ext.data.Model',
        keys   : [],
        fields : [
            {name: 'prefix', type: 'string'},
            {name: 'settings', type: 'object'}
        ]
    });

    function initLoggers() {
        var logger = Ext.namespace(saLogger);
        //functions
        logger.info = function (msg) {
            SA.core.Logger.addLog(Ext.create('SA.core.Log', {
                msg  : msg,
                level: SA.core.LogLevel.static.INFO
            }));
        };
        logger.debug = function (msg) {
            SA.core.Logger.addLog(Ext.create('SA.core.Log', {
                msg  : msg,
                level: SA.core.LogLevel.static.DEBUG
            }));
        };
        logger.trace = function (msg) {
            SA.core.Logger.addLog(Ext.create('SA.core.Log', {
                msg  : msg,
                level: SA.core.LogLevel.static.TRACE
            }));
        };
        logger.error = function (msg, exception) {
            SA.core.Logger.addLog(Ext.create('SA.core.Log', {
                msg      : msg,
                exception: exception,
                level    : SA.core.LogLevel.static.ERROR
            }));
        };
        //functions

        Ext.TaskManager.start(Ext.TaskManager.newTask({
            run     : SA.core.Logger.postLogs,
            interval: 2500
        }));
    }

    function initUXPath() {
        Ext.Loader.setConfig({
            enabled: true,
            paths  : {
                'Ext.ux': 'static/ux'
            }
        });
    }

    function initLoader() {
        var loader = Ext.namespace(saLoader);
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
        var s = Ext.namespace(sa),
            logger = Ext.namespace(saLogger);
        s.logObjectCreated = function (obj) {
            logger.trace(
                Ext.String.format('New object has been created, name={0}', obj.getName())
            );
        }
    }

    function initMixing() {
        Ext.define('SA.core.LocaleAwareUI', {
            getLocaleAwareUISetting: function (cmpName, callback, ui) {
                Ext.Ajax.request({
                    url    : '/app/meta/ui',
                    params : {
                        cmp: cmpName
                    },
                    success: function (response) {
                        var text = response.responseText,
                            result = Ext.decode(text);
                        callback.apply(ui, [result, ui]);
                    }
                })
            }
        });
    }

    function initDefine() {
        SA.define = function (className, cfg, locale) {

        }
    }

    initNamespaces();
    initLoader();
    initUXPath();
    initLoggers();
    initGlobals();
    initMixing();
    initLocale();
    initDefine();
}());