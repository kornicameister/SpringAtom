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
 * Created by trebskit on 2014-08-29.
 */
define(
    [
        'angular',
        'utils',
        'underscore'
    ],
    function authoritiesController(angular, utils, _) {
        return function ($scope, $log, stepData) {


            var initData = stepData,
                formScope = $scope.$parent,
                updateFormAuthorities = function updateFormAuthorities(data) {
                    if (!_.isUndefined(data)) {
                        $log.debug('Selected authorities changed');
                        var toForm = [];
                        _.each(data, function dataIt(chunk) {
                            toForm.push(_.property('value')(chunk));
                        });
                        if (toForm.length > 0) {
                            formScope.formData.authorities = toForm;
                        }
                    }
                },
                getIcon = function getIcon(icon) {
                    return '<i class="fa fa-fw {icon}"></i>'.format({
                        icon: icon
                    });
                },
                getRolesGroup = function getRolesGroup(local, roles) {
                    local.push({
                        isGroup: true
                    });
                    _.each(roles, function authoritiesIt(val) {
                        local.push(angular.extend({
                            icon  : getIcon('fa-user'),
                            type  : 'role',
                            ticked: false
                        }, val));
                    });
                    local.push({
                        isGroup: false
                    });
                },
                getAuthoritiesModel = function getAuthoritiesModel() {
                    var local = [],
                        roles = initData.getStepData('roles').options;
                    getRolesGroup(local, roles);
                    return local;
                },
                getExtraLabels = function getExtraLabels() {
                    return initData.getStepData('extraLabels');
                };

            angular.extend($scope, {
                authorities        : getAuthoritiesModel(),
                selectedAuthorities: [],
                extraLabels        : getExtraLabels(),
                onCloseCallback    : updateFormAuthorities
            });


        }
    }
);