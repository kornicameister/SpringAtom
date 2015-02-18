define(
    [
        'lodash',
        './navigation.module',
        '../../callbacks'
    ],
    function (_, module, callbacks) {
        "use strict";

        var cancelEvent = callbacks.cancelEvent;

        return module.controller('NavigationController', ['$rootScope', '$navigation', '$stateHelper', '$q', '$previousState', '$translate', navigationController]);

        function navigationController($scope, $navigation, $stateHelper, $q, $previousState, $translate) {
            var vm = this,
                previousStateMemo = 'nav.prev.state';

            vm.previousState = undefined;
            vm.backToPreviousState = _.wrap(backToPreviousState, cancelEvent);
            vm.navigation = [];

            $scope.$on('$stateChangeSuccess', onStateChangeSuccess);

            initialize();

            function initialize() {
                return $stateHelper.getCurrentState().then(navigationFromState);
            }

            function navigationFromState(state) {
                var navigation = $navigation.getNavigation(state),
                    nav = [];

                _.forEach(navigation, function (n) {
                    n = $stateHelper.getState(n);
                    if (n) {
                        nav.push({
                            id    : _.uniqueId('nav_'),
                            state : n.name,
                            type  : 'link',
                            label : $stateHelper.getStateLabel(n),
                            active: n.name === state.name ? 'yes' : 'no'
                        });
                    }
                });

                var prms = [];
                _.forEach(nav, function (nc) {
                    prms.push(nc.label.then(function (label) {
                        var self = this;
                        self.label = label;
                        return self;
                    }.bind(nc)));
                });

                $q.all(prms).then(function (nav) {
                    vm.navigation = nav;
                });
            }

            function rememberPreviousState(state, fromStateParams) {
                $previousState.memo(previousStateMemo, state.name, fromStateParams);
                $stateHelper.getStateLabel(state).then(function (label) {
                    vm.previousState = {
                        id   : state.name,
                        label: label
                    }
                })
            }

            function backToPreviousState() {
                $previousState.go(previousStateMemo);
                $previousState.forget(previousStateMemo);
            }

            function onStateChangeSuccess(event, state, stateParams, fromState, fromStateParams) {
                "use strict";
                navigationFromState(state);
                rememberPreviousState(fromState, fromStateParams);
            }
        }
    }
);