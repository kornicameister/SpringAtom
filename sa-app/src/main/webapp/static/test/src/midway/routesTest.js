/**
 * Created by Tomasz on 2014-12-29.
 */

'use strict';

define(
    [
        'config/states',
        'angularMocks'
    ],
    function routesTest_midway(states) {
        console.log('routesTest_midway');

        describe('routesTest_midway >', function () {

            var state;

            beforeEach(function () {
                module('springatom');
                states.configure();
                inject(function ($state) {
                    state = $state;
                })
            });

            it('should have some states defined', inject(function ($state) {
                var states = $state.get();

                expect(states).toBeDefined();
                expect(states.length).toBeGreaterThan(0);
            }));

            describe('states >', function () {

                it('should have home state defined', function () {
                    var home = state.get('home');

                    expect(home).toBeDefined();
                    expect(home.name).toEqual('home');
                    expect(home.url).toEqual('/sa');
                });

                it('should have garage state defined', function () {
                    var garage = state.get('garage');

                    expect(garage).toBeDefined();
                    expect(garage.name).toEqual('garage');
                    expect(garage.url).toEqual('/sa/garage');
                });

                it('should have admin state defined', function () {
                    var admin = state.get('admin');

                    expect(admin).toBeDefined();
                    expect(admin.name).toEqual('admin');
                    expect(admin.url).toEqual('/sa/admin');
                });

                it('should have about state defined', function () {
                    var about = state.get('about');

                    expect(about).toBeDefined();
                    expect(about.name).toEqual('about');
                    expect(about.url).toEqual('/sa/about');
                });

            });

        });

    }
);