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
                module;

            beforeEach(function () {
                module = angular.module(appName);
            });

            it('springatom :: should be registered', function () {
                expect(module).not.toBeNull();
            });

            it('springatom :: should have constant name set', function () {

            });

            it('springatom :: should have constant timeoutDelay set', function () {

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
                    deps = module.value(appName).requires;
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