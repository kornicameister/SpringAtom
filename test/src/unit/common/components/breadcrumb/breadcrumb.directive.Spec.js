define(
    [
        'angularMocks',
        'common/components/breadcrumb/breadcrumb.directive'
    ],
    function () {
        "use strict";
        describe('sgBreadcrumb directive', function () {
            var scope,
                element,
                breadcrumbService,
                q;

            function initWithCrumbs(crumbs) {
                crumbs = crumbs || [];
                return angular.mock.inject(function ($rootScope, $compile, $q, breadcrumbService) {
                    scope = $rootScope.$new();
                    q = $q;

                    spyOn(breadcrumbService, 'getBreadcrumbs').and.callFake(function () {
                        return q(function (resolve) {
                            resolve(crumbs);
                        })
                    });

                    element = angular.element('<sg-breadcrumb></sg-breadcrumb>');

                    $compile(element)(scope);
                    scope.$digest();
                });
            }

            function mockCrumbs(count) {
                var arr = [];
                while (count--) {
                    arr.push({
                        active: count - 1 === 0,
                        label : 'Crumb[' + count + ']'
                    })
                }
                return arr;
            }

            beforeEach(function () {
                angular.mock.module('templates');
                angular.mock.module('sg.components.breadcrumb');
                angular.mock.module(function ($provide) {
                    $provide.factory('breadcrumbService', function () {
                        return breadcrumbService = {
                            newCrumb      : function () {

                            },
                            getBreadcrumbs: function () {

                            }
                        }
                    });
                });
            });

            describe('empty crumbs', function () {

                beforeEach(initWithCrumbs([]));

                it('should heave sg-breadcrumb header defined', function () {
                    var header = element.find('header');
                    expect(header.length).toEqual(1);
                });

                it('should have no li elements', function () {
                    var liEls = element.find('li');
                    expect(liEls.length).toEqual(0);
                });

                afterEach(function () {
                    scope.$destroy();
                    breadcrumbService = undefined;
                });
            });

            describe('crumbs provided', function () {
                var length = 10;

                beforeEach(initWithCrumbs(mockCrumbs(length)));

                it('should have 10 li elements', function () {
                    var liEls = $(element[0]).find('li');
                    expect(liEls.length).toEqual(length);
                });
                it('should have at least one active crumb', function () {
                    var liEls = $(element[0]).find('li.active');
                    expect(liEls.length).toEqual(1);
                });

                afterEach(function () {
                    scope.$destroy();
                    breadcrumbService = undefined;
                });
            });
        })
    }
);