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
 * Created by trebskit on 2014-08-15.
 */
define(
    [
        'angular',
        'config/ext'
    ],
    function header(angular) {
        var wizNBox = function wizBindErrorDirective($filter) {
            var severityIconMapping = {
                    'INFO'   : 'list-group-item-info',
                    'WARNING': 'list-group-item-warning',
                    'ERROR'  : 'list-group-item-danger',
                    'FATAL'  : 'list-group-item-danger'
                },
                severityWeight = {
                    'INFO'   : 1,
                    'WARNING': 2,
                    'ERROR'  : 3,
                    'FATAL'  : 4
                },
                getRejectedValue = function _getRejectedValue(value) {
                    if (value['rejectedValue']) {
                        return ' ' + value['rejectedValue'] + '<<< ';
                    }
                    return '';
                },
                createScopeMsg = function _createScopeMsg(msg) {
                    var bindErrors = [];
                    angular.forEach(msg, function (value) {
                        var severity = value['severity'];
                        bindErrors.push({
                            errClass     : severityIconMapping[severity],
                            text         : value['text'],
                            weight       : severityWeight[severity],
                            severity     : severity,
                            rejectedValue: getRejectedValue(value)
                        })
                    });
                    // sort by severity
                    bindErrors = $filter('orderBy')(bindErrors, function _sort(a, b) {
                        return a.weight > b.weight ? 1 : -1
                    });
                    return bindErrors;
                },
                link = function (scope, el, attrs) {
                    var errors = scope.$eval(attrs['errors'] || []),
                        messages = scope.$eval(attrs['messages'] || []);
                    angular.forEach(errors, function (err) {
                        messages.push({
                            severity     : 'ERROR',
                            text         : err['defaultMessage'],
                            rejectedValue: err['rejectedValue']
                        });
                    });
                    scope.bindErrors = createScopeMsg(messages);
                };
            return {
                restrict   : 'E',
                scope      : true,
                templateUrl: '/ui/wiz/nbox.jsp',
                link       : link
            }
        };
        return {
            name      : 'wizardNbox',
            definition: ['$filter', wizNBox]
        }
    }
);
