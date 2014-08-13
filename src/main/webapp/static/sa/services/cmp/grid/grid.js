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
 * Created by trebskit on 2014-08-14.
 */
define(
    [
        'config/module',
        //angular injections
        'services/cmp/grid/dataHelper',
        'services/cmp/grid/configurationHelper',
        'services/cmp/grid/pageHelper'
    ],
    function grid(app) {
        var gridService = function gridService(dataHelper, configHelper, pageHelper) {
            return {
                loadConfiguration : function gs_loadConfiguration(cfg) {
                    var successCallback = cfg.success,
                        failureCallback = cfg.failure,
                        localSuccess = function (data) {
                            successCallback.apply(this, [configHelper.unpackConfiguration(data)]);
                        },
                        localFailure = function (data) {
                            failureCallback.apply(this, [data]);
                        };

                    // clear up
                    delete cfg.success;
                    delete cfg.failure;

                    configHelper.loadConfiguration(cfg).then(localSuccess, localFailure);
                },
                loadData          : function gs_loadData(cfg) {
                    var successCallback = cfg.success,
                        failureCallback = cfg.failure,
                        localSuccess = function (data) {
                            successCallback.apply(this, [dataHelper.unpackData(data)]);
                        },
                        localFailure = function (data) {
                            failureCallback.apply(this, [data]);
                        };

                    // clear up
                    delete cfg.success;
                    delete cfg.failure;

                    dataHelper.loadData(cfg).then(localSuccess, localFailure);
                },
                getPageSizes      : function delegate_getPageSizes() {
                    return pageHelper.getPageSizes();
                },
                getDefaultPageSize: function delegate_getDefaultPageSize() {
                    return pageHelper.getDefaultPageSize();
                },
                getInitialPage    : function delegate_getInitialPage() {
                    return pageHelper.getInitialPage();
                }
            }
        };

        app.factory('gridService', [ 'gridDataHelper', 'gridConfigurationHelper', 'gridPageHelper', gridService]);
    }
);
