/**
 * Created by trebskit on 2014-11-18.
 */

define(
    [
        'config/module',
        'underscore',
        'jsface'
    ],
    function userResource(app, _) {

        app.factory('userResource', function userResource(Restangular) {
            var resource = Restangular.withConfig(function configurer(RestangularConfigurer) {
                RestangularConfigurer.setBaseUrl('cmp/user');
                RestangularConfigurer.setDefaultHttpFields({
                    cache: false
                });
                RestangularConfigurer.addResponseInterceptor(function (data, operation, what, url, response, deffered) {
                    var newResponse = undefined;
                    try {
                        switch (operation) {
                            case 'get':
                                newResponse = data.content;
                                break;
                        }
                        return newResponse;
                    } catch (error) {
                        deffered.reject(error);
                    }
                }.bind(resource));
            });

            resource.getCurrentUser = function () {
                return this.one('').get();
            }.bind(resource);
            resource.isAuthenticated = function () {
                return this.one('is/authenticated').get();
            }.bind(resource);

            resource.extendModel('', function (user) {
                user.addRestangularMethod('getNotifications', 'get', 'notifications', undefined, undefined);
                return user;
            });

            return resource;
        });

    }
);