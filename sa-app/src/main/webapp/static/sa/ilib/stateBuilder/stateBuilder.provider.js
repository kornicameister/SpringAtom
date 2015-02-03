define(
    [
        'angular',
        'lodash'
    ],
    function stateBuilderProvider(angular, _) {
        "use strict";
        return ['$stateProvider', function ($stateProvider) {
            // private
            var provider = this,
                stateLabelCache,
                resolveName = function resolveName(state) {
                    if (state.parent) {
                        return (angular.isObject(state.parent) ? state.parent.name : state.parent) + '.' + state.name;
                    }
                    return state.name;
                },
                validateStateDefinition = function (state) {
                    if (!state) {
                        throw new Error('state is undefined, cannot configure');
                    }
                    if (!state.hasOwnProperty('name') || _.isUndefined(state.name)) {
                        throw new Error('state.name is missing, cannot configure');
                    }
                };

            // config available
            provider.state = function (state) {

                validateStateDefinition(state);

                var children = state.children || [];

                // copy into the definition
                state.name = resolveName(state);
                state.hasChildren = children.length > 0;

                delete state.children;

                $stateProvider.state(state);

                if (children.length) {
                    _.forEachRight(children, function (child) {
                        child.parent = state;
                        provider.state(child);
                    });
                }

                return provider;
            };

            // services, controllers, run available
            provider.$get = ['$stateLabelResolve', '$cacheFactory',
                function ($stateLabelResolve, $cacheFactory) {
                    if (_.isUndefined(stateLabelCache)) {
                        stateLabelCache = $cacheFactory('stateBuilderProvider.stateLabelCache');
                    }

                    var service = {
                        cacheStateLabel: cacheStateLabel,
                        isLabelResolved: isLabelResolved,
                        getStateLabel  : getStateLabel,
                        getStateName   : getStateName,
                        getParentState : getParentState,
                        hasParentState : hasParentState
                    };

                    return service;

                    function cacheStateLabel(state, label) {
                        if (_.isUndefined(label)) {
                            return false;
                        }
                        var inCache = service.isLabelResolved(state);

                        if (!inCache) {
                            stateLabelCache.put(resolveName(state, label));
                        }

                        return !inCache;
                    }

                    function isLabelResolved(state) {
                        validateStateDefinition(state);
                        return !!stateLabelCache.get(resolveName(state));
                    }

                    function getStateLabel(state) {
                        var name = resolveName(state),
                            label;
                        if (!service.isLabelResolved(state)) {
                            label = $stateLabelResolve.resolveLabel(state);
                            service.cacheStateLabel(state, label);
                        } else {
                            label = stateLabelCache.get(name);
                        }
                        return label;
                    }

                    function getStateName(state) {
                        return state.name;
                    }

                    function getParentState(state) {
                        return state.parent;
                    }

                    function hasParentState(state) {
                        return !_.isUndefined(service.getParentState(state));
                    }

                }
            ];
        }];

    }
);