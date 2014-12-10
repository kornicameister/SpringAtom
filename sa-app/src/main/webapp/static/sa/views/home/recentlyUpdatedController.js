/**
 * Created by trebskit on 2014-12-10.
 */
define(
    [
        // angular deps
        'resources/recentlyUpdatedResource',
        'services/authenticationService'
    ],
    function recentlyUpdatedController() {
        return function ($scope,
                         $timeout,
                         $interval,
                         authenticationService,
                         recentlyUpdatedResource) {
            var refreshDataPromise = undefined,
                internal = {
                    pagingOptions: {
                        pageSizes  : [10, 50, 100],
                        pageSize   : 10,
                        currentPage: 1
                    },
                    applyData    : function (data) {
                        $scope.grid.page = data.page;
                        if (!$scope.$$phase) {
                            $scope.$apply();
                        }
                        return data;
                    },
                    loadData     : function (page, size) {

                        page = page || internal.pagingOptions.currentPage;
                        size = size || internal.pagingOptions.pageSize;

                        return recentlyUpdatedResource.getPage(page, size).then(function (data) {
                            internal.applyData(data, page, size);
                        });
                    }
                };

            $scope.grid = {
                id              : 'recentlyUpdatedGrid',
                title           : 'Ostatnie zmiany',
                page            : {
                    content: []
                },
                totalServerItems: 0,
                options         : {
                    columnDefs      : [
                        {
                            field      : 'id',
                            displayName: 'PK',
                            width      : 50,
                            visible    : authenticationService.hasAuthority('ROLE_ADMIN')
                        },
                        {
                            field      : 'label',
                            width      : 150,
                            displayName: 'Object'
                        },
                        {
                            field      : 'ts',
                            displayName: 'Updated'
                        }
                    ],
                    enablePaging    : true,
                    enablePinning   : true,
                    showFooter      : true,
                    data            : 'grid.page.content',
                    pagingOptions   : internal.pagingOptions,
                    totalServerItems: 'grid.totalServerItems'
                },
                css             : {
                    visible   : authenticationService.isAuthenticated(),
                    responsive: false,
                    border    : true,
                    hover     : true
                }
            };


            // set up watch for PK column
            $scope.$on('authentication.terminated', function () {
                $scope.grid.options.columnDefs[0].visible = false;
                $interval.cancel(refreshDataPromise);
                refreshDataPromise = undefined;
                $scope.grid.css.visible = false;

            });
            $scope.$on('authentication.successful', function () {

                $scope.grid.css.visible = authenticationService.isAuthenticated();
                $scope.grid.options.columnDefs[0].visible = authenticationService.hasAuthority('ROLE_ADMIN');

                internal.loadData().then(function () {
                    refreshDataPromise = $interval(internal.loadData, 1000000);
                });
            });

            // watchers for the grid interactions
            $scope.$watch('grid.options.pagingOptions', function (newVal, oldVal) {
                var newPage = newVal !== oldVal && newVal.currentPage !== oldVal.currentPage;
                var newSize = newVal !== oldVal && newVal.pageSize !== oldVal.pageSize;
                if (newPage || newSize) {
                    internal.loadData(newVal.currentPage, newVal.pageSize);
                }
            }, true);

            if (authenticationService.isAuthenticated()) {
                internal.loadData().then(function () {
                    refreshDataPromise = $interval(internal.loadData, 1000000);
                });
            }
        }
    }
);