define(
    [
        'angularMocks',
        'common/navigation/navigation.provider'
    ],
    function () {
        "use strict";
        describe('$navigationProvider', function () {
            var navigationProvider,
                navigation;

            beforeEach(angular.mock.module('sg.navigation'));

            describe('no navigation provided', function () {
                beforeEach(function () {
                    angular.mock.module(function ($navigationProvider) {
                        navigationProvider = $navigationProvider;
                    });
                    angular.mock.inject(function ($navigation) {
                        navigation = $navigation;
                    });
                });

                it('should return empty navigation for every state', function () {
                    var states = ['a', 'b', 'c'],
                        l = states.length;

                    while (l--) {
                        expect(navigation.getNavigation(states[l])).toBeDefined();
                        expect(navigation.getNavigation(states[l])).toEqual([]);
                    }
                });
            });

            describe('navigation provider', function () {
                beforeEach(function () {
                    angular.mock.module(function ($navigationProvider) {
                        navigationProvider = $navigationProvider;

                        navigationProvider.navigation('a', ['b', 'c']);
                        navigationProvider.navigation('b', ['a', 'c']);
                        navigationProvider.navigation('c', ['a', 'b']);
                    });
                    angular.mock.inject(function ($navigation) {
                        navigation = $navigation;
                    });
                });

                it('should have 3 navigation items defined', function () {
                    expect(navigation.getCount()).toEqual(3);
                });

                it('should navigate to b,c from a', function () {
                    expect(navigation.getNavigation('a')).toEqual(['b', 'c']);
                });

                it('should navigate to a,c from b', function () {
                    expect(navigation.getNavigation('b')).toEqual(['a', 'c']);
                });

                it('should navigate to a,b from c', function () {
                    expect(navigation.getNavigation('c')).toEqual(['a', 'b']);
                });
            })
        })
    }
);