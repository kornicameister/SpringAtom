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
        'angular',
        'classes/cmp/wiz/feedbackMessage',
        // jsFace to create classes
        'jsface'
    ],
    function formResult(angular, FeedbackMessage) {
        var scopes = {
            WIZARD   : 'wizard',
            FORM     : 'form',
            FORM_DATA: 'formdata',
            STEP     : 'step'
        };
        return Class(function WizardResult() {
            var getScopeData = function getScopeData(data, scope, key) {
                    if (angular.isUndefined(key)) {
                        return data[scope];
                    }
                    return data[scope][key];
                },
                lookupData = function lookupData(data, key) {
                    var chunk = undefined;
                    angular.forEach(scopes, function (scope) {
                        if (angular.isUndefined(chunk)) {
                            if (angular.isUndefined(key)) {
                                chunk = data[scope];
                            } else {
                                chunk = data[scope][key];
                            }
                        }
                    });
                    return chunk;
                };
            return {
                $statics           : {
                    SCOPES: scopes
                },
                constructor        : function (cfg) {
                    this.dataMap = cfg.dataMap || {};
                    this.messages = cfg.messages || [];
                    this.errors = cfg.errors || [];
                    this.validationErrors = cfg.validationErrors || [];
                    this.oid = cfg.oid;
                    this.stepId = cfg.stepId;
                    this.wizardId = cfg.wizardId;
                    this.success = cfg.success;
                    if (!angular.isDefined(this.success)) {
                        //noinspection JSValidateTypes
                        this.success = true;
                    }
                },
                hasErrors          : function hasErrors() {
                    return this.errors.length > 0;
                },
                hasValidationErrors: function hasValidationErrors() {
                    return this.validationErrors.length > 0;
                },
                hasMessages        : function hasMessages() {
                    return this.messages.length > 0;
                },
                isSuccess          : function () {
                    return this.success && !this.hasErrors() && !this.hasValidationErrors();
                },
                getData            : function () {
                    return lookupData(this.dataMap, arguments[0]);
                },
                getWizardData      : function () {
                    return getScopeData(this.dataMap, scopes.WIZARD, arguments[0]);
                },
                getFormData        : function () {
                    return getScopeData(this.dataMap, scopes.FORM, arguments[0]);
                },
                getFormDataData    : function () {
                    return getScopeData(this.dataMap, scopes.FORM_DATA, arguments[0]);
                },
                getStepData        : function () {
                    return getScopeData(this.dataMap, scopes.STEP, arguments[0]);
                },
                getMessages        : function getMessages() {
                    var local = [];
                    angular.forEach(this.messages, function msgIt(msg) {
                        local.push(new FeedbackMessage(msg));
                    });
                    return local;
                },
                getErrors          : function getErrors() {
                    return this.errors;
                }
            }
        });
    }
);
