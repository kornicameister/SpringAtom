define(
    [
        '../authentication.popup.module'
    ],
    function logoutPopupController(module){
        
        return module.controller('LogoutPopupController',['$scope', ctrl]);
        
        function ctrl($scope){
            // private
            var vm = this;
            
            // listeners
            $scope.$on('$destroy', destroy.bind(vm));
            
            function destroy(){
                
            }
        }
    }
)