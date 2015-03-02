define(
    [
        'angular',
        'lodash'
    ],
    function stateBuilderProvider(angular, _) {
        "use strict";
        return ['$stateProvider', '$urlRouterProvider', function ($stateProvider,
                                                                  $urlRouterProvider) {
            var provider = this,
                stateLabelCache;

            provider.state = saveState;
            provider.$get = ['$stateLabelResolve', '$cacheFactory', '$state', '$q', providerFactoryFn];

            function saveState(state) {

                validateStateDefinition(state);

                var children = state.children || [];

                // copy into the definition
                state.name = resolveName(state);
                state.hasChildren = children.length > 0;

                delete state.children;

                $stateProvider.state(state);

                checkPrimaryState(state);

                if (children.length) {
                    _.forEachRight(children, function (child) {
                        child.parent = state;
                        provider.state(child);
                    });
                }

                return provider;
            }

            function providerFactoryFn($stateLabelResolve, $cacheFactory, $state, $q) {

                if (_.isUndefined(stateLabelCache)) {
                    stateLabelCache = $cacheFactory('stateBuilderProvider.stateLabelCache');
                }

                var service = {
                    cacheStateLabel: cacheStateLabel,
                    isLabelResolved: isLabelResolved,
                    getStateLabel  : getStateLabel,
                    getStateName   : getStateName,
                    getParentState : getParentState,
                    hasParentState : hasParentState,
                    getCurrentState: getCurrentState,
                    getState       : $state.get
                };

                return service;

                function isPromise(val) {
                    return !!val.then;
                }

                function asPromise(val) {
                    if (isPromise(val)) {
                        return val;
                    }
                    return $q(function (resolve) {
                        resolve(val);
                    })
                }

                function getCurrentState() {
                    return $q(function (resolve) {
                        resolve(_.clone($state.current));
                    });
                }

                function cacheStateLabel(state, label) {
                    if (_.isUndefined(label)) {
                        return false;
                    }
                    var inCache = service.isLabelResolved(state);

                    if (!inCache) {
                        stateLabelCache.put(resolveName(state), label);
                    }

                    return !inCache;
                }

                function isLabelResolved(state) {
                    validateStateDefinition(state);
                    return !!stateLabelCache.get(resolveName(state));
                }

                function getStateLabel(state) {

                    function _getStateLabel(state) {
                        return $q(function (resolve) {
                            var name = resolveName(state);
                            if (!service.isLabelResolved(state)) {
                                asPromise($stateLabelResolve.resolveLabel(state)).then(
                                    function (label) {
                                        service.cacheStateLabel(state, label);
                                        resolve(label);
                                    }
                                )
                            } else {
                                resolve(stateLabelCache.get(name));
                            }
                        })
                    }

                    if (!state) {
                        return getCurrentState().then(_getStateLabel)
                    }

                    return _getStateLabel(state);
                }

                function getStateName(state) {
                    return resolveName(state);
                }

                function getParentState(state) {
                    return state.parent;
                }

                function hasParentState(state) {
                    return !!service.getParentState(state);
                }

            }

            function resolveName(state) {
                if (state.parent) {
                    return (angular.isObject(state.parent) ? state.parent.name : state.parent) + '.' + state.name;
                }
                return state.name;
            }

            function validateStateDefinition(state) {
                if (!state) {
                    throw new Error('state is undefined, cannot configure');
                }
                if (!state.hasOwnProperty('name') || _.isUndefined(state.name)) {
                    throw new Error('state.name is missing, cannot configure');
                }
            }

            function savePrimaryState(state) {
                var stateName = resolveName(state);
                return function ($injector) {
                    var $state = $injector.get('$state');
                    state = $state.get(stateName).$$state();
                    return state.url.sourcePath;
                }
            }

            function checkPrimaryState(state) {
                if (state.primary && state.primary === true) {
                    $urlRouterProvider.otherwise(savePrimaryState(state));
                    checkPrimaryState = function () {
                    }
                }
            }
        }];
    }
);