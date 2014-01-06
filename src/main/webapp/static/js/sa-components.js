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

    if (!SA.infopage) {
        SA.infopage = {};
    }

    var infoPageHrefTemplate = '<a href="{href}" class="x-infopage-link"><i class="fa fa-info-circle fa-color-black"></i></a>';
    var actionLinkTemplate = '<button id="{id}" data-href="{href}" data-method="{method}" data-mode="{mode}" class="x-action-link"><i class="fa fa-lg fa-magic fa-color-black"></i></button>';
    var infoPageHrefTemplateWithName = '<a href="{href}" class="x-infopage-link"><i class="fa fa-info-circle fa-color-black">{name}</i></a>';

    function loadContent(dataUrl, callback) {
        return $.ajax({
            url        : dataUrl,
            type       : 'get',
            contentType: 'application/x-spring-data-compact+json',
            success    : function (data) {
                callback.apply(this, [data]);
            }
        });
    }

    function loadRenderedView(url, data, callback) {
        return $.ajax({
            url        : url,
            data       : JSON.stringify(data),
            contentType: 'application/json',
            type       : 'post',
            success    : function (data) {
                callback.apply(this, [data, true]);
            },
            failure    : function () {
                callback.apply(this, [data, false]);
            }
        });
    }

    function maskContainer(container, hide) {
        container.fadeToggle(666, 'swing')
    }

    function flattenData(data, dataType) {
        if (dataType === 'rest') {
            var restData = {};
            var origin = window.location.origin;
            $.each(data, function (key, value) {
                if (!$.isPlainObject(value) && !$.isArray(value)) {
                    restData[key] = value;
                } else if ($.isArray(value) && key === 'links') {
                    $.each(value, function (it, val) {
                        var href = val['href'];
                        if (val['rel'] !== 'self') {
//                            href = href.split(origin);
//                            href = href[1];
                            restData[val['rel']] = href;
                        } else {
                            var vv = href.split('/');
                            restData['id'] = vv[vv.length - 1];
                        }
                    })
                }
            });
            data = restData;
        }
        return data;
    }

    function transformIntoMap(data) {
        var map = [];
        $.each(data, function (key, value) {
            map.push({
                key  : key,
                value: value
            });
        });
        return map;
    }

    function getTargetKeys(attributes) {
        var keys = [];
        $.each(attributes, function (key) {
            keys.push(key);
        });
        return keys;
    }

    function getDomainObjectId() {
        var href = $(location).attr('href').split('/');
        return href[href.length - 1];
    }

    $.fn.loadDomainPage = function (cfg) {
        var container = $(this);
        var data = [];

        maskContainer(container, true);

        data.push({key: 'domainContext', value: container.attr('data-pageContext')});
        data.push({key: 'type', value: container.attr('data-pageType')});
        data.push({key: 'rel', value: container.attr('data-pageRel')});

        data.push({key: 'infopage', value: cfg['infoPage']});
        data.push({key: 'view', value: cfg['view']});
        data.push({key: 'container', value: container.attr('id')});
        data.push({key: 'objectId', value: getDomainObjectId()});

        loadRenderedView(
            cfg['view'],
            {
                values: data
            },
            function (viewContent) {
                viewContent = $(viewContent);
                container.append(viewContent);
                maskContainer(container, false);
                $(container).componentActions();
            }
        );
    };

    window.renderInfoPageLink = function (data) {
        if (!data) {
            return '?';
        }
        if (jQuery.isPlainObject(data) && data['href']) {
            return infoPageHrefTemplate.replace('{href}', data['href']);
        } else if (typeof data === 'string' && data !== '???') {
            return infoPageHrefTemplate.replace('{href}', data);
        } else {
            return '?';
        }
    };
    var _actions = {};

    function getAction(href) {
        return _actions[href];
    }

    window.renderTableAction = function (data) {
        if (jQuery.isPlainObject(data)) {
            return actionLinkTemplate
                .replace('{id}', data['id'])
                .replace('{method}', data['type'])
                .replace('{mode}', data['mode'])
                .replace('{href}', data['url']);
        }
        return '';
    };
    window.renderValue = function (data) {
        return data;
    };
    window.renderInfoPageLinkWithName = function (data, name) {
        return infoPageHrefTemplateWithName.replace('{href}', data).replace('{name}', name);
    };

    $.fn.componentActions = function () {

        $(this).find('.x-action-link').each(function (it, value) {
            value = $(value);
            if (value.hasClass('x-action-link') && value.attr('data-mode') === 'popupAction') {
                var hiddenForm = $('<form></form>');
                hiddenForm.attr('class', 'x-hidden')
                    .attr('id', value.attr('id') + '-form')
                    .attr('method', value.attr('data-method'))
                    .attr('action', value.attr('data-href'));
                value.append(hiddenForm);

                Spring.addDecoration(new Spring.AjaxEventDecoration({
                    elementId: value.attr('id'),
                    formId   : hiddenForm.attr('id'),
                    event    : 'onclick',
                    popup    : true,
                    params   : {
                        mode: "embedded"
                    }
                }));
            }
        });

        $(this).click(function (event) {
            var target = $(event.target).parent();
            if (target.hasClass('x-action-link')) {
                if (target.attr('data-mode') === 'ajaxAction') {
                    event.stopPropagation();
                    $.ajax({
                        url    : target.attr('data-href'),
                        method : target.attr('data-method'),
                        success: function (data) {
                            console.log('Success ' + JSON.stringify(data));
                        },
                        failure: function (data) {
                            console.log('Failure ' + JSON.stringify(data));
                        }
                    });
                    return false;
                } else if (target.attr('data-mode') === 'linkAction') {
                    window.location = target.attr('data-href');
                    return true;
                } else if (target.attr('data-mode') === 'downloadAction') {
                    window.location = target.attr('data-href');
                    return true;
                }
            }
            return true;
        });
    };

    // export

    SA.infopage.getInfoPageLink = function (cfg) {
        var target = cfg['target'].split('?');
        var contentTarget = undefined;
        $.each(target, function (it, value) {
            if (typeof contentTarget === 'undefined') {
                contentTarget = $(value);
            } else {
                contentTarget = contentTarget.find(value);
            }
        });
        var value = cfg['value'];
        $.ajax({
            url        : value,
            type       : 'get',
            contentType: 'application/json',
            success    : function (data) {
                data = data['links'];
                $.each(data, function (it, link) {
                    if (link['rel'] === 'self') {
                        contentTarget.append(renderInfoPageLinkWithName(SA.infopage.getLink(link['href']), 'Infopage'));
                    }
                });
            }
        });
    };

    SA.infopage.getLink = function (href) {
        return href.replace('rest', 'app/ip')
    };

    SA.infopage.getId = function (href) {
        var split = href.split('/');
        return  split[split.length - 1];
    };

    $.fn.loadBuilderView = function (cfg) {
        var container = $(this);
        var data = transformIntoMap(cfg['data'] || {});
        var target = cfg['target'] || container;

        var callback = cfg['callback'] || function (viewContent, success) {
            if (success === false) {
                target.append('General failure -> should be programmed with some sort of nice exception');
            } else {
                viewContent = jQuery(viewContent);

                var js = viewContent.filter('script');
                var vv = viewContent.not(js).not(':text');

                target.append(vv).append(js);
            }
        };

        return  $.ajax({
            url        : cfg['url'],
            data       : JSON.stringify({values: data}),
            contentType: 'application/json',
            type       : 'post',
            success    : callback,
            failure    : function () {
                callback.apply(this, [data, false]);
            }
        });
    }

}(window.SA = window.SA || {}, jQuery));