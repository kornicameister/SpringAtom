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
 * Created by trebskit on 2014-08-24.
 */
define(
    [
        'classes/cmp/wiz/feedbackMessage',
        'classes/cmp/wiz/validationMessage',
        // jsFace to create classes
        'jsface',
        'underscore'
    ],
    function formResult(FeedbackMessage, ValidationMessage) {
        var scopes = {
            WIZARD: 'wizard',
            FORM  : 'form',
            STEP  : 'step'
        };
        return Class(Object, function WizardResult() {
            var getScopeData = function getScopeData(data, scope, key) {
                    if (_.isUndefined(key)) {
                        return data[scope];
                    }
                    return data[scope][key];
                },
                lookupData = function lookupData(data, key) {
                    // for empty key create object
                    var chunk = _.isUndefined(key) ? {} : undefined;

                    _.forEach(scopes, function (scope) {
                        if (_.isUndefined(key)) {
                            chunk[scope] = data[scope];
                            // keep iterating to return completely merged data
                        } else if (_.has(data, scope)) {
                            chunk = data[scope];
                            if (_.has(chunk, key)) {
                                chunk = chunk[key];
                                return false;
                            }
                        }
                        return true;
                    });

                    return chunk;
                };
            return {
                $statics             : {
                    SCOPES: scopes
                },
                constructor          : function (cfg) {
                    this.dataMap = cfg.dataMap || {};
                    this.oid = cfg.oid;
                    this.stepId = cfg.stepId;
                    this.wizardId = cfg.wizardId;
                    this.success = cfg.success;
                    this.debugData = cfg.debugData;
                    if (_.isUndefined(this.success)) {
                        //noinspection JSValidateTypes
                        this.success = true;
                    }

                    // binding, errors etc.

                    this.feedbackMessages = cfg.feedbackMessages || [];
                    this.validationMessages = cfg.validationMessages || [];
                    this.bindingErrors = cfg.bindingErrors || [];
                    this.errors = cfg.errors || [];

                },
                hasErrors            : function hasErrors() {
                    return this.errors.length > 0;
                },
                hasBindingErrors     : function hasBindingErrors() {
                    return this.bindingErrors.length > 0;
                },
                hasFeedbackMessages  : function hasFeedbackMessages() {
                    return this.feedbackMessages.length > 0;
                },
                hasValidationMessages: function hasValidationMessages() {
                    return this.feedbackMessages.length > 0;
                },
                isSuccess            : function () {
                    return this.success;
                },
                getData              : function () {
                    return lookupData(this.dataMap, arguments[0]);
                },
                getWizardData        : function () {
                    return getScopeData(this.dataMap, scopes.WIZARD, arguments[0]);
                },
                getFormData          : function () {
                    return getScopeData(this.dataMap, scopes.FORM, arguments[0]);
                },
                getStepData          : function () {
                    return getScopeData(this.dataMap, scopes.STEP, arguments[0]);
                },
                getFeedbackMessages  : function getMessages() {
                    var local = [];
                    angular.forEach(this.feedbackMessages, function msgIt(msg) {
                        local.push(new FeedbackMessage(msg));
                    });
                    return local;
                },
                getValidationMessages: function getValidationMessages() {
                    var local = [];
                    angular.forEach(this.validationMessages, function msgIt(msg) {
                        local.push(new ValidationMessage(msg));
                    });
                    return local;
                },
                getBindingErrors     : function getBindingErrors() {
                    return this.bindingErrors;
                },
                getErrors            : function getErrors() {
                    return this.errors;
                },
                getViolations        : function getViolations() {
                    return {
                        binding   : this.getBindingErrors(),
                        validation: this.getValidationMessages(),
                        errors    : this.getErrors(),
                        messages  : this.getFeedbackMessages()
                    }
                },
                getErrorsForPath     : function getErrorsForPath() {

                },
                getDebugData         : function getDebugData() {
                    return this.debugData;
                }
            }
        });
    }
);
