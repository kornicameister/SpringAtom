define(
    [
        './user.module',
        '../../data/fireData.provider'
    ],
    function userResource(module) {
        "use strict";
        return module.factory('userResource', ['$FirebaseObject', '$fireData', userServiceFunc]);

        function userServiceFunc($FirebaseObject, $fireData) {
            var userFactory = $FirebaseObject.$extendFactory({
                    getDetails : function () {

                    },
                    getFullName: function () {
                        return this.firstName + " " + this.lastName;
                    }
                }),
                fireUser = $fireData.resource('users', {
                    objectFactory: userFactory
                });

            return fireUser.$asObject();
        }
    }
);