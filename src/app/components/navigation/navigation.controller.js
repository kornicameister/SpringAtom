define(
    'app/components/navigation/navigation.controller',
    [
        'lodash',
        'app/components/navigation/navigation.module',
        'common/callbacks'
    ],
    function (_, module, callbacks) {
        "use strict";

        var cancelEvent = callbacks.cancelEvent;

        return module.controller('NavigationController', ['$rootScope', '$stateHelper', '$navigationService', '$q', '$previousState', navigationController]);

        function navigationController($scope, $stateHelper, $navigationService, $q, $previousState) {
            var vm = this,
                listeners = [],
                previousStateMemo = 'nav.prev.state';

            vm.previousState = undefined;
            vm.backToPreviousState = _.wrap(backToPreviousState, cancelEvent);
            vm.navigation = [];

            listeners.push($scope.$on('$stateChangeSuccess', onStateChange.bind(vm)));

            initialize();

            function initialize() {
                $stateHelper.getCurrentState().then(navigationFromState);
            }

            function onStateChange(event, toState, toStateParams, fromState, fromStateParams) {
                navigationFromState(toState);
                rememberPreviousState(fromState, fromStateParams);
            }

            function navigationFromState(state) {
                $navigationService.getNavigation(state).then(function (navigation) {
                    var nav = [];

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
                        vm.navigation = _.sortBy(nav, 'state');
                    });

                });
            }

            function rememberPreviousState(state, params) {
                $previousState.memo(previousStateMemo, state.name);
                $stateHelper.getStateLabel(state).then(function (label) {
                    vm.previousState = {
                        id   : state.name,
                        label: label
                    };
                });
            }

            function backToPreviousState() {
                $previousState.go(previousStateMemo);
                $previousState.forget(previousStateMemo);
            }
        }
    }
);