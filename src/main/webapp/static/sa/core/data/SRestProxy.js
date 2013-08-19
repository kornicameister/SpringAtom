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


Ext.define('SC.core.data.SRestProxy', {
    extend      : 'Ext.data.proxy.Rest',
    requires    : [
        'Ext.data.proxy.Rest'
    ],
    alias       : 'proxy.srest',
    batchActions: true,
    noCache     : false,
    reader      : {
        type           : 'json',
        root           : 'data',
        successProperty: function (data) {
            return data['success'] === true
        },
        totalProperty  : 'total',
        messageProperty: 'message'
    },
    writer      : {
        type          : 'json',
        root          : 'data',
        allowSingle   : false,
        writeAllFields: false
    },
    listeners   : {
        exception: function (proxy, response, operation) {
            Ext.MessageBox.show({
                title  : 'REMOTE EXCEPTION',
                msg    : operation.getError(),
                icon   : Ext.MessageBox.ERROR,
                buttons: Ext.Msg.OK
            });
        }
    }
});