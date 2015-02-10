define(
    [
        'lodash',
        './breadcrumb.module',
        // angular dependencies
        'angularUiRouter',
        'common/state/state.module'
    ],
    function breadcrumbService(_, module) {
        "use strict";

        return module.factory('breadcrumbService', ['$rootScope', '$state', '$stateBuilder', service]);

        function service($rootScope, $state, $stateBuilder) {
            var registeredCallbacks = {
                    onBreadcrumbChange: []
                },
                service = {};

            // API
            service.onBreadcrumbChange = onBreadcrumbChange;

            // listeners
            $rootScope.$on('$stateChangeSuccess', onStateChangeSuccess);

            return service;

            function onStateChangeSuccess(event, toState) {
                var newCrumbs = [],
                    stateName = toState.name;

                if (toState.breadcrumb) {
                    _.forEachRight(toState.breadcrumb, function (crumbPath) {
                        newCrumbs.push(newCrumb(crumbPath, toState));
                    });
                } else {

                }

                _.forEachRight(registeredCallbacks.onBreadcrumbChange, function (func) {
                    func(_.clone(newCrumbs, true));
                });
            }

            function newCrumb(stateName, activeState) {
                var state = $state.get(stateName);
                if (!!state) {
                    throw new Error('breadcrumbService :: ' + stateName + ' has no corresponding state defined');
                }
                return {
                    label : $stateBuilder.getStateLabel(state),
                    active: stateName === $stateBuilder.getStateName(activeState)
                };
            }

            function onBreadcrumbChange(funcToCall) {
                registeredCallbacks.onBreadcrumbChange.push(funcToCall);
                return service;
            }
        }
    }
);