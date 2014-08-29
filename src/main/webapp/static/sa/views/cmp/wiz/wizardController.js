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
        'utils',
        'underscore',
        // angular injections
        'resources/wizardResource'
    ],
    function (angular, utils, _) {
        /**
         * Following arguments are injected from resolve of the state:
         * - title,
         * - definition,
         * - actions
         * - wizardKey
         * - wizardHandler
         * The rest in injected from angular
         */
        return function wizardController($scope,
                                         $timeout,
                                         $log,
                                         $state,
                                         $cookies,
                                         timeoutDelay,
                                         dialogs,
                                         wizardResult,
                                         actions,
                                         wizardKey, wizardHandler, wizardResource) {
            $log.debug('Entering wizardController');

            var exitState = $cookies.lastState,// retrieve state from which application entered wizard, for cancel action
                errorStateName = 'error',
                dlg = undefined,
                actionMap = {},
                stepsMap = {},
                dirs = {
                    NEXT: 1,
                    PREV: -1
                },
                definition = (function getDefinition() {
                    return wizardResult.getData('descriptor');
                }()),
                init = {
                    getInitialState: function () {
                        var init = definition.content[0];
                        return init.state;
                    },
                    getTitle       : function getTitle() {
                        return definition.label;
                    },
                    getLabels      : function getLabels() {
                        var labels = {};
                        angular.forEach(definition.content, function (chunk) {
                            var localLabels = chunk.labels;
                            labels = angular.extend(labels, localLabels || {});
                        });
                        return labels;
                    },
                    getSteps       : function getSteps() {
                        var steps = [];
                        angular.forEach(definition.content, function (chunk) {
                            steps.push(angular.extend(chunk, {
                                state: wizardKey + '.' + chunk.step
                            }));
                            stepsMap[chunk.state] = chunk.position;
                            stepsMap[chunk.position] = steps[steps.length - 1].state;
                        });
                        return steps;
                    },
                    getActions     : function getActions() {
                        var localActions = [];
                        angular.forEach(actions.getModel(), function (act) {
                            localActions.push(angular.extend(act, {
                                handler: hooks[act.name],
                                // by default acton is disabled, only cancel is enabled
                                visible: act.name === 'cancel'
                            }));
                            actionMap[act.name] = act.order;
                        });
                        return localActions;
                    }
                },
                helpers = {
                    setActiveStep          : function setActiveStep(step) {
                        $log.debug('setActiveStep(step={s})'.format({s: step}));
                        if (!angular.isDefined(step)) {
                            var message = 'Can not set active step in scope, because step variable in not defined';
                            if (utils.isDebug()) {
                                throw new Error(message);
                            } else {
                                alert(message);
                            }
                        }
                        $scope.activeState = step;
                        $state.go(step);
                    },
                    /**
                     * Returns currently selected step
                     * @returns {*}
                     */
                    getActiveStep          : function getActiveStep() {
                        return $scope.activeState;
                    },
                    /**
                     * Returns actions property of the current {@code $scope}
                     * @returns {*}
                     */
                    getActions             : function () {
                        return $scope.actions;
                    },
                    /**
                     * Sets {@code visible} for the action identified as {@code actionName}
                     * @param actionName action identifier
                     * @param visible true/false, true if enable the action
                     */
                    setActionVisible       : function setActionVisible(actionName, visible) {
                        visible = angular.isUndefined(visible) ? true : visible;
                        var actions = helpers.getActions();
                        actions[actionMap[actionName]].visible = visible;
                    },
                    /**
                     * Returns if action is enabled
                     * @param actionName action identifier
                     * @returns {boolean} true if enabled
                     * @see helpers.setActionVisible(actionName,enabled)
                     */
                    isActionVisible        : function isActionVisible(actionName) {
                        var actions = helpers.getActions();
                        return actions[actionMap[actionName]].visible === true;
                    },
                    setSiblingState        : function setSiblingState(dir) {
                        var currentStep = $scope.activeState,
                            index = stepsMap[currentStep],
                            nextStep = stepsMap[index + dir];
                        helpers.setActiveStep(nextStep);
                    },
                    onSubmissionSuccess    : function onSubmissionSuccess(wizardResult) {
                        // Need to add analyzing the result and reacting upon
                        helpers.setSiblingState(dirs.NEXT);
                    },
                    onSubmissionFailure    : function onSubmissionFailure(errors) {
                        var bindingErrors, // to be shown in wizard
                            validationErrors; // to be shown in wizard
                        $scope.messages = undefined;
                        $scope.errors = undefined;
                        if (_.isObject(errors) && _.keys(errors).length > 0) {

                            if (errors.errors.length > 0) {
                                // Server side errors (processing errors)
                                $state.go(errorStateName, {
                                    component: wizardKey,
                                    errors   : errors.errors
                                });
                                return false;
                            } else {
                                validationErrors = errors.validation || [];
                                bindingErrors = errors.binding || [];

                                if (bindingErrors.length > 0 || validationErrors.length > 0) {
                                    $scope.errors = bindingErrors;
                                    $scope.messages = validationErrors;
                                    return false;
                                }
                            }

                            if (!_.isUndefined(errors.messages) && errors.messages.length > 0) {
                                // growl, because these are FeedbackMessages
                            }

                        } else if (_.isString(errors) && errors.length > 0) {
                            dialogs.error('Failed to submit form', errors);
                            return false;
                        }

                        return true;
                    },
                    getActiveStepParentFree: function getActiveStepParentFree() {
                        return this.getActiveStep().replace(wizardKey + '.', '');
                    }
                },
                hooks = {
                    finish  : function finishForm($event) {
                        $event.preventDefault();
                        wizardHandler.submit({
                            $scope : $scope,
                            success: function () {
                                $state.go(exitState);
                            },
                            failure: helpers.onSubmissionFailure
                        });
                    },
                    /**
                     * On <b>cancel</b> hook. Displays simple prompt box and cancels the state
                     * upon yes answer. Cancelling results in reverting to the state from
                     * which wizard was entered. Identified by {@link cookie.lastState}
                     * @param $event current event
                     */
                    cancel  : function cancelForm($event) {
                        $event.preventDefault();
                        dlg = dialogs.confirm(
                            'Wyjdź',
                            'Czy na pewno chcesz zakończyć?'
                        );
                        dlg.result.then(
                            function onConfirm() {
                                $log.debug('Exiting wizard {w}'.format({w: $scope.title}));
                                $state.go(exitState);
                                dlg = undefined;
                            },
                            function onDecline() {
                                dlg = undefined;
                            }
                        );
                    },
                    /**
                     * Call active <b>wizardHandler#next</b> method.
                     * This method is free to do whatever it needs to do.
                     * In order to proceed with the wizard method identified as <b>success</b> must be called.
                     * In case of failure method identified as <b>failure</b> will be called
                     * @param $event current event
                     */
                    next    : function nextStep($event) {
                        $event.preventDefault();
                        wizardResource.stepSubmit(
                            wizardKey,
                            helpers.getActiveStepParentFree(),
                            wizardHandler.getStepFormData($scope, helpers.getActiveStep())
                        ).then(helpers.onSubmissionSuccess, helpers.onSubmissionFailure);
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
                            header = $scope.header;

                        helpers.setActionVisible('finish', (stepIndex === stepsCounts - 1));
                        helpers.setActionVisible('next', (stepIndex >= 0 && !helpers.isActionVisible('finish')));
                        helpers.setActionVisible('previous', (stepIndex !== 0 && stepIndex < stepsCounts));

                        // set active header
                        angular.forEach(header, function headerIt(head) {
                            head.active = (head.position === stepIndex);
                        });

                        return true;
                    }
                },
                actionHelper = {
                    isEnabled: function isEnabled(act) {
                        // only checking next and finish
                        var name = act.name,
                            enabled = true;
                        if (angular.isUndefined(name)) {
                            throw new Error('action name not found')
                        }
                        // check only if something has been entered to the wizard
                        if (name === 'next' || name === 'finish') {
                            enabled = wizardHandler.isActionEnabled(name, $scope.activeState, $scope['wizardForm']);
                        } else {
                            enabled = true;
                        }
                        return enabled;
                    }
                };

            angular.extend($scope, {
                title       : init.getTitle(),
                actions     : init.getActions(),
                header      : init.getSteps(),
                labels      : init.getLabels(),
                formData    : wizardHandler.getFormData(),
                hooks       : hooks,
                debug       : utils.isDebug(),
                actionHelper: actionHelper
            });

            // set up watchers
            $scope.$watch('activeState', watchers.onActiveStepChange);

            // enter first state in definition
            $timeout(function enterFirstStep() {
                var state = init.getInitialState();
                if (state) {
                    helpers.setActiveStep(state);
                }
            }, timeoutDelay);
        }
    }
);
