angular.module('sg.app.components.actionBar').directive('actionBar', function () {
    return {
        restrict   : 'E',
        transclude : true,
        templateUrl: 'app/components/action-bar/action-bar.tpl.html'
    }
});