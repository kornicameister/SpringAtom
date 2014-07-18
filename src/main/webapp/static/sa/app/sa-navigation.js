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

(function SpringAtom_Nav() {
	"use strict";
	var app = angular.module('springatom.nav', ['ui.router']),
		sp;

	function StateRegisterHelper(stateProvider) {
		this.stateProvider = stateProvider;
		this.registerViewState = function (cfg) {
			this.stateProvider.state(cfg.name, cfg.mapping);
		}
	}

	/**
	 * NavigationModel is the class wrapping up actions retrieved from the
	 * server. Contains several method that are able to represent such model
	 * in formats understandable by directives or stateService
	 * @param config configuration object, should contain key {@code key} and {@code model}
	 * @constructor NavigationModel constructor
	 */
	function NavigationModel(config) {
		this.key = config.key;
		this.model = config.model;
	}

	NavigationModel.prototype = (function initNavigationModelPrototype() {
		var keysToDirective = ['id', 'name', 'mode', 'iconClass', 'label'];

		/**
		 * Narrows the navigation [action] model according to the passed {@code keys}
		 * @param model original models [as array]
		 * @param keys keys to narrows {@code model}
		 * @return {Array} array of narrowed models
		 */
		function narrowModel(model, keys) {
			var local = [];
			angular.forEach(model, function toDirectiveFC(value) {
				var localObj = {};
				angular.forEach(keys, function overKeys(kValue) {
					localObj[kValue] = value[kValue];
				});
				local.push(localObj);
			});
			return local;
		}

		return {
			key         : undefined,
			model       : undefined,
			getKey      : function getKey() {
				return this.key;
			},
			setKey      : function setKey(key) {
				this.key = key;
				return key;
			},
			getModel    : function getModel() {
				return this.model;
			},
			setModel    : function setModel(model) {
				this.model = model;
				return model;
			},
			toDirective : function toDirective() {
				return narrowModel(this.model, keysToDirective);
			},
			toViewStates: function toViewStates() {
				var local = [];
				angular.forEach(this.model, function toViewStatesFC(index, value) {

				});
				return local;
			}
		}
	}());

	app.factory('navigationService', ['$http', '$log', '$q', function ($http, $log, $q) {
		var directiveData = {},
			dataPromise = {},
			/**
			 * Points to the controller where <b>main.navigation</b> actions are defined
			 * @type {string}
			 */
			url = '/app/cmp/actions/model/jsp/';

		function loadNavigationModelByKey(key) {
			$log.log('loading navigationModel by key=' + key);
			if (dataPromise[key]) {
				$log.debug('dataPromise[' + key + '] is already promised');
				return dataPromise[key];
			}

			var deferred = $q.defer();
			dataPromise[key] = deferred.promise;

			$log.debug('dataPromise[' + key + '] has been created');

			if (directiveData[key]) {
				deferred.resolve(directiveData[key]);
			} else {
				$log.debug('directiveData[' + key + '] GET call');
				$http.get(url + key)
					.success(function (data) {
						directiveData[key] = new NavigationModel({
							key  : key,
							model: data
						});
						deferred.resolve(directiveData[key]);
					})
					.error(function () {
						deferred.reject('Failed to load data');
					});
			}

			return dataPromise[key];
		}

		return {
			loadNavigation: function (key) {
				$log.log('loading navigation by key=' + key);
				return loadNavigationModelByKey(key);
			}
		}

	}]);

	// configure routing
	app.config(function configureRouter($stateProvider, $urlRouterProvider) {
		sp = new StateRegisterHelper($stateProvider);
		sp.registerViewState({
			name   : 'home',
			mapping: {
				url        : '/sa',
				templateUrl: '/app/home'
			}
		});
		sp.registerViewState({
			name   : 'dashboards',
			mapping: {
				url        : '/sa/dashboard',
				templateUrl: '/app/dashboard'
			}
		});
		sp.registerViewState({
			name   : 'garage',
			mapping: {
				url        : '/sa/garage',
				templateUrl: '/app/garage'
			}
		});
		sp.registerViewState({
			name   : 'admin',
			mapping: {
				url        : '/sa/admin',
				templateUrl: '/app/admin'
			}
		});
		sp.registerViewState({
			name   : 'about',
			mapping: {
				url        : '/sa/about',
				templateUrl: '/app/about'
			}
		});
		sp.registerViewState({
			name   : 'login',
			mapping: {
				url        : '/sa/login',
				templateUrl: '/app/auth/login'
			}
		});
		$urlRouterProvider.otherwise('/sa');
	});

	// navigator directive
	app.directive('navigator', ['$log', 'navigationService', function navigatorDirective($log, navigationService) {
		$log.log('navigator-directive in progress...');

		function computeTemplate() {
			var array = [];
			array.push('<ul class="nav nav-pills nav-stacked">');
			array.push('<li ng-repeat="action in data">');
			array.push('<a id="{{action.id}}" ui-sref="{{action.name}}" role="{{action.mode}}">');
			array.push('<i ng-show="action.iconClass" ng-class="action.iconClass"></i>');
			array.push('{{action.label}}');
			array.push('</a></li></ul>');
			return array.join(' ');
		}

		function getTemplate() {
			return '<div><span ng-hide="data">Loading...</span>' +
			'<div ng-show="data">' + computeTemplate() + '</div>' +
			'</div>';
		}

		return {
			restrict: 'E',
			template: getTemplate(),
			link    : function (scope) {
				navigationService
					.loadNavigation('main.navigation')
					.then(function onSuccess(data) {
						scope.data = data.toDirective();
					}, function onError() {
						scope.data = 'ERROR: failed to load data';
					})
			}
		}
	}]);

}());