/**
 * Created by Tomasz on 2014-12-29.
 */
define(
    [
        'angularMocks',
        'config/module'
    ],
    function moduleTest() {


        describe('moduleTest', function () {
            var appName = 'springatom',
                appModule;

            beforeEach(function () {
                appModule = module(appName);
            });

            it('springatom :: should have constant name set', inject(function ($injector) {
                "use strict";

                var constantName = $injector.get('appName');

                expect(constantName).toBeDefined();
                expect(constantName).toEqual('springatom');
            }));

            it('springatom :: should have constant timeoutDelay set', inject(function ($injector) {
                "use strict";

                var timeoutDelay = $injector.get('timeoutDelay');

                expect(timeoutDelay).toBeDefined();
            }));

            describe('springatom :: module should be initialized from config/module', function () {
                it('springatom :: should be registered', function () {
                    var localModule = angular.module(appName);

                    expect(localModule).not.toBeNull();

                    // should ensure that this is angular module
                    expect(localModule.run).toBeDefined();
                    expect(localModule.config).toBeDefined();
                });
            });

            describe('springatom :: dependencies', function () {
                var deps,
                    expectedDependencies = [
                        'ui.bootstrap',
                        'ui.calendar',
                        'ui.router',
                        'ngAnimate',
                        'ngCookies',
                        'ngGrid',
                        'dialogs.main',
                        'dialogs.default-translations',
                        'pascalprecht.translate',
                        'angularMoment',
                        'ngProgress',
                        'multi-select',
                        'LocalStorageModule',
                        'restangular'
                    ],
                    hasModule = function (m) {
                        return deps.indexOf(m) >= 0;
                    };

                beforeEach(function () {
                    appModule = angular.module(appName);
                    deps = appModule.requires;
                });

                it('should have all required dependencies as a dependency', function () {
                    for (var i = 0; i < expectedDependencies.length; i++) {
                        var dep = expectedDependencies[i];
                        expect(hasModule(dep)).toEqual(true);
                    }
                });

            })
        });
    }
);