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
 * Created by trebskit on 2014-07-29.
 */
(function SpringAtom_Wizards() {

    var moduleName = 'springatom.wiz',
        app = angular.module(moduleName, ['swf.angular']),
        wizardHeader = function wizardHeader($log, $filter) {
            $log.debug('wizardHeader directive loading');
            var applyStepsState = function _applyStepsState(cfg) {
                    $log.debug('Applying steps state, state={i}'.format({
                        i: cfg['stateId']
                    }));
                    var descriptor = cfg['descriptor'],
                        me = this,
                        activeStep = $filter('getByProperty')('stateId', descriptor['state'], me.steps);

                    activeStep.active = true;
                    activeStep.enabled = true;

                    angular.forEach(descriptor['predecessors'], function (predecessorSpan) {
                        var step = $filter('getByProperty')('stateId', predecessorSpan, me.steps);
                        if (angular.isDefined(step)) {
                            step.active = false;
                            step.done = true;
                        }
                    });
                    angular.forEach(descriptor['successors'], function (successorsSpan) {
                        var step = $filter('getByProperty')('stateId', successorsSpan, me.steps);
                        if (angular.isDefined(step)) {
                            step.enabled = true;
                            step.active = false;
                        }
                    });
                    me.$evalAsync();
                },
                link = function _wizardHeaderLink(scope, el, attrs) {
                    if (!angular.isDefined(attrs.steps)) {
                        throw new Error('wizardHeader :: steps)) are not defined');
                    }
                    scope.steps = scope.$eval(attrs.steps);
                    scope.wizardId = attrs['wizard'];
                    scope.wizardTitle = attrs['title'];
                    scope.applyStepsState = applyStepsState;
                };
            return {
                restrict   : 'E',
                scope      : true,
                templateUrl: '/ui/wizard/header.jsp',
                link       : link
            }
        },
        wizardActions = function wizardActions($log, $filter) {
            $log.debug('wizardActions directive loading');

            var applyActionVisibility = function _applyActionVisibility(actions) {
                    $log.debug('Reapplying actions visibility');
                    var me = this;
                    angular.forEach(actions, function (act) {
                        var action = $filter('getByProperty')('eventName', act, me.actions);
                        if (!angular.isDefined(action)) {
                            throw new Error('Action not found');
                        }
                        action.visible = true;
                    });
                    me.$evalAsync();
                },
                addDynamicActions = function _addDynamicActions(actions) {
                    $log.debug('Applying dynamic actions');
                    if (!angular.isDefined(actions) || !angular.isArray(actions)) {
                        $log.warn('Cannot apply dynamic actions, passed var is not defined or array, var={v}'.format({
                            v: actions.toString()
                        }));
                    }
                    if (actions.length > 0) {
                        $log.debug('Applying {d} dynamic actions'.format({
                            d: actions.length
                        }));
                        var me = this;
                        angular.forEach(actions, function (act) {
                            me.actions.push({
                                id       : act['eventName'],
                                label    : act['labelName'],
                                eventName: act['eventName'],
                                visible  : true,
                                dynamic  : true
                            });
                        });
                        me.$evalAsync();
                    }
                },
                linkFunc = function wizardActionsLinkFunction(scope, el, attrs) {
                    if (!angular.isDefined(attrs.actions)) {
                        throw new Error('wizardActions :: actions are not defined');
                    }
                    var eval = scope.$eval(attrs.actions);
                    if (!angular.isDefined(eval)) {
                        throw new Error('wizardActions :: actions\' content is missing');
                    }
                    scope.actions = eval.content;
                    scope.wizardId = attrs['wizard'];
                    scope.applyActionVisibility = applyActionVisibility;
                    scope.addDynamicActions = addDynamicActions;
                },
                /**
                 * Computes template for actions
                 * @returns {string}
                 */
                getTemplate = function wizardActionsGetTemplate() {
                    var array = [];

                    array.push('<div class="btn-group" ng-show="actions">');
                    array.push('<button ng-repeat="act in actions" id="{{act.id}}" ng-show="act.visible" type="submit" role="link" name="{{act.eventName}}" class="{{act.iconClass}}" title="{{act.label}}" ng-click="wizAct.onActionClick($event,act,wizardId)">');
                    array.push('<i ng-show="act.eventIconClass" class="{{act.eventIconClass}}" role="presentation"><span ng-if="act.dynamic">{{act.label}}</span></i>');
                    array.push('</button>');
                    array.push('</div>');

                    return array.join('');
                };

            return {
                restrict    : 'E',
                scope       : true,
                template    : getTemplate(),
                link        : linkFunc,
                controller  : ['$scope', 'swfSubmit', function ($scope, swfSubmit) {
                    $log.debug('wizActions controller initializing');
                    var me = this,
                        getClickTarget = function _getTarget(target) {
                            target = $(target);
                            if (target.is('i')) {
                                return target.parent();
                            }
                            return target;
                        },
                        onWizActionClick = function _onWizActionClick(event, action, wizardId) {
                            var wizard = $('#{id}'.format({
                                    id: wizardId
                                })),
                                form = wizard.find('form'),
                                cfg = {
                                    elementId: getClickTarget(event.target).attr('id'),
                                    formId   : form.attr('id'),
                                    event    : event.type
                                };

                            if (action.name === 'finish') {
                                swfSubmit.submit(cfg, event);
                            } else {
                                angular.extend(cfg, {
                                    popup : true,
                                    params: {
                                        fragments: 'wiz.content',
                                        mode     : "embedded"
                                    }
                                });
                                swfSubmit.submit(cfg, event);
                            }
                        };

                    // export functions of the controllers
                    angular.extend(me, {
                        onActionClick: onWizActionClick
                    });
                }],
                controllerAs: 'wizAct'
            }
        },
        wizNBox = function wizBindErrorDirective($filter) {
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
                templateUrl: '/ui/wizard/notificationsBox.jsp',
                link       : link
            }
        };

    app.directive('wizardActions', ['$log', '$filter', wizardActions])
        .directive('wizardHeader', ['$log', '$filter', wizardHeader])
        .directive('wizardNbox', ['$filter', wizNBox]);

}());
