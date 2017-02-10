var mainApp = angular.module("mainApp", [
    'ngRoute'//,
    //'DetailsInjectorService'
]);

//var DetailsInjectorService = angular.module('DetailsInjectorService', [])
//        .service('Details', function () {
//            this.company = function (ticker) {
//                return ticker
//            };
//        });

mainApp.config(['$routeProvider', function ($routeProvider) {
        $routeProvider.
                when('/list', {
                    templateUrl: 'partials/list.html',
                    controller: 'ListController'
                }).
                when('/details/:itemId', {
                    templateUrl: 'partials/details.html',
                    controller: 'DetailsController'
                }).
                otherwise({
                    redirectTo: '/list'
                });
    }]);








