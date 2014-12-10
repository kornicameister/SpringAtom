/**
 * Created by trebskit on 2014-12-10.
 */
define(
    [
        'config/module'
    ],
    function recentlyUpdatedResource(app) {
        app.factory('recentlyUpdatedResource', function (Restangular) {
            var resource = Restangular.withConfig(function configurer(RestangularConfigurer) {
                RestangularConfigurer.setBaseUrl('rest/ru');
                RestangularConfigurer.setDefaultHttpFields({
                    cache: false
                });
                RestangularConfigurer.addResponseInterceptor(function responseInterceptor(data) {
                    return data.content;
                });
            });

            resource.getAll = function () {
                return this.one('get/all').get();
            };
            resource.getPage = function (pageNumber, pageSize, sort) {
                return this.one('get/page').get({
                    page: pageNumber,
                    size: pageSize,
                    sort: sort
                })
            };

            return resource;
        });
    }
);