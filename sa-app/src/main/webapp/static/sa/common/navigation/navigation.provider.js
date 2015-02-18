define(
    [
        'jsface',
        'lodash',
        './navigation.module'
    ],
    function navigationProvider(jsface, _, module) {
        "use strict";

        var NavigationMap = Class(Object.prototype, {
                $singleton: true,
                map       : {},
                push      : function (state, navs) {
                    if (!navs) {
                        return;
                    }
                    NavigationMap.map[state] = navs;
                },
                pull      : function (state) {
                    return NavigationMap.map[state] || []
                }
            }
        );

        return module.provider('$navigation', [provider]);

        function provider() {
            var self = this,
                navigationMap = NavigationMap;

            self.navigation = appendNavigation;

            self.$get = [function () {
                var service = {};

                service.getNavigation = getNavigation;
                service.getCount = _.constant(_.keys(navigationMap.map).length);

                return service;

                function getNavigation(state) {
                    return _.clone(navigationMap.pull(toStateName(state)));
                }
            }];

            function toStateName(state) {
                if (_.isObject(state)) {
                    return state.name;
                }
                return state;
            }

            function appendNavigation(from, navs) {
                navigationMap.push(toStateName(from), navs);
            }
        }
    }
);