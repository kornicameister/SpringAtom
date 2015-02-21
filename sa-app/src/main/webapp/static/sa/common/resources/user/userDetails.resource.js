define(
    [
        './user.module',
        '../../data/fireData.provider'
    ],
    function userResource(module) {
        "use strict";
        return module.factory('userDetailsResource', ['$FirebaseObject', '$fireData', userServiceFunc]);

        function userServiceFunc($FirebaseObject, $fireData) {
            var userDetailsFactory = $FirebaseObject.$extendFactory({}),
                fireUser = $fireData.resource('users/details', {
                    objectFactory: userDetailsFactory
                });

            return fireUser.$asObject();
        }
    }
);