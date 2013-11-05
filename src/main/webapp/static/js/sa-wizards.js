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

/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2013]                   *
 *                                                                                                *
 * [SpringAtom] is free software: you can redistribute it and/or modify                           *
 * it under the terms of the GNU General Public License as published by                           *
 * the Free Software Foundation, either version 3 of the License, or                              *
 * (at your option) any later version.                                                            *
 *                                                                                                *
 * [SpringAtom] is distributed in the hope that it will be useful,                                *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of                                 *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                                  *
 * GNU General Public License for more details.                                                   *
 *                                                                                                *
 * You should have received a copy of the GNU General Public License                              *
 * along with [SpringAtom].  If not, see <http://www.gnu.org/licenses/gpl.html>.                  *
 **************************************************************************************************/

(function (SA, $) {

    if (!SA.wizard) {
        SA.wizard = {};
    }

    function checkAndAppendClass(el) {
        if (!el.hasClass('swMain')) {
            el.addClass('swMain');
        }
        return el;
    }

    var registry = {},
        Wizard = function (cfg, el) {
            var me = this,
                target = checkAndAppendClass(el);
            me['steps'] = cfg['steps'];
            me['stepPrefix'] = cfg['stepPrefix'];
            me['url'] = cfg['url'];
            me['wizard'] = target.smartWizard({
                enableAllSteps    : true,
                enableFinishButton: true,
                labelNext         : SA.core.getLocalizedMsg('button.next.short'),
                labelPrevious     : SA.core.getLocalizedMsg('button.previous.short'),
                labelFinish       : SA.core.getLocalizedMsg('button.ok'),
                onFinish          : $.proxy(me.submit, me)
            });
        };
    Wizard.prototype.submit = function () {
        /*
         step = {
         id: {String}
         collector : function(data){}
         }
         */
        var me = this,
            steps = me['steps'],
            stepPrefix = me['stepPrefix'],
            data = [];
        $.each(steps, function (index, step) {
            var array = $('#' + step['id']).find('form').serializeArray(),
                collector = step['collector'];

            if (typeof collector === 'undefined') {
                collector = function (data) {
                    return data;
                }
            }

            data.push({
                name: stepPrefix + index,
                data: collector.apply(me, [array])
            });
        });
        $.ajax({
            url        : me['url'],
            type       : 'POST',
            contentType: "application/json",
            dataType   : 'json',
            data       : JSON.stringify({
                data: data
            }),
            success    : function () {
                SA.core.showSuccess('Ok');
                delete registry[me['wizard'].attr('id')];
                me['wizard'].remove();
            },
            error      : function (xhr, status, error) {
                SA.core.showError('Failed to submit wizard=' + me['url'] + ' => ' + error);
                delete registry[me['wizard'].attr('id')];
                me['wizard'].remove();
            }
        })
    };

    $.fn.wizard = function (cfg) {
        registry[this.attr('id')] = new Wizard(cfg, this);
    }

}(window.SA = window.SA || {}, jQuery));
