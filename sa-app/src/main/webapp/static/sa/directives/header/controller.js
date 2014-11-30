/**
 * Created by trebskit on 2014-11-12.
 */
define(
    [
        'underscore',
        // angular injections
        'services/authenticationService',
        'services/navigation',
        'dialogs/login/def'
    ],
    function headerController(_) {
        return function ($scope, $rootScope, $interval, navigationService, loginDialog) {
            function setAuthenticationChangeListener() {
                $rootScope.$on('authentication.successful', function (ev, user) {
                    notificationPromise = $interval(function () {
                        user.getNotifications().then(function (nots) {
                            if (!_.isUndefined(nots)) {
                                nots = nots['collection'];
                                $scope.notifications = nots;
                            }
                        });
                    }, 10000);
                    navigationService
                        .getNavigationModel('top.header.nav')
                        .then(function onSuccess(data) {
                            $scope.userMenu = data;
                        });
                });
                $rootScope.$on('authentication.terminated', function () {
                    $interval.cancel(notificationPromise);
                    notificationPromise = undefined;
                    $scope.notifications = [];
                    $scope.userMenu = [];
                });
            }

            function setActions() {
                $scope.actions = {
                    popupLoginDialog : function ($event) {
                        $event.stopPropagation();
                        loginDialog.loginDialog();
                    },
                    popupLogoutDialog: function ($event) {
                        $event.stopPropagation();
                        loginDialog.logoutDialog();
                    }
                };
            }

            var notificationPromise = undefined;

            $scope.notifications = [];
            $scope.userMenu = [];

            setActions.bind(this)();
            setAuthenticationChangeListener.bind(this)();
        }
    }
);