define(
    [
        'ilib/configurationHelper',
        'lodash',
        'angularMocks'
    ],
    function (ConfigurationHelper) {

        describe('ConfigurationHelper test', function () {

            beforeEach(function () {
                window.DEBUG_CONFIGURATION_HELPER = true;
            });

            it('is defined', function () {
                expect(ConfigurationHelper).toBeDefined();
            });

            it('is class', function () {
                expect(_.isFunction(ConfigurationHelper)).toEqual(true);
            });

            it('has static method to access new instance', function () {
                expect(ConfigurationHelper.newHelper).toBeDefined();
                expect(_.isFunction(ConfigurationHelper.newHelper)).toEqual(true);

                expect(ConfigurationHelper.newHelper()).toBeDefined();
                expect(ConfigurationHelper.newHelper() instanceof ConfigurationHelper).toEqual(true);
            });

            describe('logic', function () {

                var helper;

                beforeEach(function () {
                    helper = new ConfigurationHelper();
                });

                it('has dependencies initially empty', function () {
                    var deps = helper.dependencies;

                    expect(deps).toBeDefined();
                    expect(deps.length).toEqual(0);
                });

                it('addDependency with function call', function () {
                    var spy = {
                        getDeps: function () {
                            return {};
                        }
                    };

                    spyOn(spy, 'getDeps');
                    spyOn(helper, 'addDependency').and.callThrough();

                    helper.addDependency(spy.getDeps);

                    expect(helper.addDependency).toHaveBeenCalled();
                    expect(helper.dependencies.length).toEqual(1);
                });

                it('addDependency with object call', function () {
                    spyOn(helper, 'addDependency').and.callThrough();

                    helper.addDependency({});

                    expect(helper.addDependency).toHaveBeenCalled();
                    expect(helper.addDependency).toHaveBeenCalledWith({});
                    expect(helper.dependencies.length).toEqual(1);
                });

                it('addDependency with array empty call', function () {
                    spyOn(helper, 'addDependency').and.callThrough();

                    helper.addDependency([]);

                    expect(helper.addDependency).toHaveBeenCalled();
                    expect(helper.addDependency).toHaveBeenCalledWith([]);
                    expect(helper.dependencies.length).toEqual(0);
                });

                it('addDependency with array call', function () {
                    spyOn(helper, 'addDependency').and.callThrough();

                    helper.addDependency([{}]);

                    expect(helper.addDependency).toHaveBeenCalled();
                    expect(helper.addDependency).toHaveBeenCalledWith([{}]);
                    expect(helper.dependencies.length).toEqual(1);
                });

                it('addDependency with arrays call', function () {
                    spyOn(helper, 'addDependency').and.callThrough();

                    helper.addDependency([[{}], [{}]]);

                    expect(helper.addDependency.calls.count()).toEqual(3);
                    expect(helper.addDependency).toHaveBeenCalledWith([[{}], [{}]]);
                    expect(helper.dependencies.length).toEqual(2);
                });

                it('undefined callback', function () {
                    spyOn(helper, 'configure').and.callThrough();

                    expect(function () {
                        helper.configure()
                    }).toThrowError();
                });

                it('not a function callback', function () {
                    spyOn(helper, 'configure').and.callThrough();

                    expect(function () {
                        helper.configure({})
                    }).toThrowError();
                });

                it('callback not called, dependencies empty', function () {
                    spyOn(helper, 'configure').and.callThrough();
                    helper.dependencies = [];

                    var spy = {
                        getDeps: function () {
                            return {};
                        }
                    };

                    spyOn(spy, 'getDeps').and.callThrough();

                    helper.configure(spy.getDeps);

                    expect(helper.configure).toHaveBeenCalled();
                    expect(helper.configure).toHaveBeenCalledWith(spy.getDeps);

                    expect(spy.getDeps).not.toHaveBeenCalled();
                });

                it('callback called', function () {
                    spyOn(helper, 'configure').and.callThrough();
                    helper.dependencies = [{}, {}, {}];

                    var spy = {
                        getDeps: function () {
                            return {};
                        }
                    };

                    spyOn(spy, 'getDeps').and.callThrough();

                    helper.configure(spy.getDeps);

                    expect(helper.configure).toHaveBeenCalled();
                    expect(helper.configure).toHaveBeenCalledWith(spy.getDeps);

                    expect(spy.getDeps).toHaveBeenCalled();
                    expect(spy.getDeps.calls.count()).toEqual(3);
                });

            })

        })

    }
);