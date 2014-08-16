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
        var directive = function wizardActions($log) {
            $log.debug('wizardActions directive loading');

            var linkFunc = function wizardActionsLinkFunction(scope) {
                    if (!angular.isDefined(scope.actions)) {
                        throw new Error('wizardActions :: actions are not defined');
                    }
                },
                /**
                 * Computes template for actions
                 * @returns {string}
                 */
                getTemplate = function wizardActionsGetTemplate() {
                    var array = [];

                    array.push('<div class="btn-group-lg" ng-show="actions">');
                    array.push('<button ng-repeat="act in actions" id="{{act.id}}" ng-click="act.handler($event)" type="submit" role="link" name="{{act.eventName}}" class="{{act.iconClass}}" title="{{act.label}}" ng-show="act.enabled">');
                    array.push('<i ng-show="act.eventIconClass" class="{{act.eventIconClass}}" role="presentation"></i>');
                    array.push('</button>');
                    array.push('</div>');

                    return array.join('');
                };

            return {
                restrict: 'E',
                scope   : {
                    actions: '=actions'
                },
                template: getTemplate(),
                link    : linkFunc
            }
        };
        return {
            name      : 'wizardActions',
            definition: ['$log', directive]
        }
    }
);
