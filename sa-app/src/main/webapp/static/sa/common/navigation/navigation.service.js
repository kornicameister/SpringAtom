define(
    [
        'lodash',
        './navigation.module'
    ],
    function (_, module) {
        "use strict";

        return module.service('$navigationService', ['$rootScope', '$q', 'localStorageService', '$stateHelper', $navigationService]);

        function passState(func, event, toState) {
            func(toState);
        }

        function $navigationService($scope, $q, localStorageService, $stateHelper) {
            var self = this,
                callbacks = [],
                listeners = [],
                allStates,
                cache;

            self.onNavigationChange = onNavigationChange;

            listeners.push($scope.$on('$stateChangeSuccess', _.wrap(navigationFromState.bind(self), passState)));
            $scope.$on('$destroy', destroy.bind(self));

            initialize.bind(self)();

            function navigationFromState(to) {
                var navigation,
                    promise = $q(function (resolve) {
                        resolve(to, navigation);
                    });

                var toName = $stateHelper.getStateName(to);
                if (cache[toName]) {
                    navigation = cache[toName];
                } else {
                    var parentStateName = getParentStateName(toName),
                        firstLevelStates = getFirstLevelState(parentStateName);

                    _(firstLevelStates).transform(function (state) {
                        return navigation.push($stateHelper.getStateName(state));
                    });

                    cache[toName] = navigation;
                }

                _.forEachRight(callbacks, function (callback) {
                    callback.call(promise);
                })
            }

            function getFirstLevelState(parentStateName) {
                var stateName;
                return _(allStates).filter(function (state) {
                    stateName = $stateHelper.getStateName(state);
                    return stateName.replace(parentStateName + '.', '').split('.').length === 1;
                })
            }

            function getParentStateName(name) {
                return name.replace(/(\.[^.]*)$/gi, '');
            }

            function onNavigationChange(callback) {
                if (callback) {
                    callbacks.push(callback);
                }
                return self;
            }

            function destroy() {
                _.forEachRight(listeners, function (lst) {
                    lst();
                });
            }

            function initialize() {
                localStorageService.set('sg.$navigationServiceCache', cache = {});
                allStates = $stateHelper.getState();
                $stateHelper.getCurrentState().then(navigationFromState);
            }
        }
    }
);