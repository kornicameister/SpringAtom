angular
    .module('sg.common.data', [
        'sg.common.utils',
        'restangular'
    ])
    .constant('DATA_END_POINT', '/')
    .config(function (RestangularProvider, DATA_END_POINT, DEBUG_MODE) {
        RestangularProvider.setBaseUrl(DATA_END_POINT);
        RestangularProvider.setDefaultHttpFields({cache: DEBUG_MODE});
        RestangularProvider.setMethodOverriders(["put", "patch"]);
    });
