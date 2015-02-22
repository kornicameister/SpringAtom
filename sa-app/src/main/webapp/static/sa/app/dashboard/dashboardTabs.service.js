define(
    [
        'lodash',
        './dashboard.module'
    ],
    function dashboardTabsService(_, module) {
        "use strict";

        return module.service('$dashboardTabService', ['$stateHelper', '$q', $dashboardTabService]);

        /**
         *  <b>$dashboardTabService</b> allows to dynamically resolve following information of the dashboard tabs
         *  - navigator items (i.e. navigation)
         *  - tabs items (i.e. local view navigation)
         *
         * @ngdoc service
         * @name $dashboardTabService
         * @requires sg.state
         *
         * @param $stateHelper
         * @param $q
         */
        function $dashboardTabService($stateHelper, $q) {

            var self = this;

            /**
             * Using dashboard root state retrieves dashboard tabs.
             * Dashboard tabs are dynamically retrieved from all
             * states registered in the application, which allows
             * to plugin any additional ones into the system.
             *
             * @returns promise
             * @type {function(this:service)}
             */
            self.getDashboardTabs = getDashboardTabs.bind(self);
            /**
             * Retrieves navigation items for the navigator components
             *
             * @returns promise
             * @type {function(this:service)}
             */
            self.getDashboardNavigation = getDashboardNavigation.bind(self);

            function getDashboardNavigation() {
                var states = $stateHelper.getState(),
                    rootState = $stateHelper.getState('sg.dashboard').name;

                return $q(function (resolve) {
                    var nav = [],
                        stateName;

                    _.forEachRight(states, function (state) {
                        stateName = $stateHelper.getStateName(state);
                        if (stateName !== rootState && _.startsWith(stateName, rootState)) {
                            nav.push(stateName);
                        }
                    });
                    resolve(nav);
                })
            }

            function getDashboardTabs() {
                return $q(function (resolve, reject) {
                    try {
                        getDashboardNavigation().then(function (states) {
                            var dashboardTabs = [],
                                state;

                            _.forEachRight(states, function (stateName) {
                                state = $stateHelper.getState(stateName);
                                dashboardTabs.push({
                                    state: stateName,
                                    label: $stateHelper.getStateLabel(state),
                                    view : getTabViewName(state)
                                })
                            });

                            var prms = [];
                            _.forEachRight(dashboardTabs, function (nc) {
                                prms.push(nc.label.then(function (label) {
                                    var self = this;
                                    self.label = label;
                                    return self;
                                }.bind(nc)));
                            });

                            $q.all(prms).then(function (tabs) {
                                resolve(_.sortBy(tabs, 'state'));
                            });
                        });

                    } catch (error) {
                        reject(error);
                    }
                });
            }

            function getTabViewName(tabState) {
                var views = tabState.views,
                    viewsKeys = _.keys(views),
                    viewsCounts = viewsKeys.length;
                if (viewsCounts === 0) {
                    throw new Error(tabState.name + ' does not declared any view');
                }
                return viewsKeys[0];
            }
        }
    }
);