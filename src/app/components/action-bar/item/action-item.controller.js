angular.module('sg.app.components.actionBar')
    .controller('ActionItemController', function ($scope,
                                                  $attrs,
                                                  $translate,
                                                  loggerFactory) {

        var type = $scope.type,
            action = $scope.action,
            logger = loggerFactory(_.format('ActionItemController[{type}]', {type: type}));

        if (!type) {
            throw new Error('Type is not defined, cannot execute ActionItemController');
        }

        $scope.onItemAction = initializeVarsAndPickFunction();

        function initializeVarsAndPickFunction() {
            logger.debug('Initializing controller for type ' + type);
            switch (type) {
                case 'search':
                    return initSearchType();
                case 'button':
                    return initButtonType();
                case 'separator':
                    return angular.noop;
            }
        }

        function initButtonType() {
            $translate($attrs.label).then(function (text) {
                logger.debug('Retrieved button label = ' + text);
                $scope.buttonLabel = text;
            });
            return function aicButtonAction() {
                action()
            }
        }

        function initSearchType() {
            $scope.searchPhrase = undefined;
            return function aicSearchAction() {
                var searchPhrase = $scope.searchPhrase;
                if (!searchPhrase || searchPhrase.length === 0) {
                    logger.warn('No search phrase was specified');
                    return false;
                }
                action({text: searchPhrase});
            };
        }


    });