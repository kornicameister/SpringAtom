define(
    [
        'lodash',
        './navigation.module'
    ],
    function (_, module) {
        "use strict";

        return module.service('$navigationService', ['$q', 'localStorageService', '$stateHelper', $navigationService]);

        function passState(func, event, toState) {
            func(toState);
        }

        function $navigationService($q, localStorageService, $stateHelper) {
            var self = this,
                allStates,
                cache;

            self.getNavigation = getNavigationStates;
            self.getSibilingStates = getSibilingStates;

            initialize.bind(self)();
            
            function getSibilingStates(to, level){
                level = level || 1;
                
                var toName = $stateHelper.getStateName(to),
                    parentStateName = getParentStateName(toName),
                    stateName;
                
                return _.filter(allStates, function (state) {
                    if(state.abstract){
                        return false;
                    }
                    stateName = $stateHelper.getStateName(state);
                    return stateName.replace(parentStateName + '.', '').split('.').length === level;
                });
            }

            function getNavigationStates(to) {
                var navigation;

                var toName = $stateHelper.getStateName(to);
                if (cache[toName]) {
                    navigation = cache[toName];
                } else {
                    var firstLevelStates = getSibilingStates(to, 1);
                    cache[toName] = navigation = _.transform(firstLevelStates, function (result, state) {
                        result.push($stateHelper.getStateName(state));
                        return true;
                    });
                }
                
                localStorageService.set('sg.$navigationServiceCache', cache);
                
                return $q(function (resolve) {
                    resolve(navigation);
                });
            }

            function getParentStateName(name) {
                return name.replace(/(\.[^.]*)$/gi, '');
            }

            function initialize() {
                localStorageService.set('sg.$navigationServiceCache', cache = {});
                allStates = $stateHelper.getState();
            }
        }
    }
);