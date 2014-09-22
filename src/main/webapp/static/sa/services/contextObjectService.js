/*
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2014]
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

/**
 * Created by trebskit on 2014-09-09.
 */
define(
    [
        'config/module',
        'utils',
        'underscore',
        // other dependencies
        'jsface'
    ],
    function contextObjectService(app, utils, _) {

        var ContextObject = Class({
                constructor: function (cfg) {
                    this.type = cfg.type;
                    this.id = cfg.id;
                    this.version = cfg.version;
                    this.revision = cfg.revision;
                }
            }),
            ContextObjectBuilder = Class({
                setType    : function (type) {
                    this.type = type;
                },
                setId      : function (id) {
                    this.id = id;
                },
                setVersion : function (ver) {
                    this.version = ver;
                },
                setRevision: function (rev) {
                    this.revision = rev;
                },
                build      : function () {
                    return new ContextObject({
                        type    : this.type,
                        id      : this.id,
                        version : this.version,
                        revision: this.revision
                    })
                }
            });

        app.factory('contextObjectService', function ($log, $rootScope) {
            return {
                /**
                 * Views presenting infoPages, self standing tables or any other visual
                 * components should call this method in order to keep track on the context object.
                 * Such context object is used afterwards to create valid request to the server
                 * and allows to retrieve data specific for this object
                 * @param co context object definition
                 */
                setContextObject  : function (co) {
                    if (!_.isUndefined(co)) {
                        $rootScope.contextObject = co;
                    }
                },
                getContextObject  : function () {
                    return $rootScope.contextObject;
                },
                clearContextObject: function () {
                    delete $rootScope.contextObject;
                },
                buildContextObject: function () {
                    $log.debug('Building context object...');
                    var self = this,
                        builder = new ContextObjectBuilder();
                    _.wrap(builder.build, function (result) {
                        $log.debug('Context object built, setting up..');
                        self.setContextObject(result);
                    });
                    return builder;
                }
            }
        });
    }
);