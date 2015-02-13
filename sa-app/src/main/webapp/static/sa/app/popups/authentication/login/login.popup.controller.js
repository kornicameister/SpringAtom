define(
    [
        'lodash',
        '../authentication.popup.module'
    ],
    function loginPopupController(_, module){
        
        return module.controller('LoginPopupController',['$modalInstance', '$scope', ctrl]);
        
        function cancelEventWrapper(func, $event){
            func($event);
            $event.preventDefault();
            return false;
        }
        
        function ctrl($modalInstance, $scope){
            // private
            var vm = this;
            
            vm.username = '';
            vm.password = '';
            vm.forgotPassword = _.wrap(forgotPassword.bind(vm), cancelEventWrapper);
            vm.register = _.wrap(register.bind(vm), cancelEventWrapper);
            vm.login = _.wrap(login.bind(vm), cancelEventWrapper);
            vm.cancel = _.wrap(cancel.bind(vm), cancelEventWrapper);
            
            // listeners
            $scope.$on('$destroy', destroy.bind(vm));
            
            function destroy(){
                
            }
            
            function forgotPassword($event){
                
            }
            
            function register($event){
                
            }
            
            function login($event){
                
            }
            
            function cancel($event){
                
            }
        }
    }
)