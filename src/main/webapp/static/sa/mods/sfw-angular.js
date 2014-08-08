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
 * Created by trebskit on 2014-07-31.
 */

(function swf_angular() {
    'use strict';

    console.log('swf_angular module');
    var st = window['performance']['now'](),
        fixViewId = 'swf-angular-view-id',
        modalId;

    // creating string formatter if not yet defined
    if (!String.prototype.format) {
        String.prototype.format = function (args) {
            var newStr = this;
            for (var key in args) {
                //noinspection JSUnfilteredForInLoop
                newStr = newStr.replace('{' + key + '}', args[key]);
            }
            return newStr;
        }
    }

    (function swf_hidden_modal_creator() {
        if (!angular.isDefined(modalId)) {
            modalId = 'modal_{id}'.format({id: Math.floor(window['performance']['now']())});
        }
        var body = $('body'),
            lastChild = body.last(),
            tmp = $('<div id="{id}" class="modal fade" role="dialog" aria-hidden="true"></div>'.format({id: modalId}));
        lastChild.append(tmp);
    }());

    (function swf_get_fixed_view_id() {
        if (!window.swf) {
            window.swf = {
                angular: {
                    WIZ_VIEW_ID: (function () {
                        return String(fixViewId);
                    }())
                }
            }
        }
    }());

    (function sfw_angular_loader() {
        var app = angular.module('swf.angular', []),
            errorUtil = {
                newError: function _newError(msg) {
                    return new Error('swf.angular exception occurred,err={err}'.format({
                        err: msg
                    }), 'swf.angular');
                }
            },
            string = {
                trim: function (str) {
                    str = str.replace(/^\s+/, '');
                    for (var i = str.length - 1; i >= 0; i--) {
                        if (/\S/.test(str.charAt(i))) {
                            str = str.substring(0, i + 1);
                            break;
                        }
                    }
                    return str;
                }
            },
            submitService = function submitService($log, loader) {
                var _doSubmit = function _ss_submit(cfg, event) {
                        var formId = cfg.formId || '';

                        if (!angular.isDefined(cfg.sourceId)) {
                            cfg.sourceId = cfg.elementId;
                        }

                        if (formId === '') {
                            loader.getLinkedResource(cfg.sourceId, cfg.params, cfg.popup);
                        } else {
                            _doSubmitForm(cfg.sourceId, cfg.formId, cfg.params);
                        }

                        event.preventDefault();
                    },
                    _doSubmitForm = function _ss_sf(sourceId, formId, params) {
                        var content = {};

                        angular.forEach(params, function (val, key) {
                            if (angular.isDefined(val)) {
                                content[key] = val;
                            }
                        });

                        var sourceComponent = $('#{id}'.format({
                            id: sourceId
                        }));

                        if (sourceComponent != null) {
                            if (sourceComponent.val() != undefined && sourceComponent.attr('type') && ("button,submit,reset").indexOf(sourceComponent.attr('type')) < 0) {
                                content[sourceId] = sourceComponent.val();
                            } else if (sourceComponent.attr('name') != undefined) {
                                content[sourceComponent.attr('name')] = sourceComponent.attr('name');
                            } else {
                                content[sourceId] = sourceId;
                            }
                        }

                        if (!content['ajaxSource']) {
                            content['ajaxSource'] = sourceId;
                        }

                        var formNode = $('#{id}'.format({
                                id: formId
                            })),
                            formMethod = string.trim(formNode.attr('method'));

                        formMethod = formMethod.length > 0 ? formMethod.toUpperCase() : "GET";

                        loader.submitForm(formId, content, formMethod === 'POST');
                    };
                return {
                    submit: _doSubmit
                }
            },
            jsService = function ($log) {
                var _collectScripts = function _jsService_evalScripts(text, replace, evaluate) {
                        evaluate = evaluate || false;
                        replace = replace || true;

                        $log.debug('collectScripts :: in progress, text length {d}, replace {r}, evaluate {b}'.format({
                            d: text.length,
                            r: replace,
                            b: evaluate
                        }));

                        var st = window['performance']['now'](),
                            et,
                            scriptNodes,
                            scriptPattern = '(?:<script(.|[\n|\r])*?>)((\n|\r|.)*?)(?:<\/script>)',
                            extractedScriptNodes = [],
                            matchAll = new RegExp(scriptPattern, 'img'),
                            matchOne = new RegExp(scriptPattern, 'im');

                        scriptNodes = text.match(matchAll);
                        if (scriptNodes != null) {
                            for (var i = 0; i < scriptNodes.length; i++) {
                                var script = (scriptNodes[i].match(matchOne) || ['', '', ''])[2];
                                script = script
                                    .replace(/<!--/mg, '')
                                    .replace(/\/\/-->/mg, '')
                                    .replace(/<!\[CDATA\[(\/\/>)*/mg, '')
                                    .replace(/(<!)*\]\]>/mg, '');
                                extractedScriptNodes.push(script);
                            }
                        }

                        if (replace) {
                            $log.debug('collectScripts :: Replace is true, replacing...');
                            text = text.replace(matchAll, '<script> // Original script removed to avoid re-execution </script>');
                        }

                        if (evaluate) {
                            $log.debug('collectScripts :: Evaluate is true, calling evaluation...');
                            _evalScripts(extractedScriptNodes);
                        }

                        et = window['performance']['now']();

                        $log.debug('collectScripts :: collected {d} script nodes in {t} s'.format({
                            d: extractedScriptNodes.length,
                            t: ((et - st) / 1000)
                        }));

                        return {
                            text    : text,
                            js      : extractedScriptNodes,
                            evaluate: evaluate,
                            replace : replace
                        }
                    },
                    _evalScripts = function _jsService_evalScripts(scripts, args) {

                        var st = window['performance']['now']();

                        scripts = scripts || [];
                        args = args || {};

                        if (scripts.length == 0) {
                            $log.warn('evalScripts :: passed scripts array is empty');
                        }
                        angular.forEach(scripts, function (script) {
                            eval(script);
                        });

                        $log.debug('evalScripts :: evaluated {d} scripts in {dd} s'.format({
                            d : scripts.length,
                            dd: (window['performance']['now']() - st)
                        }));
                    };
                return {
                    collectScripts: _collectScripts,
                    evalScripts   : _evalScripts
                }
            },
            renderService = function renderService($log, $compile, $rootScope) {
                /**
                 * Tries to locate the {@code scope} in the passed object. If it is not there,
                 * method creates it using {@code rootScope}
                 * @param arg the configuration object
                 * @returns {object} new scope
                 * @private
                 */
                var _getScope = function _getScope(arg) {
                        if (!angular.isDefined(arg) || !angular.isObject(arg)) {
                            $log.warn('arg is either not defined or not an object');
                        }
                        var localScope,
                            scopeKey = 'scope',
                            isolate = arguments.length == 2 ? arguments[1] : true;
                        if (!angular.isDefined(arg[scopeKey]) || (Object.getOwnPropertyNames(arg[scopeKey]).length === 0)) {
                            localScope = $rootScope.$new(isolate);
                        }
                        return localScope;
                    },
                    /**
                     * Takes care of creating new modal window and executing angular compilation for
                     * nested directives
                     * @param response response text
                     * @param args arguments
                     * @private
                     */
                    _renderToModal = function _renderToModal(response, args) {
                        $log.debug('_renderToModal to modal in progress');
                        var localModal = $('#{id}'.format({
                            id: modalId
                        }));

                        if (!angular.isDefined(localModal)) {
                            throw errorUtil.newError('Failed to retrieve hidden modal element');
                        }

                        args = args || {};
                        args.scope = _getScope(args);

                        var tt = $compile(angular.element('<div></div>').html(response))(args.scope),
                            option = {
                                keyboard: true,
                                show    : true
                            };
                        localModal.append(tt);
                        localModal
                            .modal(option)
                            .on('hide.bs.modal', function onDestroyAppender() {
                                localModal.children().remove();
                            });
                    },
                    _showErrModal = function _showErrorModal(message, cfg) {
                        _renderToModal(message);
                    },
                    _replaceContent = function replaceContent(response, args) {
                        $log.debug('_replaceContent to modal in progress');
                        var localModal = $('#{id}'.format({
                                id: modalId
                            })),
                            toReplace = localModal.find('#{id}'.format({
                                id: fixViewId
                            })),
                            nodeList = localModal.find('*');

                        args = args || {};
                        args.scope = _getScope(args);

                        var tt = $compile(angular.element('<div></div>').html(response))(args.scope);
                        toReplace.children().remove();
                        toReplace.append(tt.first());
                    };
                return {
                    renderToModal : _renderToModal,
                    showErrorModal: _showErrModal,
                    replaceContent: _replaceContent
                }
            },
            loaderService = function loaderService($log, $http, $q, swfRender, swfJsHelper) {

                var formHelper = {
                        collectFormInput: function _collectForm(form) {
                            var dd = {};
                            form.find('input').each(function (idx, input) {
                                dd[$(input).attr('name')] = $(input).val();
                            });
                            return dd;
                        }
                    },
                    loaders = {
                        submitForm       : function _submitForm(formId, params, hasBody) {
                            var formNode = $('#{id}'.format({
                                    id: formId
                                })),
                                formMethod = string.trim(formNode.attr('method')).toUpperCase(),
                                cfg = {
                                    rId    : Math.floor(window['performance']['now']() / 1000),
                                    url    : formNode.attr('action'),
                                    params : params || {},
                                    hasBody: hasBody || false,
                                    method : formMethod.length > 0 ? formMethod.toUpperCase() : "GET"
                                };
                            angular.extend(cfg.params, formHelper.collectFormInput(formNode));
                            requestUtils
                                .doRequest(cfg)
                                .then(function onSuccess(data) {
                                    $log.debug(data);
                                }, function onError() {
                                    $log.debug('error');
                                })
                        },
                        getLinkedResource: function _getLinkedResource(linkId, params, modal) {
                            $log.debug('getLinkedResource(linkId={a})'.format({
                                a: linkId
                            }));
                            var href = $('#' + linkId).attr('href');
                            if (!angular.isDefined(href)) {
                                throw errorUtil.newError('Failed to find href on node with id={i}'.format({
                                    i: linkId
                                }))
                            }
                            return loaders.getResource(href, params, modal)
                        },
                        getResource      : function _getResource(resourceUri, params, modal) {
                            var cfg = {
                                rId   : Math.floor(window['performance']['now']() / 1000),
                                url   : resourceUri,
                                params: params || {},
                                modal : modal || false
                            };
                            requestUtils
                                .doRequest(cfg)
                                .then(function onSuccess(data) {
                                    $log.debug(data);
                                }, function onError() {
                                    $log.debug('error');
                                })
                        }
                    },
                    requestUtils = {
                        dataPromise         : {},
                        directiveData       : {},
                        responseTransformer : function _responseTransformer(response) {
                            var st = window['performance']['now']();
                            {
                                if ((string.trim(response).length == 0)) {
                                    swfRender.showErrorModal('Received empty response with no Spring redirect headers. If this is intentional set the response status code to 204 or 205.');
                                }
                            }
                            var et = window['performance']['now']();
                            $log.debug('responseTransformer completed in {d} ms'.format({
                                d: ((et - st) / 1000)
                            }));
                            return response;
                        },
                        getHttpConfiguration: function _getHttpConfiguration(cfg) {
                            return {
                                url              : cfg.url,
                                method           : cfg.method || 'GET',
                                params           : cfg.params || {},
                                cache            : cfg.params.cache || true,
                                responseType     : "text",
                                headers          : {
                                    "Accept"          : "text/html;type=ajax",
                                    "X-Requested-With": "XMLHttpRequest"
                                },
                                transformResponse: requestUtils.responseTransformer,
                                modal            : cfg.modal || false,
                                scope            : cfg.scope || {}
                            }
                        },
                        onSuccess           : function ru_onSuccess(response, status, headers, args) {
                            var redirectURL = headers('Spring-Redirect-URL'),
                                modalViewHeader = headers('Spring-Modal-View'),
                                modalView = ((angular.isString(modalViewHeader) && modalViewHeader.length > 0) || args.modal);

                            if (angular.isString(redirectURL) && redirectURL.length > 0) {
                                if (modalView) {
                                    //render a popup with the new URL
                                    Spring.remoting._renderURLToModalDialog(redirectURL);
                                    return response;
                                }
                                else {
                                    if (redirectURL.indexOf("/") >= 0) {
                                        window.location = window.location.protocol + "//" + window.location.host + redirectURL;
                                    } else {
                                        var location = window.location.protocol + "//" + window.location.host + window.location.pathname;
                                        var appendIndex = location.lastIndexOf("/");
                                        location = location.substr(0, appendIndex + 1) + redirectURL;
                                        if (location == window.location) {
                                            Spring.remoting.getResource(location, args.content, false);
                                        }
                                        else {
                                            window.location = location;
                                        }
                                    }
                                    return response;
                                }
                            } else if ((string.trim(response).length == 0) && (headers.status != 204) && (headers.status != 205)) {
                                swfRender.showErrorModal('Received empty response with no Spring redirect headers. If this is intentional set the response status code to 204 or 205.');
                            }

                            var collectResult = swfJsHelper.collectScripts(response, true);

                            if (modalView) {
                                swfRender.renderToModal(collectResult.text, args);
                            } else {
                                swfRender.replaceContent(collectResult.text, args);
                            }

                            swfJsHelper.evalScripts(collectResult.js, args);
                        },
                        onError             : function ru_onError(response, status, headers, args) {
                            swfRender.showErrorModal(response, {
                                status: status,
                                args  : args
                            });
                        },
                        doRequest           : function ru_doRequest(cfg) {
                            $log.log('loading doRequest(uri={url})'.format({
                                url: cfg.url
                            }));

                            // unpack cfg
                            var key = cfg.rId,
                                url = cfg.url;

                            // pre validate
                            if (!angular.isDefined(key) || key === 0) {
                                throw errorUtil.newError('Key is not defined or equal to 0')
                            }
                            if (!angular.isDefined(url)) {
                                throw errorUtil.newError('Url is missing');
                            }

                            if (requestUtils.dataPromise[key]) {
                                $log.debug('dataPromise[' + key + '] is already promised');
                                return requestUtils.dataPromise[key];
                            }

                            var deferred = $q.defer();
                            requestUtils.dataPromise[key] = deferred.promise;

                            $log.debug('dataPromise[' + key + '] has been created');

                            if (requestUtils.directiveData[key]) {
                                deferred.resolve(requestUtils.directiveData[key]);
                            } else {
                                $log.debug('directiveData[' + key + '] GET call');
                                $http(requestUtils.getHttpConfiguration(cfg))
                                    .success(requestUtils.onSuccess)
                                    .error(requestUtils.onError)
                            }

                            return requestUtils.dataPromise[key];
                        }
                    };

                return {
                    getLinkedResource: loaders.getLinkedResource,
                    submitForm       : loaders.submitForm
                }
            },
            swfViewDirective = function _swfViewDirective() {
                return {
                    restrict  : 'A',
                    transclude: true,
                    replace   : true,
                    template  : (function createTemplate() {
                        var arr = [];
                        arr.push('<div id="{{swfViewId}}" role="contentinfo">');
                        arr.push('<div ng-transclude><div>');
                        arr.push('</div>');
                        return arr.join('');
                    }()),
                    link      : function (scope) {
                        scope.swfViewId = fixViewId;
                    }
                }
            };

        app.factory('swfJsHelper', ['$log', jsService])
            .factory('swfRender', ['$log', '$compile', '$rootScope', renderService])
            .factory('swfSubmit', ['$log', 'swfLoader', submitService])
            .factory('swfLoader', ['$log', '$http', '$q', 'swfRender', 'swfJsHelper', loaderService])
            .directive('swfView', swfViewDirective);
    }());

    var et = window['performance']['now']();
    console.log('swf_angular module loaded in ' + ((et - st) / 1000) + ' ms');
}());
