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
 * Created by trebskit on 2014-09-17.
 */
define(
    [
        'config/module',
        'underscore'
    ],
    function oidService(app, _) {
        /*
         TODO Should be replace with generating server request to resolve it
         or combined out of MongoID when project will be ported to MongoDB Based
         solution
         */
        var oidShorts = {
            appointment: 'P:org.agatom.springatom.server.model.beans.appointment.SAppointment:{id}',
            car        : 'P:org.agatom.springatom.server.model.beans.car.SCar:{id}'
        };

        app.factory('oidService', function () {
            return {
                getOid: function (shortRel, id) {
                    var shorty = oidShorts[shortRel];
                    return shorty.format({
                        id: id
                    })
                }
            }
        });
    }
);