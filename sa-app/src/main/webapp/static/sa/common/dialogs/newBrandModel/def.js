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
 * Created by trebskit on 2014-08-22.
 */
define(
    [
        'angular',
        'common/utils',
        'ngDialogs',
        'lodash'
    ],
    function newBrandModel(angular, utils) {
        var templateUrl = '/static/sa/common/dialogs/newBrandModel/view.html';

        /**
         * <b>sg.common.dialogs.newBrandModel</b> module allows to show a dialog which gives
         * a way to create new brand and model
         *
         * @module sg.common.dialogs.newBrandModel
         */
        var module = angular.module('sg.common.dialogs.newBrandModel', ['dialogs.main']);

        module.controller('NewBranModelController', function newBrandModelCtrl($scope, $modalInstance, data) {
            _.assign($scope, {
                bm        : undefined,
                carMasters: data.carMasters,
                debug     : utils.isDebug(),
                done      : function (bm) {
                    $modalInstance.close(bm);
                },
                dismiss   : $modalInstance.dismiss
            });
        });

        module.factory('brandModelDialog', function newBrandModelService(dialogs) {
            return {
                /**
                 * <b>data</b> must contain following property:
                 * - carMasters in following format (label,value) in order to properly initialize the dialog
                 * @param data
                 * @returns {templateUrl}
                 */
                dialog: function (data) {
                    return dialogs.create(templateUrl, 'NewBranModelController', data);
                }
            }
        });

        return module;
    }
);
