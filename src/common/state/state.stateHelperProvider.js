(function () {
    angular.module('sg.common.state').provider('$stateHelper', ['$stateProvider', $stateHelperProvider]);

    function $stateHelperProvider($stateProvider) {
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
                if (!_.isUndefined(val) && isPromise(val)) {
                    return val;
                }
                var defered = $q.defer();
                defered.resolve(val);
                return defered.promise;
            }

            function getCurrentState() {
                var defered = $q.defer();
                defered.resolve(_.clone($state.current));
                return defered.promise;
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
                if (!state || state.abstract) {
                    return asPromise(state);
                }

                function _getStateLabel(state) {
                    var defer = $q.defer();

                    var name = resolveName(state);
                    if (!service.isLabelResolved(state)) {
                        asPromise($stateLabelResolve.resolveLabel(state)).then(
                            function (label) {
                                service.cacheStateLabel(state, label);
                                defer.resolve(label);
                            }
                        )
                    } else {
                        defer.resolve(stateLabelCache.get(name));
                    }

                    return defer.promise;
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
    }
}());