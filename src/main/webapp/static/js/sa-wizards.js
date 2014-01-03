/*
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2013]
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

(function (SA, $) {

    if (!SA.wizard) {
        SA.wizard = {};
    }

    /**
     * //TODO missing jsDoc
     * @param cfg
     */
    SA.wizard.applyActionVisibility = function (cfg) {
        var actionsContainer = cfg['container'];
        var actionsElement = cfg['selector'] || 'button';
        var availableActions = cfg['actions'];
        var firstStep = cfg['first'] || false;
        var actions = {};

        $.each(availableActions, function (it, val) {
            actions[val] = true;
        });

        var buttons = $(actionsContainer).find(actionsElement);
        $.each(buttons, function (it, val) {
            var buttonAsJQ = $(val);
            var buttonId = buttonAsJQ.attr('id');
            if (actions[buttonId]) {
                buttonAsJQ.fadeIn();
            } else {
                if (!firstStep) {
                    buttonAsJQ.fadeOut();
                } else {
                    buttonAsJQ.hide();
                }
            }
        });
    };

    SA.wizard.applyDynamicActions = function (cfg) {
        var actionsContainer = cfg['container'];
        var availableActions = cfg['actions'];
        var formId = cfg['formId'];
        var actions = {};

        var buttons = $(actionsContainer);
        if (buttons.find('.x-wizard-dynamic-action').length > 1) {
            buttons.find('.x-wizard-dynamic-action').remove();
        }

        $.each(availableActions, function (it, val) {
            var button = $('<button></button>');
            button = button.html(val['labelName']);
            button = button
                .attr('id', val['eventName'])
                .attr('type', 'submit')
                .attr('name', val['eventName'])
                .attr('class', 'x-wizard-action x-wizard-dynamic-action');
            buttons.prepend(
                button
            );
            if (val['finish']) {
                applyFinishActionDecorator(button.attr('id'), formId);
            } else {
                applyActionDecorator(button.attr('id'), formId);
            }
        });
    };

    /**
     * //TODO missing jsDoc
     * @param cfg
     */
    SA.wizard.applyStepsState = function (cfg) {
        var state = SA.wizard.genStepHeaderId(cfg['stateId']);
        var descriptor = cfg['descriptor'];
        var header = $(cfg['headerSelector']);

        var stateSpanNode = $(header.find('li').find('span#' + state));
        stateSpanNode.removeClass('disabled');
        stateSpanNode.removeClass('enabled');
        stateSpanNode.removeClass('done');
        stateSpanNode.removeClass('error');
        stateSpanNode.addClass('selected');

        $.each(descriptor['predecessors'], function (it, predecessorSpan) {
            var el = $('span#' + SA.wizard.genStepHeaderId(predecessorSpan));
            el.removeClass('selected');
            el.addClass('done');
        });
        $.each(descriptor['successors'], function (it, successorsSpan) {
            var el = $('span#' + SA.wizard.genStepHeaderId(successorsSpan));
            el.removeClass('disabled');
            el.addClass('enabled');
        });

    };

    SA.wizard.genStepHeaderId = function (val) {
        return SA.core.genId(val, 'wiz-step');
    };

    SA.wizard.applyWebFlowDecorators = function (el, formId) {
        console.log('Applying WF decorator over elements > ' + el);
        $.each(el, function (index, val) {
            console.log('Applying WF decorator over > ' + $(val).attr('id'));
            applyFinishActionDecorator(val, formId);
        });
        var not = $('.x-wizard-actions').find('button').not(function (index) {
            var id = $(this).attr('id');
            var match = false;
            $.each(el, function (index, val) {
                if (id === val) {
                    match = true;
                    return false;
                }
                return true;
            });
            return match;
        });
        console.log('Applying AWF decorator over elements > ' + not);
        not.each(function (index, val) {
            console.log('Applying WF decorator over > ' + $(val).attr('id'));
            applyActionDecorator($(val).attr('id'), formId);
        })
    };

    SA.wizard.decorateWizard = function (cfg) {
        var wizardId = cfg['wizardId'];
        var wizardHeaderId = cfg['wizardHeaderId'];
        var wizardContentId = cfg['wizardContentId'];
        var wizardActionsId = cfg['wizardActionsId'];

        var decorations = [];

        decorations.push(new Spring.ElementDecoration({
            elementId  : wizardId,
            widgetType : "dijit.layout.BorderContainer",
            widgetAttrs: {
                title        : $('#' + wizardId).attr('title'),
                gutters      : true,
                liveSplitters: true
            }
        }));
        decorations.push(new Spring.ElementDecoration({
            elementId  : wizardHeaderId,
            widgetType : "dijit.layout.ContentPane",
            widgetAttrs: {
                region: 'leading'
            }
        }));
        decorations.push(new Spring.ElementDecoration({
            elementId  : wizardContentId,
            widgetType : "dijit.layout.ContentPane",
            widgetAttrs: {
                region: 'center'
            }
        }));
        decorations.push(new Spring.ElementDecoration({
            elementId  : wizardActionsId,
            widgetType : "dijit.layout.ContentPane",
            widgetAttrs: {
                region: 'bottom'
            }
        }));

        console.log(wizardId + ' with ' + decorations.length + ' decorations');

        $.each(decorations, function (index, val) {
            Spring.addDecoration(val);
        });
    };

    function applyActionDecorator(val, formId) {
        Spring.addDecoration(new Spring.AjaxEventDecoration({
            elementId: val,
            event    : 'onclick',
            formId   : formId,
            popup    : true,
            params   : {
                fragments: 'wiz.content',
                mode     : "embedded"
            }
        }));
    }

    function applyFinishActionDecorator(val, formId) {
        Spring.addDecoration(new Spring.AjaxEventDecoration({
            elementId: val,
            event    : 'onclick',
            formId   : formId
        }));
    }

}(window.SA = window.SA || {}, jQuery));


