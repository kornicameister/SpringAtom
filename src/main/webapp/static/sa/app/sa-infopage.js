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
 * Created by kornicameister on 18.07.14.
 */
(function SpringAtom_InfoPage() {
	"use strict";

	var app = angular.module('springatom.infopage', ['springatom.component']),
		/**
		 * Defines InfoPageAttribute class. Used in {@link app.factory()}
		 * @param AbstractComponent super class
		 * @return {*|class} InfoPageAttribute
		 */
		defineInfoPageAttributeClass = function defineInfoPageAttributeClass(AbstractComponent) {
			return Class(AbstractComponent, function () {

				var getValueProperty = function _getValueProperty(value, prop) {
					if (!value) {
						return undefined;
					}
					return value.hasOwnProperty(prop) ? value[prop] : undefined;
				};

				return {
					constructor: function (config, value) {
						this.path = config['path'];
						this.value = value;
						this.$class.$super.call(this, config);
					},
					getPath    : function _getPath() {
						return this.path;
					},
					getIcon    : function _getIcon() {
						return getValueProperty(this.value, 'icon');
					},
					getType    : function _getType() {
						return getValueProperty(this.value, 'type');
					},
					getValue   : function _getValue() {
						return getValueProperty(this.value, 'value');
					},
					getRawValue: function _getRawValue() {
						return getValueProperty(this.value, 'rawValue');
					}
				}
			});
		},
		defineInfoPagePanelClass = function defineInfoPagePanelClass(Component, InfoPageAttribute) {

			var unwrapIcon = function unwrapIcon(icon) {
					if (icon) {
						return icon['iconCls'];
					}
					return undefined;
				},
				unwrapAttributes = function unwrapAttributes(attributes, data) {
					var local = [];
					if (!attributes || attributes.length < 1) {
						return local;
					}
					angular.forEach(attributes, function (attr) {
						var valueOfAttr = data[attr['path']];
						local.push(new InfoPageAttribute(attr, valueOfAttr));
					});
					return local;
				};

			return Class(Component, function () {
				return {
					constructor  : function (config, data) {
						this.icon = unwrapIcon.call(this, config['iconCfg']);
						this.label = config['label'];
						this.position = config['position'];
						this.attributes = unwrapAttributes.call(this, config['content'], data);
						this.$class.$super.call(this, config);    // Invoke parent's constructor
					},
					/**
					 * Returns position of the panel
					 * @return {number}
					 */
					getPosition  : function () {
						return this.position;
					},
					/**
					 * Returns label of the panel
					 * @return {string}
					 */
					getLabel     : function () {
						return this.label;
					},
					/**
					 * Returns icon CSS class
					 * @return {string}
					 */
					getIcon      : function () {
						return this.icon;
					},
					/**
					 * Returns attributes of the panel
					 * @return {attributes|*}
					 */
					getAttributes: function () {
						return this.attributes;
					}
				}
			});
		},
		defineInfoPageCMPClass = function defineInfoPageComponent(Component, InfoPagePanel) {
			return Class(Component, function () {

				var getPanels = function _getPanels(content, data) {
					var panels = [];
					angular.forEach(content, function (panel) {
						panels.push(new InfoPagePanel(panel, data));
					});
					return panels;
				};

				return {
					constructor: function (config, data) {
						this.id = config.id;
						this.data = data;
						this.panels = getPanels(config['content'], data);
						this.$class.$super.call(this, config);    // Invoke parent's constructor
					},
					/**
					 * Return is of the InfoPage (i.e. config['id'])
					 * @return {string} the id
					 */
					getId      : function () {
						return this.id;
					},
					/**
					 * Returns panels defined in this infopage
					 * @return {panels|*}
					 */
					getPanels  : function () {
						return this.panels;
					}
				}
			});
		},
		ipCfgRouter = function IP_configureRouter($stateProvider) {
			$stateProvider.state('infopage', {
				url        : '/sa/infopage/:domain/:id',
				/**
				 * Points to generic view holding Angular based InfoPage View view
				 */
				templateUrl: '/app/cmp/ip/generic',
				controller : [
					'$scope', '$stateParams', 'ipService',
					function ($scope, $stateParams) {
						$scope.infopage = {
							key   : $stateParams.id,
							domain: $stateParams.domain
						};
					}
				]
			});
		},
		ipService = function ipService($log, $http, $q) {
			$log.debug('ipService loading...');

			var urlTemplate = '/app/cmp/{what}/ip/{domain}/{id}',
				getUrl = function (arg, what) {
					return urlTemplate.format({
						domain: arg.domain,
						id    : arg.key,
						what  : what
					});
				},
				getParams = function () {
					return {
						method: 'GET',
						cache : true
					}
				},
				doLoad = function loadIpCfg(object, what, getParamForRequest) {
					var whatData,
						whatDataPromise,
						methodToBuildParams = getParamForRequest || getParams;

					$log.debug('Loading ', what, ' for InfoPage');
					if (!object.key || !object.domain) {
						throw new Error('Invalid IP ', what, ' data');
					}

					if (whatDataPromise) {
						$log.debug(what, ' promised, key=', object.domain);
						return whatDataPromise;
					}

					var deferred = $q.defer();
					whatDataPromise = deferred.promise;

					if (whatData) {
						deferred.resolve(whatData);
					} else {
						$log.debug('Loading ', what, ' for key = ', object.domain);

						var httpConfiguration = methodToBuildParams.call(this, object, what);
						if (!httpConfiguration['url']) {
							httpConfiguration['url'] = getUrl(object, what);
						}

						$http(httpConfiguration)
							.success(function (data) {
								whatData = data;
								deferred.resolve(whatData);
							})
							.error(function () {
								deferred.reject('Failed to load {what}'.format({what: what}));
							});
					}

					return whatDataPromise;
				},
				getUiSref = function _getUiSref(href) {
					var prefix = '/app/cmp/ip/',
						stripped = href.replace(prefix, '').split('/');
					return {
						domain: stripped[0],
						id    : stripped[1]
					};
				};

			return {
				loadConfiguration: function (arg) {
					return doLoad(arg, 'config');
				},
				loadData         : function (arg, getParams) {
					return doLoad(arg, 'data', getParams);
				},
				getInfoPageURI   : getUiSref,
				toInfoPageLink   : function (attribute) {
					$log.debug('toInfoPageLink(attribute=', attribute, ')');
					if (angular.isObject(attribute)) {

						var value = attribute.value,
							linkCfg = {};

						linkCfg.linkLabel = value.linkLabel;
						linkCfg.uiSref = getUiSref(value.value);

						return linkCfg;
					} else {
						throw new Error('Attribute(val=', attribute, ') is not InfoPageAttribute');
					}
				}
			}
		},
		ipHeaderDirective = function ipHeaderDirective() {
			return {
				restrict: 'E',
				scope   : {
					label: '=label'
				},
				template: (function computeTemplate() {
					var array = [];
					array.push('<div class="page-header">');
					array.push('<h3 ng-show="label">{{label}}</h3>');
					array.push('</div>');
					return array.join('');
				}())
			}
		},
		infopageAttributeDirective = function infopageAttributeDirective(ipService) {
			function getTemplate() {
				var array = [];
				array.push('<div class="col-lg-2 x-ip-attr-label">{{label}}</div>');
				array.push('<div class="col-lg-9">');
				array.push('<span ng-if="!isLink">{{value}}</span>');
				array.push('<span ng-if="isLink">');
				array.push('<a ui-sref="infopage({domain:\'{{uiSref.domain}}\',id:{{uiSref.id}}})">{{linkLabel}}</a>');
				array.push('</span>');
				array.push('<icon ng-show="icon" configuration="icon"></icon>');
				array.push('</div>');
				return array.join('');
			}

			return {
				restrict: 'E',
				scope   : {
					attribute: '=attribute'
				},
				link    : function (scope) {
					var attr = scope.attribute;
					scope.label = attr.getLabel ? attr.getLabel() : attr.label;
					scope.value = attr.getValue ? attr.getValue() : attr.value;
					scope.icon = attr.getIcon ? attr.getIcon() : attr.icon;
					scope.isLink = (function isLinkAttribute() {
						return attr.getType() === 'linkGuiComponent'
					}());
					if (scope.isLink === true) {
						var linkConfiguration = ipService.toInfoPageLink(attr);
						if (!linkConfiguration) {
							scope.isLink = false;
						} else {
							scope.uiSref = linkConfiguration.uiSref;
							scope.linkLabel = linkConfiguration.linkLabel;
						}
					}
				},
				template: getTemplate()
			}
		},
		infoPageController = function InfoPageController($log, $scope, ipService, InfoPageComponent) {
			var me = this,
				currentStep = 0,
				stepsTotalCount = 4,
				rawConfig,
				rawData;

			me.data = undefined;

			me.isLoading = function () {
				return !me.data;
			};
			me.isDebug = function () {
				return false;
			};
			me.getLoadingProgress = function () {
				var humanized = arguments.length == 1,
					progress = currentStep / stepsTotalCount;
				return humanized ? (progress * 100.0) : progress;
			};

			function combineDataAndFinish() {
				$log.debug('Configuration and data load for infoPage=[{ip}/{key}]'.format({
					ip : $scope.$parent.infopage.domain,
					key: $scope.$parent.infopage.key
				}));

				me.data = new InfoPageComponent(rawConfig, rawData);

				rawConfig = undefined;
				rawData = undefined;

				currentStep += 1;
			}

			function loadDataAfterConfig() {

				if (me.error) {
					$log.error('Cannot load data, found error={error}'.format({
						error: me.error
					}));
					return;
				}

				var config = rawConfig;

				if (!config) {
					throw new Error('Called without configuration loaded');
				}

				function infoPageDataRequestCreate(ip) {
					return {
						method: 'POST',
						url   : '/app/cmp/data/ip',
						cache : false,
						data  : (function buildParams() {
							var params = {
								pageId: config['id'],
								id    : ip['key'],
								domain: config['domain']
							};
							params['version'] = ip['version'] || -1;
							params['revision'] = ip['revision'] || -1;
							params['attributes'] = (function () {
								var localArray = [];
								angular.forEach(config.content, function (panel) {
									angular.forEach(panel.content, function (attribute) {
										localArray.push({
											path: attribute['path'],
											type: attribute['displayAs']
										})
									});
								});
								return localArray;
							}());
							currentStep += 1;
							return params;
						}())
					}
				}

				ipService
					.loadData($scope.$parent.infopage, infoPageDataRequestCreate)
					.then(function onSuccess(data) {
						$log.debug('Loaded configuration object in {time} ms, builder = {builder}'.format({
							time   : moment(data['time']).milliseconds(),
							builder: data['builtBy']
						}));
						currentStep += 1;
						rawData = data.data;
						combineDataAndFinish();
					}, function onError() {
						me.error = 'ERROR: failed to load batch [config,data]';
					});
			}

			ipService
				.loadConfiguration($scope.$parent.infopage)
				.then(function onSuccess(data) {
					$log.debug('Loaded configuration object in {time} ms, builder = {builder}'.format({
						time   : moment(data['time']).milliseconds(),
						builder: data['builtBy']
					}));
					rawConfig = data.data;
					currentStep += 1;
					loadDataAfterConfig();
				}, function onError() {
					me.error = 'ERROR: failed to load batch [config,data]';
				});
			currentStep += 1;
		};

	app.config(ipCfgRouter)
		.factory('InfoPageAttribute', ['AbstractComponent', defineInfoPageAttributeClass])
		.factory('InfoPagePanel', ['Component', 'InfoPageAttribute', defineInfoPagePanelClass])
		.factory('InfoPageComponent', ['Component', 'InfoPagePanel', defineInfoPageCMPClass])
		.factory('ipService', ['$log', '$http', '$q', ipService])
		.controller('InfoPageController', ['$log', '$scope', 'ipService', 'InfoPageComponent', infoPageController])
		.directive('infopageHeader', ipHeaderDirective)
		.directive('infopageAttribute', ['ipService', infopageAttributeDirective])

}());