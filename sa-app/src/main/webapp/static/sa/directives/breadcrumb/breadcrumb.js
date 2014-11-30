/**
 * Created by trebskit on 2014-11-12.
 */
define(
    [
        // angular deps
    ],
    function breadcrumbDirective() {
        function getDummyCrumbs() {
            return [{
                label : 'Home',
                active: true
            }];
        }

        return {
            name      : 'breadcrumb',
            definition: function ($log) {
                $log.log('breadcrumb-directive in progress...');
                return {
                    restrict   : 'A',
                    controller : function ($scope) {
                        $scope.crumbs = getDummyCrumbs();
                    },
                    templateUrl: '/static/sa/directives/breadcrumb/breadcrumb.html'
                }
            }
        }
    }
);