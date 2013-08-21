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


Ext.define('SC.core.SLogger', function (LOGGER) {
    var pScope = undefined,
        pSentLogs = function () {
            var logs = LOGGER.pScope.logs,
                sent = logs.filterBy(function (log) {
                    return log.isSent();
                }),
                notSent = logs.filterBy(function (log) {
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
        requires   : [
            'Ext.data.writer.Json',
            'SC.core.logger.SLog',
            'SC.core.logger.SExceptionLog',
            'SC.core.logger.SLogLevel'
        ],
        constructor: function (config) {
            config = {} || config;
            if (!Ext.isDefined(config.url)) {
                config.url = '/app/remote/log'
            }
            config.logs = new Ext.util.MixedCollection();
            config.logId = 0;

            LOGGER.pScope = this;
            Ext.TaskManager.start(Ext.TaskManager.newTask({
                run     : LOGGER.pScope.postLogs,
                interval: 2500
            }));
            Ext.Error.handle = LOGGER.pScope.error;
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
        },
        info       : function (msg) {
            var scope = LOGGER.pScope;
            scope.addLog(Ext.create('SC.core.logger.SLog', {
                msg  : msg,
                level: SC.core.logger.SLogLevel.static.INFO
            }));
            Ext.log({
                msg  : msg,
                level: 'info'
            });
        },
        debug      : function (msg) {
            var scope = LOGGER.pScope;
            scope.addLog(Ext.create('SC.core.logger.SLog', {
                msg  : msg,
                level: SC.core.logger.SLogLevel.static.DEBUG
            }));
            Ext.log({
                msg  : msg,
                level: 'log'
            });
        },
        trace      : function (msg) {
            var scope = LOGGER.pScope;
            scope.addLog(Ext.create('SC.core.logger.SLog', {
                msg  : msg,
                level: SC.core.logger.SLogLevel.static.TRACE
            }));
            Ext.log({
                msg  : msg,
                level: 'log'
            });
        },
        error      : function (err) {
            var scope = LOGGER.pScope,
                args = err['arguments'];

            args = [] || args;

            scope.addLog(Ext.create('SC.core.logger.SExceptionLog', {
                msg      : err['msg'],
                clazz    : err['sourceClass'],
                method   : err['sourceMethod'],
                arguments: args,
                level    : SC.core.logger.SLogLevel.static.ERROR
            }));
        }
    }
});
