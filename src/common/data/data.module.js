angular.module('sg.common.data', ['sg.common.log', 'restangular'])
    .config(function (RestangularProvider) {
        RestangularProvider.setBaseUrl('/');
        RestangularProvider.setDefaultHttpFields({cache: true});
        RestangularProvider.setMethodOverriders(["put", "patch"]);
    });
