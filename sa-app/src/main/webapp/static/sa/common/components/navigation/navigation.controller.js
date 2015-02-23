define(
    [
        'lodash',
        './navigation.module',
        '../../callbacks'
    ],
    function (_, module, callbacks) {
        "use strict";

        var cancelEvent = callbacks.cancelEvent;

        return module.controller('NavigationController', ['$stateHelper', '$navigationService', '$q', '$previousState', navigationController]);

        function navigationController($stateHelper, $navigationService, $q, $previousState) {
            var vm = this,
                previousStateMemo = 'nav.prev.state';

            vm.previousState = undefined;
            vm.backToPreviousState = _.wrap(backToPreviousState, cancelEvent);
            vm.navigation = [];

            initialize();

            function initialize() {
                $navigationService.onNavigationChange(function (state, navigation) {
                    navigationFromState(state, navigation);
                    rememberPreviousState(state);
                });
            }

            function navigationFromState(state, navigation) {
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
                    vm.navigation = nav;
                });
            }

            function rememberPreviousState(state) {
                $previousState.memo(previousStateMemo, state.name);
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
        }
    }
);