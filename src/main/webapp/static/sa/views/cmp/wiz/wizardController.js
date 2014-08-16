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
        'angular',
        'utils'
    ],
    function (angular, utils) {
        /**
         * Following arguments are injected from resolve of the state:
         * - title,
         * - definition,
         * - actions
         * - service
         * The rest in injected from angular
         */
        return function newCarController($scope, $timeout, $log, $state, $cookies, timeoutDelay, dialogs, title, definition, actions, service) {
            $log.debug('Entering wizardController');

            var exitState = $cookies.lastState,// retrieve state from which application entered wizard, for cancel action
                dlg = undefined,
                actionMap = {},
                stepsMap = {},
                dirs = {
                    NEXT: 1,
                    PREV: -1
                },
                init = {
                    getSteps  : function getSteps() {
                        var steps = [],
                            index = 0;
                        angular.forEach(definition, function (chunk) {
                            steps.push({
                                index   : index,
                                label   : chunk.label,
                                required: chunk.required,
                                state   : chunk.state
                            });
                            stepsMap[chunk.state] = index;
                            stepsMap[index++] = chunk.state;
                        });
                        return steps;
                    },
                    getActions: function getActions() {
                        var localActions = [];
                        angular.forEach(actions.getModel(), function (act) {
                            localActions.push(angular.extend(act, {
                                handler: hooks[act.name],
                                // by default acton is disabled, only cancel is enabled
                                enabled: act.name === 'cancel'
                            }));
                            actionMap[act.name] = act.order;
                        });
                        return localActions;
                    }
                },
                helpers = {
                    setActiveStep  : function setActiveStep(step) {
                        $log.debug('setActiveStep(step={s})'.format({s: step}));
                        if (!angular.isDefined(step)) {
                            var message = 'Can not set active step in scope, because step variable in not defined';
                            if (utils.isDebug()) {
                                throw new Error(message);
                            } else {
                                alert(message);
                            }
                        }
                        $scope.activeStep = step;
                        $state.go(step);
                    },
                    setSiblingState: function setSiblingState(dir) {
                        var currentStep = $scope.activeStep,
                            index = stepsMap[currentStep],
                            nextStep = stepsMap[index + dir];
                        helpers.setActiveStep(nextStep);
                    }
                },
                hooks = {
                    finish  : function finishForm($event) {
                        $event.preventDefault();
                    },
                    cancel  : function cancelForm($event) {
                        $event.preventDefault();
                        dlg = dialogs.confirm(
                            'Wyjdź',
                            'Czy na pewno chcesz zakończyć?'
                        );
                        dlg.result.then(
                            function onConfirm() {
                                $log.debug('Exiting wizard {w}'.format({w: title}));
                                $state.go(exitState);
                                dlg = undefined;
                            },
                            function onDecline() {
                                dlg = undefined;
                            }
                        );
                    },
                    next    : function nextStep($event) {
                        $event.preventDefault();
                        helpers.setSiblingState(dirs.NEXT);
                    },
                    previous: function previousStep($event) {
                        $event.preventDefault();
                        helpers.setSiblingState(dirs.PREV);
                    }
                },
                watchers = {
                    onActiveStepChange: function onActiveStepChange(step, lastStep) {
                        $log.debug('onActiveStepChange(step={s})'.format({s: step}));
                        if (angular.isUndefined(step) || !angular.isString(step) || step === '') {
                            return false;
                        }
                        if (step === lastStep) {
                            $log.warn('step and last step are the same this should not happen');
                            return false;
                        }
                        // rebuild action model according to the state
                        var stepIndex = stepsMap[step],
                            stepsCounts = $scope.header.length,
                            actions = $scope.actions,
                            header = $scope.header;

                        actions[actionMap['finish']].enabled = (stepIndex === stepsCounts - 1);
                        actions[actionMap['next']].enabled = (stepIndex >= 0 && !actions[actionMap['finish']].enabled);
                        actions[actionMap['previous']].enabled = (stepIndex !== 0 && stepIndex < stepsCounts);

                        // set active header
                        angular.forEach(header, function headerIt(head) {
                            head.active = (head.index === stepIndex);
                        });

                        return true;
                    }
                };

            angular.extend($scope, {
                title   : title,
                actions : init.getActions(),
                header  : init.getSteps(),
                formData: service.getFormData(),
                hooks   : hooks,
                debug   : utils.isDebug()
            });

            // set up watchers
            $scope.$watch('activeStep', watchers.onActiveStepChange);

            // enter first state in definition
            $timeout(function enterFirstStep() {
                var state = definition[0].state;
                if (state) {
                    $log.debug('Initializing initial state({s}) of wizard({t})'.format({
                        s: state,
                        t: title
                    }));
                    helpers.setActiveStep(state);
                }
            }, timeoutDelay);
        }
    }
);
