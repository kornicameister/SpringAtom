(function () {
    angular.module('sg.common.state').provider('$statePageTitle', $statePageTitleProvider);

    function $statePageTitleProvider() {
        // private
        var self = this,
            defaultPageTitle,
            /**
             * <b>springatomPageTitle</b> default expression for page title
             * @private
             * @type {string}
             */
            pageTitleExpression = 'springatomPageTitle',
            pageTitleChange = false;

        // config available
        /**
         * @description
         * Enables/disables [depends on param] updating the page title upon state change
         *
         * @param enable {@boolean} by default enable is true
         * @type {enablePageTitleChange}
         */
        self.enablePageTitleChange = enablePageTitleChange;
        self.setPageTitleExpression = setPageTitleExpression;
        self.setDefaultPageTitle = setDefaultPageTitle;

        // API
        self.$get = function () {
            var self = {};

            self.isPageTitleChangeEnabled = _.constant(pageTitleChange);
            self.getPageTitleExpression = _.constant(pageTitleExpression);
            self.getDefaultPageTitle = _.constant(defaultPageTitle);

            return self;
        };

        function setDefaultPageTitle(title) {
            if (!title) {
                throw new Error('title is undefined, cannot configure');
            }
            defaultPageTitle = title;
            return self;
        }

        function setPageTitleExpression(title) {
            if (!title) {
                throw new Error('title is undefined, cannot configure');
            }
            pageTitleExpression = title;
            pageTitleChange = !!title;
            return self;
        }

        function enablePageTitleChange(enable) {
            pageTitleChange = enable || true;
            return self;
        }
    }
}());