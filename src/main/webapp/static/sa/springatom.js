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

(function SpringAtom() {
	console.log('SpringAtom - CSL');
	"use strict";
	var app = angular.module('springatom', []);

	// navigator directive
	app.directive('navigator', ['$log', '$http', '$q', function ($log, $http, $q) {
		// perform ajax call to get top navigation
		$log.log('navigator-directive in progress...');

		var directiveData,
			dataPromise;

		function loadNavigation() {
			if (dataPromise) {
				return dataPromise;
			}

			var deferred = $q.defer();
			dataPromise = deferred.promise;

			if (directiveData) {
				deferred.resolve(directiveData);
			} else {
				$http.get('/app/cmp/actions/model/jsp/main.navigation')
					.success(function (data) {
						directiveData = data;
						deferred.resolve(directiveData);
					})
					.error(function () {
						deferred.reject('Failed to load data');
					});
			}
			return dataPromise;
		}

		function computeTemplate() {
			var array = [];
			array.push('<ul class="nav nav-pills nav-stacked">');
			array.push('<li ng-repeat="action in data">');
			array.push('<a id="{{action.id}}" href="{{action.url}}" role="{{action.mode}}">');
			array.push('<i ng-show="action.iconClass" ng-class="action.iconClass"></i>');
			array.push('{{action.label}}');
			array.push('</a></li></ul>');
			return array.join(' ');
		}

		return {
			restrict: 'E',
			template: '<div>' +
			'<span ng-hide="data">Loading...</span>' +
			'<div ng-show="data">' + computeTemplate() + '</div>' +
			'</div>',
			link    : function (scope, elem, attr) {
				loadNavigation().then(function (data) {
					scope.data = data;
				}, function () {
					scope.data = 'ERROR: failed to load data.';
				})
			}
		}
	}]);

}());