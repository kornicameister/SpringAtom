define(
    [
        'lodash',
        './navigation.module'
    ],
    function (_, module) {
        "use strict";
        return module.controller('NavigationController', ['$rootScope', '$navigation', '$stateBuilder', '$q', navigationController]);

        function navigationController($scope, $navigation, $stateBuilder, $q) {
            var vm = this;

            vm.navigation = [];

            $scope.$on('$stateChangeSuccess', onStateChangeSuccess);

            initialize();

            function initialize() {
                return $stateBuilder.getCurrentState().then(navigationFromState);
            }

            function navigationFromState(state) {

                vm.navigation = [];

                var navigation = $navigation.getNavigation(state),
                    nav = [];

                navigation.unshift(state.name);

                _.forEach(navigation, function (n) {
                    n = $stateBuilder.getState(n);
                    if (n) {
                        nav.push({
                            id    : _.uniqueId('nav_'),
                            state : n.name,
                            type  : 'link',
                            label : $stateBuilder.getStateLabel(n),
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

            function onStateChangeSuccess(event, state) {
                navigationFromState(state);
            }
        }
    }
);