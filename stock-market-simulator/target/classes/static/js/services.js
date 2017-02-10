var PortfolioRefreshService = angular.module('PortfolioRefreshService', [])
.service('Portfolio', function ($scope,$http) {
    var stocks = $http.get('/stocks')
                                    .then(function(response) {
                                        $scope.stock = null;
                                        if(response.data == null){
                                        $scope.stocks = null;
                                        }
                                        $scope.stocks = response.data;
                                    });

                                return stocks;
});