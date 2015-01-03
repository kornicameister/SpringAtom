define(
    [
        'config/states',
        'ngMidwayTester'
    ],
    function (states) {
        describe('Midway :: routesTest', function () {

            var tester, state;

            beforeEach(function () {
                states.configure();
                tester = ngMidwayTester('springatom');
                state = tester.inject('$state');
            });

            afterEach(function () {
                tester.destroy();
                tester = null;
            });

            it("should have a working home route", function () {
                var homeState = state.get('home');

                expect(homeState).toBeDefined();
                expect(state.href(homeState)).toEqual('#/sa')
            });

            it('should have a working garage route', function () {
                var garage = state.get('garage');

                expect(garage).toBeDefined();
                expect(state.href(garage)).toEqual('#/sa/garage')
            });

            it('should have a working admin route', function () {
                var admin = state.get('admin');

                expect(admin).toBeDefined();
                expect(state.href(admin)).toEqual('#/sa/admin')
            });

            it('should have a working about route', function () {
                var about = state.get('about');

                expect(about).toBeDefined();
                expect(state.href(about)).toEqual('#/sa/about')
            });

            describe('wizards', function () {
                describe('newAppointment', function () {
                    it('should have a working wrapping state', function () {
                        var wrapping = state.get('newAppointment');

                        expect(wrapping).toBeDefined();
                        expect(state.href(wrapping)).toEqual('#/sa/wizard/new/appointment');
                    });
                    it('should have a working definition step', function () {
                        var definition = state.get('newAppointment.definition');

                        expect(definition).toBeDefined();
                        expect(state.href(definition)).toEqual('#/sa/wizard/new/appointment/definition');
                    });
                    it('should have a working tasks step', function () {
                        var tasks = state.get('newAppointment.tasks');

                        expect(tasks).toBeDefined();
                        expect(state.href(tasks)).toEqual('#/sa/wizard/new/appointment/tasks');
                    });
                    it('should have a working comment step', function () {
                        var comment = state.get('newAppointment.comment');

                        expect(comment).toBeDefined();
                        expect(state.href(comment)).toEqual('#/sa/wizard/new/appointment/comment');
                    });
                });

                describe('newCar', function () {
                    it('should have a working wrapping state', function () {
                        var wrapping = state.get('newCar');

                        expect(wrapping).toBeDefined();
                        expect(state.href(wrapping)).toEqual('#/sa/wizard/new/car');
                    });
                    it('should have a working vin step', function () {
                        var vin = state.get('newCar.vin');

                        expect(vin).toBeDefined();
                        expect(state.href(vin)).toEqual('#/sa/wizard/new/car/vin');
                    });
                    it('should have a working car step', function () {
                        var car = state.get('newCar.car');

                        expect(car).toBeDefined();
                        expect(state.href(car)).toEqual('#/sa/wizard/new/car/car');
                    });
                    it('should have a working owner step', function () {
                        var owner = state.get('newCar.owner');

                        expect(owner).toBeDefined();
                        expect(state.href(owner)).toEqual('#/sa/wizard/new/car/owner');
                    });
                });

                describe('newUser', function () {
                    it('should have a working wrapping state', function () {
                        var wrapping = state.get('newUser');

                        expect(wrapping).toBeDefined();
                        expect(state.href(wrapping)).toEqual('#/sa/wizard/new/user');
                    });
                    it('should have a working credentials step', function () {
                        var credentials = state.get('newUser.credentials');

                        expect(credentials).toBeDefined();
                        expect(state.href(credentials)).toEqual('#/sa/wizard/new/user/credentials');
                    });
                    it('should have a working authorities step', function () {
                        var authorities = state.get('newUser.authorities');

                        expect(authorities).toBeDefined();
                        expect(state.href(authorities)).toEqual('#/sa/wizard/new/user/authorities');
                    });
                    it('should have a working contacts step', function () {
                        var contacts = state.get('newUser.contacts');

                        expect(contacts).toBeDefined();
                        expect(state.href(contacts)).toEqual('#/sa/wizard/new/user/contacts');
                    });
                });
            })

        })
    }
);