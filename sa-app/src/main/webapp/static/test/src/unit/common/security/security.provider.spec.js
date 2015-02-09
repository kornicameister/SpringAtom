define(
    [
        'angularMocks',
        'common/security/security.module'
    ],
    function () {
        describe('$securityProvider', function(){

            beforeEach(angular.mock.module('sg.security'));

            describe('default values', function () {
                var security;

                beforeEach(function () {
                    angular.mock.inject(function ($security) {
                        security = $security;
                    });
                });

                it('enableStateSecurity should return false by default', function(){
                    expect(security.isStateSecurityEnabled()).toEqual(false);
                });

                it('enableAuthenticateOnRun should return false by default', function(){
                    expect(security.isOnRunAuthenticationEnabled()).toEqual(false);
                });
            });

            describe('non-default values', function () {
                var securityProvider,
                    security;

                beforeEach(function () {
                    angular.mock.module(function ($securityProvider) {
                        securityProvider = $securityProvider;

                        securityProvider.enableStateSecurity();
                        securityProvider.enableAuthenticateOnRun();

                    });
                    angular.mock.inject(function ($security) {
                        security = $security;
                    });
                });

                it('enableStateSecurity should return true', function () {
                    expect(security.isStateSecurityEnabled()).toEqual(true);
                });

                it('enableAuthenticateOnRun should return true', function () {
                    expect(security.isOnRunAuthenticationEnabled()).toEqual(true);
                });

            })

        })
    }
);