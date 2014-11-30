/**
 * Created by trebskit on 2014-11-12.
 */
define(
    [
        'directives/header/controller'
    ],
    function headerDirective(headerController) {
        return {
            name      : 'topHeader',
            definition: function ($log) {
                $log.log('topHeader-directive in progress...');
                return {
                    restrict   : 'EA',
                    transclude : true,
                    controller : headerController,
                    templateUrl: '/static/sa/directives/header/view.html'
                }
            }
        };
    }
);