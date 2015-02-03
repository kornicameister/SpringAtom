define(
    [
        'angularMocks',
        'ilib/stateBuilder/stateBuilder.module'
    ],
    function () {
        "use strict";

        describe('$stateLabelResolve', function () {
            var $stateLabelResolve,
                labelService;

            beforeEach(angular.mock.module('stateBuilder', function ($provide) {
                labelService = {
                    getLabel: function () {
                    }
                };
                $provide.factory('labelService', function () {
                    return labelService;
                });
            }));
            beforeEach(angular.mock.inject(function (_$stateLabelResolve_) {
                $stateLabelResolve = _$stateLabelResolve_;
            }));

            it('it should have factory defined', function () {
                expect($stateLabelResolve).not.toBe(null);
                expect($stateLabelResolve).toBeDefined();
            });

            describe('check public API', function () {
                it('should have resolveLabel function', function () {
                    var resolveLabel = $stateLabelResolve.resolveLabel;

                    expect(resolveLabel).toBeDefined();
                    expect(angular.isFunction(resolveLabel)).toEqual(true);
                });
            });

            describe('check resolveLabel', function () {
                it('should return undefined for undefined state', function () {
                    expect($stateLabelResolve.resolveLabel(undefined)).not.toBeDefined();
                });
                it('should return undefined for missing definition in state', function () {
                    var state = {};

                    expect($stateLabelResolve.resolveLabel(state)).not.toBeDefined();
                });
                it('should return undefined for valid definition but no explicitly defined label', function () {
                    var state = {
                        name: ''
                    };

                    expect($stateLabelResolve.resolveLabel(state)).not.toBeDefined();
                });
                it('should return string for string defined label', function () {
                    var label = 'Test',
                        state = {
                            name : '',
                            label: label
                        };

                    expect($stateLabelResolve.resolveLabel(state)).toBeDefined();
                    expect($stateLabelResolve.resolveLabel(state)).toEqual(label);
                });
                it('should return label for ordinary defined function', function () {
                    var label = 'Test',
                        labelFunc = function () {
                            return label;
                        },
                        state = {
                            name : '',
                            label: labelFunc
                        };

                    spyOn(state, 'label').and.callThrough();

                    var actualLabel = $stateLabelResolve.resolveLabel(state);

                    expect(actualLabel).toBeDefined();
                    expect(actualLabel).toEqual(label);

                    expect(state.label).toHaveBeenCalled();
                    expect(state.label.calls.count()).toEqual(1);
                });
                it('should return label for DI defined function', function () {
                    var label = 'Test',
                        state = {
                            name : '',
                            label: function (labelService) {
                                return labelService.getLabel();
                            }
                        };

                    spyOn(labelService, 'getLabel').and.callFake(function () {
                        return label;
                    });

                    var actualLabel = $stateLabelResolve.resolveLabel(state);

                    expect(actualLabel).toBeDefined();
                    expect(actualLabel).toEqual(label);

                    expect(labelService.getLabel).toHaveBeenCalled();
                    expect(labelService.getLabel.calls.count()).toEqual(1);
                });
                it('should return label for DI as ARRAY defined function', function () {
                    var label = 'Test',
                        state = {
                            name : '',
                            label: ['labelService', function (a) {
                                return a.getLabel();
                            }]
                        };

                    spyOn(labelService, 'getLabel').and.callFake(function () {
                        return label;
                    });

                    var actualLabel = $stateLabelResolve.resolveLabel(state);

                    expect(actualLabel).toBeDefined();
                    expect(actualLabel).toEqual(label);

                    expect(labelService.getLabel).toHaveBeenCalled();
                    expect(labelService.getLabel.calls.count()).toEqual(1);
                });
            })
        })
    }
);