
mainApp.controller('stockController', ['$scope', '$http', function ($scope, $http) {

        var hey;

        $http.get('/currentuser')
                            .then(function (response) {
                                $scope.stock = null;
                                if (response.data == null) {
                                    $scope.currentUser = null;
                                }
                                $scope.currentUser = response.data;
                                $scope.currentUser.firstName = response.data.firstName;
                                $scope.currentUser.lastName = response.data.lastName;
                                $scope.currentUser.email = response.data.email;
                                $scope.currentUser.userID = response.data.userID;
                                $scope.funds = response.data.funds;
                                $scope.notLoggedIn=true;
                                if(response.data.firstName){
                                    $scope.currentUser = response.data;
                                    $scope.currentUser.firstName = response.data.firstName;
                                    $scope.currentUser.lastName = response.data.lastName;
                                    $scope.currentUser.email = response.data.email;
                                    $scope.currentUser.userID = response.data.userID;
                                    $scope.funds = response.data.funds;
                                    $scope.signedInAs = ("Signed in as " + response.data.firstName);
                                    $scope.loggedIn = true;
                                    $scope.notLoggedIn = false;
                                }
                                if(!response.data.firstName){
                                    $scope.loggedIn = false;
                                    $scope.notLoggedIn = true;
                                    $scope.signedInAs = ("Not signed in");
                                }
                            });

        $scope.shares = '';
        $scope.showSearch = false;
        $scope.stocks = $http.get('/stocks')
                .then(function (response) {
                    $scope.stock = null;
                    if (response.data == null) {
                        $scope.stocks = null;
                    }
                    hey = response.data;
                    $scope.stocks = response.data;
                });
        $scope.stocks = hey;
        $scope.selectedIndex = -1;
        $scope.itemClicked = function ($index) {
            console.log($index);
            $scope.selectedIndex = $index;
        }

        $scope.removeStock = function(){
            var t =  angular.element('#t-details').val();
            var funds = angular.element('#details-funds').val();
            var valueOfShares = angular.element('#details-shares-value').val();
            $http.delete('/stock/' + t)
                                .then(function (response) {
                                    $scope.stocks = null;
                                    if (response.data == null) {
                                        $scope.stocks = null;
                                    }
                                    $scope.stocks = response.data;
                                    angular.element('#details').hide();
                                    $scope.funds = funds - valueOfShares;
                                });
        }

        $scope.updateShareCount = function () {
            var symbol = angular.element("#t-details").val();
            var count;
            $http.get('/stock/' + symbol)
                    .then(function (response) {
                        $scope.stock = null;
                        if (response.data == null) {
                            $scope.companyDetails = null;
                        }
                        count = response.data.shareCount;
                        //alert(count);
                        $scope.companyDetails.shareCount = response.data.shareCount;
                    });
        }

        $scope.updatePortfolio = function () {
            $http.get('/stocks')
                    .then(function (response) {
                        $scope.stock = null;
                        if (response.data == null) {
                            $scope.stocks = null;
                        }
                        $scope.stocks = response.data;
                    });
        };

        $scope.barChart = function () {
            $http.get('/stocks')
                    .then(function (response) {
                        $scope.stock = null;
                        if (response.data == null) {
                            $scope.stocks = null;
                        }
                        $('#graph-container').empty();
                        $('#details').hide();
                        $('#barChart').empty();
                        $('#barChart').show();
                        $('#portfolioDetails').show();
                        msg = response.data;
                        Graph(msg);
                    });
        };


        $scope.getDetails = function () {
            var symbol = event.target.id;
            $scope.showSearch = false;
            $scope.formVisible = false;
            $scope.buttonVisible = true;
            $http.get('/stock/' + symbol)
                    .then(function (response) {
                        $scope.stock = null;
                        if (response.data == null) {
                            $scope.companyDetails = null;
                        }
                        $('#barChart').hide();
                        $('#portfolioDetails').hide();
                        $('#list').hide();
                        $('#details').show();
                        angular.element('#t-details').val(response.data.t);
                        $scope.companyDetails = response.data;
                    });

            $http.get('/quotes/' + symbol)
                    .then(function (response) {
                        $scope.stock = null;
                        if (response.data == null) {
                            $scope.companyDetails = null;
                        }
                        $('#details').show();
                        $('#graph-container').empty();
                        var msg = response.data;
                        console.log(msg);
                        NewGraph(msg);
                    });
            $scope.formVisible = false;
            $scope.buttonVisible = true;
        };

        function  getDetails(t) {
            $scope.formVisible = false;
            $scope.buttonVisible = true;
            $http.get('/stock/' + t)
                    .then(function (response) {
                        $scope.stock = null;
                        if (response.data == null) {
                            $scope.companyDetails = null;
                        }
                        $('#barChart').hide();
                        $('#portfolioDetails').hide();
                        $('#details').show();
                        angular.element('#t-details').val(response.data.t);
                        var result = response.data;
                        return result;
                    });
            return result;
        }
        ;

        $scope.purchaseReset = function () {
            $scope.formVisible = false;
            $scope.saleFormVisible = false;
            $scope.buttonVisible = true;
            $scope.sellButtonVisible = true;
            angular.element('#buyShares').one('focus', function(e){$(this).blur();});
            angular.element('#sellShares').one('focus', function(e){$(this).blur();});
            angular.element('#remove').one('focus', function(e){$(this).blur();});
        }
        /*BEGINNING OF BUY FUNCTIONS*/
        $scope.buttonVisible = true;
        $scope.formVisible = false;
        $scope.showForm = function () {
            $scope.formVisible = true;
            $scope.sellButtonVisible = false;
            $scope.buttonVisible = false;
            $scope.saleFormVisible = false;
        }


         $scope.buyShares = function () {
                    var amount = angular.element('#buy-share-price').val();
                    var t = angular.element('#t-details').val();
                    var number = Number(angular.element('#purchase-quantity').val());
                    var parameter = JSON.stringify({type: "Transaction", symbol: t, shareCount: number, cost: amount});
                    var f;
                    $http.post("/buy", parameter)
                            .then(function (response) {
                                $scope.funds = null;
                                if (response.data == null) {
                                    $scope.stocks = null;
                                }
                                f = response.data.funds.toString().match(/^-?\d+(?:\.\d{0,2})?/)[0];
                                $scope.companyDetails.myShares = response.data.shareCount;
                                $scope.funds = f;
                                $scope.formVisible = false;
                                $scope.buttonVisible = true;
                                angular.element('#purchase-quantity').val('');
                            });
                }
        /*END OF BUY FUNCTIONS*/

        /*BEGINNING OF SELL FUNCTIONS*/
        $scope.sellButtonVisible = true;
        $scope.saleFormVisible = false;

        $scope.showSaleForm = function () {
        $scope.saleFormVisible = true;
            $scope.sellButtonVisible = false;
            $scope.buttonVisible = false;
            $scope.formVisible = false;
        }

       $scope.sellShares = function () {
                   var t = angular.element('#t-details').val();
                   var number = Number(angular.element('#sale-quantity').val());
                   var amount = angular.element('#sale-share-price').val() * number;
                   var parameter = JSON.stringify({type: "Transaction", symbol: t, shareCount: number, cost: amount});
                   var f;
                   $http.post("/sell", parameter)
                           .then(function (response) {
                               $scope.funds = null;
                               if (response.data == null) {
                                   $scope.stocks = null;
                               }
                               f = response.data.funds.toString().match(/^-?\d+(?:\.\d{0,2})?/)[0];
                               $scope.companyDetails.myShares = response.data.shareCount;
                               $scope.funds = f;
                               $scope.formVisible = false;
                               $scope.buttonVisible = true;
                               angular.element('#sale-quantity').val('');
                           });
               }
        /*END OF SELL FUNCTIONS*/

        $scope.updateFunds = function () {
            $http.get('/funds')
                    .then(function (response) {
                        $scope.funds = null;
                        if (response.data == null) {
                            $scope.funds = null;
                        }
                        var re = response.data;
                        var with2Decimals = re.toString().match(/^-?\d+(?:\.\d{0,2})?/)[0];
                        return with2Decimals;
                    });
        }

        $scope.funds =
                $http.get('/funds')
                .then(function (response) {
                    $scope.stock = null;
                    if (response.data == null) {
                        $scope.stocks = null;
                    }
                    var msg = response.data;
                    var with2Decimals = msg.toString().match(/^-?\d+(?:\.\d{0,2})?/)[0];
                    $scope.funds = with2Decimals;
                });

        $scope.addFunds = function () {
            var amount = Number($("#funds-input").val());
            $http.post('/funds/' + amount)
                    .then(function (response) {
                        $scope.stock = null;
                        if (response.data == null) {
                            $scope.funds = null;
                        }
                        var msg = response.data;
                        var with2Decimals = msg.toString().match(/^-?\d+(?:\.\d{0,2})?/)[0];
                        $scope.funds = with2Decimals;
                        angular.element('#funds-input').val('');
                    });
        }

//    }]);


/* LIST OF INDEXES *//* LIST OF INDEXES *//* LIST OF INDEXES */
//controllers.controller('ListController', ['$scope', '$http', function ($scope, $http) {
        $http.get('NYSE.json').success(function (data) {
            $scope.companies = data;
            $scope.companyOrder = 'name';
        });

        $scope.update = function () {
            $http.get('/stocks')
                    .then(function (response) {
                        $scope.stock = null;
                        if (response.data == null) {
                            $scope.stocks = null;
                        }
                       // alert(response.data);
                        $scope.stocks = response.data;
                    });
        };

        $scope.descending = function () {
            $scope.companyOrder = '-' + 'name';
        }

        $scope.ascending = function () {
            $scope.companyOrder = '+' + 'name';
        }

        $scope.searchFocus = function () {
            event.preventDefault();
            $scope.showSearch = true;
            $('#details').hide();
            $('#barChart').hide();
            $('#list').show();
        }

        $scope.hideSearch = function () {
          //  alert("hide search");
          $scope.showSearch = false;
            $('#listWrap').hide();
            $('#details').show();
            $('#barChart').show();
        }

        $scope.updateDetails = function () {
            var symbol = angular.element('#t-details').val();
            $http.get('/stock/' + symbol)
                    .then(function (response) {
                        $scope.stock = null;
                        if (response.data == null) {
                            $scope.companyDetails = null;
                        }
                        $scope.companyDetails = response.data;
                    });
        }


    $scope.showModal = function(){
        $scope.modalVisible = true;
    }

    $scope.round = function(re){
        var with2Decimals = re.toString().match(/^-?\d+(?:\.\d{0,2})?/)[0];
        return with2Decimals;
    }

$scope.signedInAs ="Not signed in";
$scope.registered = false;
$scope.notRegistered = true;

$scope.register = function(){
var firstName = angular.element('#register-first-name').val();
var lastName = angular.element('#register-last-name').val();
var password = angular.element('#register-password').val();
var email = angular.element('#register-email').val();
var funds;

var parameter = JSON.stringify({type: "User", firstName: firstName, lastName: lastName, email: email, password: password});
$http.put("/user/create", parameter)
                    .then(function (response) {
                        $scope.funds = null;
                        if (response.data == null) {
                            $scope.user = null;
                        }
                        $scope.funds = response.data.funds;
                        $scope.firstName= response.data.firstName;
                        $scope.signedInAs = ("Signed in as "+response.data.firstName);
                        $scope.lastName= response.data.lastName;
                        $scope.email= response.data.email;
                        $scope.userID = response.data.userID;
                        $scope.registered = true;
                        $scope.notRegistered = false;
                        //angular.element('#purchase-quantity').val('');
                    });
}

$scope.resetRegisterModal = function(){
    $scope.registered = false;
    $scope.notRegistered = true;
}
//$scope.currentUser =
//            $http.get('/user')
//                    .then(function (response) {
//                        $scope.stock = null;
//                        if (response.data == null) {
//                            $scope.currentUser = null;
//                        }
//                        $scope.currentUser = response.data;
//                        $scope.currentUser.firstName = response.data.firstName;
//                        $scope.currentUser.lastName = response.data.lastName;
//                        $scope.currentUser.email = response.data.email;
//                        $scope.currentUser.userID = response.data.userID;
//                        $scope.funds = response.data.funds;
//                        $scope.firstName = response.data.firstName;
//                    });




$scope.login = function(){
var email = angular.element('#login-email').val();
var password = angular.element('#login-password').val();
var parameter = JSON.stringify({type: "User", email: email, password: password});
$http.post("/login", parameter)
                    .then(function (response) {
                      //  $scope.funds = null;
                        if (response.data == null) {
                            $scope.user = null;
                        }
                        $scope.funds = response.data.funds;
                        $scope.firstName= response.data.firstName;
                        $scope.signedInAs = ("Signed in as "+response.data.firstName);
                        $scope.lastName= response.data.lastName;
                        $scope.email= response.data.email;
                        $scope.userID = response.data.userID;
                        $scope.loggedIn = true;
                        $scope.notLoggedIn = false;
                        //angular.element('#purchase-quantity').val('');
                    });
}

$scope.resetLoginModal = function(){
    $scope.loggedIn = false;
    $scope.notLoggedIn = true;
}


    }]);

//    mainApp.controller('ModalController', function($scope, close) {
//
//     $scope.close = function(result) {
//     	close(result, 500); // close, but give 500ms for bootstrap to animate
//     };
//
//    });



mainApp.controller('tickerController', ['$scope', '$http', function ($scope, $http) {
        $scope.tickers = $http.get('NYSE.json')
                .then(function (response) {
                    $scope.tickers = response.data;
                });
    }]);

//    mainApp.controller('LoginController', ['$scope', '$http', function ($scope, $http) {
//        $scope.user = {
//            firstName: "Foo",
//            lastName: "Bar"
//        };
//        $scope.logins = [];
//        $scope.login = function () {
//            $scope.logins.push($scope.user.firstName + " was logged in.");
//        };
//    }]);
