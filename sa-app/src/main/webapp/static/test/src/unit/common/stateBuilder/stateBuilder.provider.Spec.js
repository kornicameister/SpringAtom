define(
    [
        'angularMocks',
        'common/state/state.module'
    ],
    function () {
        "use strict";

        describe('stateBuilderProvider', function () {
            beforeEach(angular.mock.module('sg.state'));

            describe('provider', function () {
                var stateBuilderProvider,
                    stateProvider;

                beforeEach(function () {
                    angular.mock.module(function ($stateBuilderProvider, $stateProvider) {
                        stateBuilderProvider = $stateBuilderProvider;
                        stateProvider = $stateProvider;
                    });
                    angular.mock.inject(function () {
                    });
                });
                afterEach(function () {
                    stateBuilderProvider = undefined;
                });

                it('should be defined', function () {
                    expect(stateBuilderProvider).not.toBeNull();
                    expect(stateBuilderProvider).toBeDefined();
                });
                it('should have state configuration method', function () {
                    var func = stateBuilderProvider.state;

                    expect(func).toBeDefined();
                    expect(angular.isFunction(func)).toEqual(true);
                });

                describe('logic', function () {

                    describe('fails', function () {
                        it('should fail if state not defined', function () {
                            expect(function () {
                                stateBuilderProvider.state(undefined);
                            }).toThrowError("state is undefined, cannot configure");
                        });
                        it('should fail if state.name[key] not defined', function () {
                            expect(function () {
                                stateBuilderProvider.state({});
                            }).toThrowError('state.name is missing, cannot configure');
                        });
                    });

                    describe('resolveName', function () {
                        it('should resolve single state name', function () {
                            var expectedState,
                                state = {
                                    name: 'sg.test'
                                };

                            spyOn(stateProvider, 'state').and.callFake(function (state) {
                                expectedState = state;
                            });

                            stateBuilderProvider.state(state);

                            expect(stateProvider.state).toHaveBeenCalled();
                            expect(stateProvider.state.calls.count()).toEqual(1);

                            expect(state.name).toEqual(expectedState.name);
                            expect(expectedState.hasChildren).toEqual(false);
                        });
                        it('should resolve subsequent state names', function () {
                            var expectedStates = [],
                                state = {
                                    name    : 'sg.test',
                                    children: [{
                                        name      : 'a',
                                        definition: {}
                                    }]
                                };

                            spyOn(stateProvider, 'state').and.callFake(function (state) {
                                expectedStates.push(state);
                            });

                            stateBuilderProvider.state(state);

                            expect(stateProvider.state).toHaveBeenCalled();
                            expect(stateProvider.state.calls.count()).toEqual(2);

                            expect(state.name).toEqual(expectedStates[0].name);
                            expect(expectedStates[0].children).toBeUndefined();
                            expect(expectedStates[0].hasChildren).toEqual(true);

                            expect(state.name + '.a').toEqual(expectedStates[1].name);
                            expect(expectedStates[1].parent).not.toBeUndefined();
                            expect(expectedStates[1].parent.name).toEqual(state.name);
                        });
                    });

                });
            });

            describe('service', function () {
                var stateBuilder,
                    labelService;
                beforeEach(function () {
                    angular.mock.module(function ($provide) {
                        labelService = {
                            getLabel: function () {
                            }
                        };
                        $provide.factory('labelService', function () {
                            return labelService;
                        });
                    });
                    angular.mock.inject(function ($stateBuilder) {
                        stateBuilder = $stateBuilder;
                    });
                });

                it('should be defined', function () {
                    expect(stateBuilder).not.toBeNull();
                    expect(stateBuilder).toBeDefined();
                });
                it('should have API defined', function () {
                    var funcs = [
                            'cacheStateLabel',
                            'isLabelResolved',
                            'getStateLabel',
                            'getStateName',
                            'getParentState',
                            'hasParentState'
                        ],
                        func;
                    angular.forEach(funcs, function (key) {
                        func = stateBuilder[key];
                        expect(func).toBeDefined();
                        expect(angular.isFunction(func)).toEqual(true);
                    });
                });

                describe('should resolve parent state', function () {
                    beforeEach(function () {
                        spyOn(stateBuilder, 'getParentState').and.callThrough();
                    });
                    it('should return false for no parent', function () {
                        expect(stateBuilder.hasParentState({})).toEqual(false);
                    });
                    it('should return false for undefined parent', function () {
                        expect(stateBuilder.hasParentState({parent: undefined})).toEqual(false);
                    });
                    it('should return true for parent', function () {
                        expect(stateBuilder.hasParentState({parent: {}})).toEqual(true);
                    });
                    it('should return undefined for undefined parent', function () {
                        expect(stateBuilder.getParentState({})).toBeUndefined();
                    });
                    it('should return undefined for undefined parent [key exists]', function () {
                        expect(stateBuilder.getParentState({parent: undefined})).toBeUndefined();
                    });
                    it('should return parent for defined parent', function () {
                        var parent = {
                                prop: 'TEST'
                            },
                            state = {
                                parent: parent
                            };
                        var actualParent = stateBuilder.getParentState(state);

                        expect(actualParent).not.toBeUndefined();
                        expect(actualParent).toEqual(parent);
                    });
                    afterEach(function () {
                        expect(stateBuilder.getParentState.calls.count()).toEqual(1);
                    });
                });

                describe('should resolve label of state', function () {
                    var expectedLabel = 'sg.labelService.test';

                    it('as string', function () {
                        var state = {
                            name : 'a',
                            label: expectedLabel
                        };

                        expect(stateBuilder.isLabelResolved(state)).toEqual(false);
                        expect(stateBuilder.getStateLabel(state)).toEqual(expectedLabel);
                        expect(stateBuilder.isLabelResolved(state)).toEqual(true);
                    });
                    it('as function', function () {
                        var state = {
                            name : 'a',
                            label: function () {
                                return expectedLabel;
                            }
                        };

                        spyOn(state, 'label').and.callThrough();

                        expect(stateBuilder.isLabelResolved(state)).toEqual(false);
                        expect(stateBuilder.getStateLabel(state)).toEqual(expectedLabel);
                        expect(stateBuilder.isLabelResolved(state)).toEqual(true);

                        expect(state.label.calls.any()).toEqual(true);
                    });
                    it('as DI function', function () {
                        var state = {
                            name : 'a',
                            label: function (labelService) {
                                return labelService.getLabel();
                            }
                        };

                        spyOn(labelService, 'getLabel').and.callFake(function () {
                            return expectedLabel;
                        });

                        expect(stateBuilder.isLabelResolved(state)).toEqual(false);
                        expect(stateBuilder.getStateLabel(state)).toEqual(expectedLabel);
                        expect(stateBuilder.isLabelResolved(state)).toEqual(true);

                        expect(labelService.getLabel.calls.any()).toEqual(true);
                    });
                    it('as DI function', function () {
                        var state = {
                            name : 'a',
                            label: function (labelService) {
                                return labelService.getLabel();
                            }
                        };

                        spyOn(labelService, 'getLabel').and.callFake(function () {
                            return expectedLabel;
                        });

                        expect(stateBuilder.isLabelResolved(state)).toEqual(false);
                        expect(stateBuilder.getStateLabel(state)).toEqual(expectedLabel);
                        expect(stateBuilder.isLabelResolved(state)).toEqual(true);

                        expect(labelService.getLabel.calls.any()).toEqual(true);
                    });
                    it('as DI [array declaration] function', function () {
                        var state = {
                            name : 'a',
                            label: ['labelService', function (a) {
                                return a.getLabel();
                            }]
                        };

                        spyOn(labelService, 'getLabel').and.callFake(function () {
                            return expectedLabel;
                        });

                        expect(stateBuilder.isLabelResolved(state)).toEqual(false);
                        expect(stateBuilder.getStateLabel(state)).toEqual(expectedLabel);
                        expect(stateBuilder.isLabelResolved(state)).toEqual(true);

                        expect(labelService.getLabel.calls.any()).toEqual(true);
                    });

                });
            });
        });
    }
);