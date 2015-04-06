angular.module('sg.app.components.actionBar').directive('actionItem', function () {

    var baseUrl = 'app/components/action-bar/item/action-item-{type}.tpl.html';

    return {
        restrict   : 'E',
        scope      : {
            type  : '@',
            action: '&'
        },
        controller : 'ActionItemController',
        templateUrl: getTemplateUrl
    };

    //noinspection JSUnusedLocalSymbols
    function getTemplateUrl(el, attrs) {
        var type = attrs.type;
        return _.format(baseUrl, {type: type})
    }
});