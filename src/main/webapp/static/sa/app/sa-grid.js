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
 * Created by kornicameister on 22.07.14.
 */
(function SpringAtom_Grids() {

	"use strict";

	var moduleName = 'springatom.grids',
		app = angular.module(moduleName, []),
		skipRowNumberer = true,
		gridConfig = function gridConfigClass() {
			return Class(function GridConfiguration() {

				var getUrl = function _getUrl(which) {
					var url = undefined;
					angular.forEach(this.urls, function (uri) {
						if (uri.rel === which && !angular.isDefined(url)) {
							url = uri.href;
						}
					});
					return url;
				};

				return {
					constructor        : function (cfg) {
						this.id = cfg.id;
						this.urls = cfg.urls;
						this.context = cfg.context;
						this.label = cfg.label;
					},
					getId              : function () {
						return this.id;
					},
					getLabel           : function () {
						return this.label;
					},
					getContext         : function () {
						return this.context;
					},
					hasContext         : function () {
						return angular.isDefined(this.context);
					},
					getConfigurationUrl: function () {
						return getUrl.call(this, 'configuration');
					},
					getDataUrl         : function () {
						return getUrl.call(this, 'data');
					}
				}
			});
		},
		gridPageHelper = function gridPageHelper() {
			/**
			 * defaultPageSize is 25
			 * @private
			 * @type {number}
			 */
			var defaultPageSize = 25;

			/**
			 * Returns the sizes of the pages.
			 * Ideally it should be server configured for the first call and then stored locally in the script
			 * @private
			 */
			function _getPageSizes() {
				var step = 5,
					arr = [],
					it = 1;
				while (it !== step) {
					arr.push(it * defaultPageSize);
					it += 1;
				}
				return arr;
			}

			return  {
				getPageSizes      : _getPageSizes,
				getDefaultPageSize: function () {
					return defaultPageSize
				},
				getInitialPage    : function () {
					return {
						page : 1,
						limit: defaultPageSize
					}
				}
			}
		},
		gridCfgHelper = function gridConfigurationHelper($http, $q, $log) {
			$log.debug('gridConfigurationHelper initializing');
			var priv = {
					/**
					 * Creates HTTP configuration to be used by angular to bootstrap loading configuration object
					 * @param cfg must contain url property
					 * @returns {{method: string, url: string, cache: boolean}}
					 * @private
					 */
					getHttpConfiguration: function _getHttpConfiguration(cfg) {
						return {
							method: 'GET',
							url   : priv.getUrl(cfg),
							cache : true
						}
					},
					/**
					 * Extracts the {@config url} out of cfg. Checks if defined and throws error if not
					 * @param cfg configuration to get url from
					 * @returns {string}
					 * @private
					 */
					getUrl              : function _getUrl(cfg) {
						var url = cfg.url;
						if (!url) {
							throw new Error('cfg.url is not defined');
						}
						$log.debug('gridConfigurationHelper :: url={url}'.format({url: url}));
						return url;
					},
					loadConfiguration   : function _loadConfiguration(cfg) {
						var whatData,
							whatDataPromise,
							httpConf = priv.getHttpConfiguration(cfg);

						if (whatDataPromise) {
							return whatDataPromise;
						}

						var deferred = $q.defer();
						whatDataPromise = deferred.promise;

						if (whatData) {
							deferred.resolve(whatData);
						} else {
							$http(httpConf)
								.success(function (data) {
									$log.debug('gridConfigurationHelper :: Loaded configuration of the grid');
									whatData = data;
									deferred.resolve(whatData);
								})
								.error(function () {
									deferred.reject('Unable to load data');
								});
						}

						return whatDataPromise;
					}
				},
				unpacks = {
					unpackSingleColumn : function _unpackSingleColumn(col) {
						if (!col) {
							throw new Error('col not defined, cannot unpack')
						} else if (col['xtype'] === 'rownumberer' && skipRowNumberer) {
							return undefined;
						}
						$log.debug('unpackSingleColumn :: unpacking column [id={id}]'.format({id: col['dataIndex']}));
						return {
							displayName: col['text'] || col['label'],
							field      : col['dataIndex'],
							sortable   : col['sortable'],
							fieldType  : col['fieldType'] || col['displayAs'],
							visible    : col['visible'] || true,
							groupable  : col['groupable'] || true,
							pinnable   : col['pinnable'] || true
						}
					},
					unpackColumns      : function _unpackColumns(rawColumns, editable) {
						if (!rawColumns) {
							throw new Error('rawColumns not defined, cannot unpack')
						}
						$log.debug('unpackColumns :: unpacking {size} columns'.format({size: rawColumns.length}));
						var columns = [],
							sTime = window['performance']['now']();

						angular.forEach(rawColumns, function (col) {
							var singleColumn;

							function canOverrideCellEditable() {
								return angular.isDefined(editable) && (!angular.isDefined(singleColumn['enableCellEdit']) && editable !== singleColumn['enableCellEdit']);
							}

							if (angular.isDefined((singleColumn = unpacks.unpackSingleColumn(col)))) {
								if (canOverrideCellEditable()) {
									singleColumn['enableCellEdit'] = editable;
								}
								columns.push(singleColumn);
							}
						});

						$log.debug('unpackColumns :: completed in {time} ms'.format({time: (window['performance']['now']() - sTime)}));

						return columns;
					},
					unpackFeature      : function _unpackFeature(features, ftype) {
						if (!features || !ftype) {
							return false;
						} else if (!angular.isArray(features)) {
							return false;
						}
						var localFtype = ftype.toLowerCase(),
							found = undefined;
						angular.forEach(features, function _check(feature) {
							if (feature['ftype'] == localFtype && !angular.isDefined(found)) {
								found = 1;
							}
						});
						return angular.isDefined(found);
					},
					unpackConfiguration: function _unpackConfiguration(data) {
						$log.debug('gridConfigurationHelper :: Unpacking configuration');
						$log.debug('gridConfigurationHelper :: Loaded configuration object in {time} ms, builder = {builder}'.format({
							time   : moment(data['time']).milliseconds(),
							builder: data['builtBy']
						}));

						var confBean = data.data,
							features = confBean['features'],
							sTime = window['performance']['now']();

						if (!angular.isDefined(confBean)) {
							throw new Error('Invalid confBean of the grid');
						}

						var gridOptions = {
							collapsible        : confBean['collapsible'] || false,
							border             : confBean['border'] || false,
							enableSorting      : confBean['sortableColumns'] || false,
							tableId            : confBean['tableId'],
							builderId          : confBean['builderId'],
							enableCellEdit     : unpacks.unpackFeature(features, 'editable'),
							enablePinning      : unpacks.unpackFeature(features, 'pinning'),
							enablePaging       : unpacks.unpackFeature(features, 'paging'),
							enableRowReordering: unpacks.unpackFeature(features, 'rowReordering'),
							showFooter         : unpacks.unpackFeature(features, 'showFooter'),
							showFilter         : unpacks.unpackFeature(features, 'showFilter'),
							showColumnMenu     : unpacks.unpackFeature(features, 'showColumnMenu'),
							columns            : unpacks.unpackColumns.call(this, confBean.content, unpacks.unpackFeature(features, 'editable'))
						};

						$log.debug('unpackConfiguration :: completed in {time} ms'.format({time: (window['performance']['now']() - sTime)}));

						return gridOptions;
					}
				};
			return {
				loadConfiguration  : priv.loadConfiguration,
				unpackConfiguration: unpacks.unpackConfiguration
			}
		},
		gridDataHelper = function gridDataHelper($http, $q, $log, gridPageHelper) {
			var priv = {
					getHttpConfiguration: function _createHTTPConfiguration(conf) {
						var searchText = conf['request']['searchText'] || '',
							page = conf['request']['page'] || 1,
							pageSize = conf['request']['limit'] || gridPageHelper.getDefaultPageSize(),
							url = conf['url'];
						if (!url || url === '') {
							throw new Error('URL is not defined, can not proceed');
						}
						var retConf = {
							method: 'POST',
							cache : false,
							url   : url,
							params: {
								start: page === 0 ? 0 : (page * pageSize),
								page : page,
								limit: pageSize
							}
						};
						delete conf['request']['page'];
						delete conf['request']['limit'];
						delete conf['request']['searchText'];
						delete conf['url'];

						angular.extend(retConf.params, conf.request);

						return retConf
					},
					/**
					 * Loads data for the grid
					 * @param conf configuration of the object. Must at least contain;
					 * <ul>
					 *     <li>url</li>
					 *     <li>page: size, index</li>
					 *     <li>searchText</li>
					 * </ul>
					 * @private
					 */
					loadData            : function _loadData(conf) {
						var whatData,
							whatDataPromise,
							httpConf = priv.getHttpConfiguration(conf);

						if (whatDataPromise) {
							return whatDataPromise;
						}

						var deferred = $q.defer();
						whatDataPromise = deferred.promise;

						if (whatData) {
							deferred.resolve(whatData);
						} else {
							$http(httpConf)
								.success(function (data) {
									$log.debug('gridDataHelper :: Loaded data of the grid');
									whatData = data;
									deferred.resolve(whatData);
								})
								.error(function () {
									deferred.reject('Unable to load data');
								});
						}

						return whatDataPromise;
					}
				},
				unpacks = {
					unpackChunk      : function _unpackChunk(chunk) {
						if (!chunk) {
							$log.warn('unpackChunk :: cannot unpack, chunk not defined');
						}
						var localChunk = {};
						angular.forEach(chunk, function (value, key) {
							localChunk[key] = value['value'];
						});
						return localChunk;
					},
					unpackDataPackage: function _unpackDataPackage(data) {
						if (!data) {
							throw new Error('data not defined, cannot unpack')
						}
						$log.debug('unpackDataPackage :: unpacking {size} data objects'.format({size: data.length}));
						var localData = [],
							sTime = window['performance']['now']();

						angular.forEach(data, function (chunk) {
							var singleColumn;
							if (angular.isDefined((singleColumn = unpacks.unpackChunk(chunk)))) {
								localData.push(singleColumn);
							}
						});

						$log.debug('unpackDataPackage :: completed in {time} ms'.format({time: (window['performance']['now']() - sTime)}));

						return localData;
					},
					unpackData       : function _unpackData(data) {
						$log.debug('gridDataHelper :: Unpacking data');
						$log.debug('gridDataHelper :: Loaded configuration object in {time} ms, builder = {builder}'.format({
							time   : moment(data['time']).milliseconds(),
							builder: data['builtBy']
						}));

						var dataBean = data.data,
							sTime = window['performance']['now']();

						if (!angular.isDefined(dataBean)) {
							throw new Error('Invalid dataBean of the grid');
						}

						var localData = {
							data: unpacks.unpackDataPackage(dataBean)
						};

						$log.debug('unpackData :: completed in {time} ms'.format({time: (window['performance']['now']() - sTime)}));

						return localData;
					}
				};

			return {
				loadData  : priv.loadData,
				unpackData: unpacks.unpackData
			}
		},
		gridService = function gridService(dataHelper, configHelper, pageHelper) {
			return {
				loadConfiguration : function gs_loadConfiguration(cfg) {
					var successCallback = cfg.success,
						failureCallback = cfg.failure,
						localSuccess = function (data) {
							successCallback.apply(this, [configHelper.unpackConfiguration(data)]);
						},
						localFailure = function (data) {
							failureCallback.apply(this, [data]);
						};

					// clear up
					delete cfg.success;
					delete cfg.failure;

					configHelper.loadConfiguration(cfg).then(localSuccess, localFailure);
				},
				loadData          : function gs_loadData(cfg) {
					var successCallback = cfg.success,
						failureCallback = cfg.failure,
						localSuccess = function (data) {
							successCallback.apply(this, [dataHelper.unpackData(data)]);
						},
						localFailure = function (data) {
							failureCallback.apply(this, [data]);
						};

					// clear up
					delete cfg.success;
					delete cfg.failure;

					dataHelper.loadData(cfg).then(localSuccess, localFailure);
				},
				getPageSizes      : function delegate_getPageSizes() {
					return pageHelper.getPageSizes();
				},
				getDefaultPageSize: function delegate_getDefaultPageSize() {
					return pageHelper.getDefaultPageSize();
				},
				getInitialPage    : function delegate_getInitialPage() {
					return pageHelper.getInitialPage();
				}
			}
		},
		gridDirective = function gridDirective($log, gridService, GridConfiguration) {

			var defaultUnpack = function _defaultUnpack(conf) {
					if (!conf) {
						return undefined;
					}
					return new GridConfiguration({
						id     : conf['builderId'],
						label  : conf['label'],
						urls   : conf['urls'],
						context: conf['value']
					});
				},
				linkGridDirective = function _linkGridDirective(scope) {
					var currentStep = 0,
						stepsTotalCount = 4,
						origin = scope.origin,
						conf = scope.conf,
						unpackedConfiguration,
						configReady,
						collectAttributesFromConf = function _collectAttributesFromConf() {
							var localArray = [];
							angular.forEach(scope.grid.gridConfig.columns, function (column) {
								localArray.push({
									path: column['field'],
									type: column['fieldType']
								});
							});
							return {
								attributes: localArray
							}
						},
						pushLoadCfg = function _pushLoadCfg() {
							gridService.loadConfiguration({
								url    : unpackedConfiguration.getConfigurationUrl(),
								success: function success(data) {
									// safe at this point, in data we must assign data directly
									scope.grid.gridConfig.columns = data.columns;
									delete data.columns;
									angular.extend(scope.grid.gridOptions, data);
									// safe at this point, in data we must assign data directly
									configReady = true;
									currentStep += 1;
								},
								failure: function failure(data) {
									scope.error = data;
								}
							});
						},
						bootstrapLoadingData = function bootstrapLoadingData() {
							if (configReady) {
								pushLoadData();
							} else {
								$log.debug('Config not loaded yet, waiting another 100ms');
								setTimeout(bootstrapLoadingData, 100);
							}
						},
						pushLoadData = function _pushLoadData() {
							var cfg = {
								url    : unpackedConfiguration.getDataUrl(),
								success: function success(data, page) {
									scope.grid.gridConfig.data = data.data;
									currentStep += 1;
								},
								failure: function failure(data) {
									scope.error = data;
								}
							};
							// empty wrapping for request
							cfg.request = {};
							angular.extend(cfg.request, unpackedConfiguration.getContext());
							angular.extend(cfg.request, gridService.getInitialPage());
							angular.extend(cfg.request, collectAttributesFromConf());
							gridService.loadData(cfg);
						},
						combineAndFinish = function _combineAndFinish() {
							if (scope.grid.gridConfig.columns && scope.grid.gridConfig.data) {
								$log.log('Configuration and data loaded, combining');
								currentStep += 1;
								scope.$apply();
							} else {
								$log.debug('Config/Data not loaded yet, waiting another 100ms');
								setTimeout(combineAndFinish, 100);
							}
						};

					if (!origin) {
						$log.debug('No origin set, using grid');
						origin = 'grid';
					}
					if (origin === 'infopage') {
						unpackedConfiguration = defaultUnpack(conf);
						$log.debug('Configuration unpacked, grid(id=', unpackedConfiguration.getId(), ')');
						currentStep += 1;
					}
					if (!unpackedConfiguration) {
						throw new Error('No available GridConfiguration');
					}

					scope.grid = {
						id                : unpackedConfiguration.getId(),
						title             : unpackedConfiguration.getLabel(),
						isLoading         : function () {
							return currentStep !== stepsTotalCount;
						},
						getLoadingProgress: function () {
							var humanized = arguments.length == 1,
								progress = currentStep / stepsTotalCount;
							return humanized ? (progress * 100.0) : progress;
						},
						gridConfig        : {
							data   : [],
							columns: []
						},
						gridOptions       : {
							data          : 'grid.gridConfig.data',
							columnDefs    : 'grid.gridConfig.columns',
							enablePinning : 'grid.gridConfig.enablePinning',
							enablePaging  : 'grid.gridConfig.enablePaging',
							showFooter    : 'grid.gridConfig.showFooter',
							showFilter    : 'grid.gridConfig.showFilter',
							showColumnMenu: 'grid.gridConfig.showColumnMenu',
							pagingOptions : {
								pageSizes: gridService.getPageSizes(),
								pageSize : gridService.getDefaultPageSize()
							}
						}
					};

					setTimeout(pushLoadCfg, 2);
					setTimeout(bootstrapLoadingData, 3);
					setTimeout(combineAndFinish, 5);
				};

			return {
				restrict   : 'E',
				scope      : {
					/**
					 * Configuration for the grid.
					 * Must contains conf and data urls
					 */
					conf  : '=config',
					origin: '=origin',
					/**
					 * Function that unpacks attributes
					 */
					unpack: '&unpack'
				},
				templateUrl: '/app/cmp/grid/generic',
				link       : linkGridDirective
			}
		};

	app.factory('GridConfiguration', gridConfig)
		.factory('gridPageHelper', gridPageHelper)
		.factory('gridConfigurationHelper', ['$http', '$q', '$log', gridCfgHelper])
		.factory('gridDataHelper', ['$http', '$q', '$log', 'gridPageHelper', gridDataHelper])
		.factory('gridService', [ 'gridDataHelper', 'gridConfigurationHelper', 'gridPageHelper', gridService])
		.directive('dynamicGrid', ['$log', 'gridService', 'GridConfiguration', gridDirective]);

}());