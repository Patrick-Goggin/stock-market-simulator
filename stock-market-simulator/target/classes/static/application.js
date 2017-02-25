//$(document).ready(function() {
//        $("a").click(function(event) {
//            alert(event.target.id);
//        });
//});
//$('#listWrap').hide();
$(document).ready(function () {
$('#details').hide();
$('#portfolioDetails').hide();

//$('#purchase-form').hide();
 $.ajax({
        url: 'stocksApi',
        type: 'GET',
        async: false,
        cache: false,
        timeout: 30000,
        error: function(){
            return true;
        },
        success: function(msg){
            if (false){
                return false;
            } else {
                renderStockList(msg)
                return true;
            }
        }
    });
//$.ajax({
//             type: 'GET',
//             url: 'stocksApi',
//             headers: {
//                 'Accept': 'application/json',
//                 'Content-Type': 'application/json',
//                  'dataType': 'jsonp'
//             },
//              success: function (data) {
//              var d = JSON.parse(data)
//             renderStockList(data);
//                                           }
//         });

//$('a').click(function() {
//var link = event.target.id;
//      $("a.active").removeClass("active");
//      $(link).addClass('active');
//});
function focus() {
    $(this).focus();
}

$('#search-input').focus(function (event) {
//alert("hey");
    event.preventDefault();
    $('#details').hide();
    $('#barChart').hide();
    $('#list').show();

});

$('#search-input').click(function (event) {
//alert("hey");
    event.preventDefault();
    $('#details').hide();
    $('#barChart').hide();
    $('#list').show();

});
//getFunds();
//$.ajax({
//                       url: '/funds',
//                       type: 'GET',
//                       async: false,
//                       cache: false,
//                       timeout: 30000,
//                       error: function(){
//                           return true;
//                       },
//                       success: function(msg){
//                           if (false){
//                               return false;
//                           } else {
//                                  //addStock(msg);
//                                  //renderPortfolio(msg);
//                                  alert(msg);
//                                  renderFunds(msg);
//                               return true;
//                           }
//                       }
//                   });
});
//$('a').click(function() {
//      $("a.active").removeClass("active");
//      $(this).addClass('active');
//});
//function focus() {
//    //event.preventDefault();
//   // alert(event.target.id);
//   var link = event.target.id;
//    $(".focus").removeClass(".focus");
//    $("a").removeClass(".focus");
//    $link.addClass(".focus");
//
//    //Some code
//}
//$("a.readmore").click(function (event) {
//    event.preventDefault();
//   // alert(event.target.id);
//   // var link = event.target.id;
//    $(".focus").removeClass(".focus");
//    $("a").removeClass(".focus");
//   // $(link).addClass(".focus");
//
//    //Some code
//});
//function getFunds(){
//$.ajax({
//                       url: '/funds',
//                       type: 'GET',
//                       async: false,
//                       cache: false,
//                       timeout: 30000,
//                       error: function(){
//                           return true;
//                       },
//                       success: function(msg){
//                           if (false){
//                               return false;
//                           } else {
//                                  //addStock(msg);
//                                  //renderPortfolio(msg);
//                                  //alert(msg);
//                                  var with2Decimals = msg.toString().match(/^-?\d+(?:\.\d{0,2})?/)[0]
//                                  renderFunds(with2Decimals);
//                               return true;
//                           }
//                       }
//                   });
//}
function renderFunds(msg){
$('#funds').text(msg);
}
function quotes() {
event.preventDefault();
var symbol = event.target.id;
    $.ajax({
            url: '/quotes/' + symbol,
            type: 'GET',
            async: false,
            cache: false,
            timeout: 30000,
            error: function(){
                return true;
            },
            success: function(msg){
                if (false){
                    return false;
                } else {
                $('#graph').empty();
                $('#list').hide();
                $('#graph-container').show();
             console.log(msg);
             InitChart(msg);
                    return true;
                }
            }
        });
    }

function refreshList(){
$.ajax({
url: 'stocks',
type: 'GET',
async: false,
cache: false,
timeout: 30000,
error: function(){
                                        return true;
                                    },
                                    success: function(msg){
                                        if (false){
                                            return false;
                                        } else {
                                               renderPortfolio(msg);
                                            return true;
                                        }
                                    }
                                });
}
function renderStockList(stockList, status){
$.each(stockList, function (index, stock) {
        if (stock !== "null" && stock !== "") {
            $('#test').append($('<div>')
                    .attr({
                           'id': 'stock',
                           'data-stock-id': stock.id,
                          'data-stock-t': stock.t,
                          'data-stock-e': stock.e,
                          'data-stock-l': stock.l,
                          'data-stock-l_fix': stock.l_fix,
                          'data-stock-l_cur': stock.l_cur,
                          'data-stock-s': stock.s,
                          'data-stock-ltt': stock.ltt,
                          'data-stock-lt': stock.lt,
                          'data-stock-lt_dts': stock.lt_dts,
                          'data-stock-c': stock.c,
                          'data-stock-c_fix': stock.c_fix,
                          'data-stock-cp': stock.cp,
                          'data-stock-cp_fix': stock.cp_fix,
                          'data-stock-ccol': stock.ccol,
                          'data-stock-pcls_fix': stock.pcls_fix,
                          'data-stock-eo': stock.eo,
                          'data-stock-delay': stock.delay,
                          'data-stock-op': stock.op,
                          'data-stock-hi': stock.hi,
                          'data-stock-lo': stock.lo,
                          'data-stock-vo': stock.vo,
                          'data-stock-avvo': stock.avvo,
                          'data-stock-hi52': stock.hi52,
                          'data-stock-lo52': stock.lo52,
                          'data-stock-mc': stock.mc,
                          'data-stock-pe': stock.pe,
                          'data-stock-fwpe': stock.fwpe,
                          'data-stock-beta': stock.beta,
                          'data-stock-eps': stock.eps,
                          'data-stock-shares': stock.shares,
                          'data-stock-inst_own': stock.inst_own,
                          'data-stock-name': stock.name,
                          'data-stock-type': stock.type,
                        'data-target': '#stock',
                    })
                    .text('#' + stock.name + " " + stock.t + " " + stock.l)
                    .append($('<a>').attr({
                    'id': 'remove-stock-link',
                    'data-stock-t': stock.t,
                    'data-target' :'#remove-stock-link',
                    'onclick':  'removeStock(' + stock.t + ')'

                    }).text('remove')));
        }
    });
}

//$('#search-button').click(function (event) {
//    event.preventDefault();
//    $.ajax({
//            url: 'search/' + $('#search-symbol').val(),
//            type: 'GET',
//            async: false,
//            cache: false,
//            timeout: 30000,
//            error: function(){
//                return true;
//            },
//            success: function(msg){
//                if (false){
//                    return false;
//                } else {
//                    return true;
//                }
//            }
//        });
//         $.ajax({
//                    url: 'stock/' + $('#search-symbol').val(),
//                    type: 'POST',
//                    async: false,
//                    cache: false,
//                    timeout: 30000,
//                    error: function(){
//                        return true;
//                    },
//                    success: function(msg){
//                        if (false){
//                            return false;
//                        } else {
//                               //addStock(msg);
//                               //renderPortfolio(msg);
//                            return true;
//                        }
//                    }
//                });
//});

function add(){
var symbol = event.target.id;
$.ajax({
                    url: '/stock/' + symbol,
                    type: 'POST',
                    async: false,
                    cache: false,
                    timeout: 30000,
                    error: function(){
                        return true;
                    },
                    success: function(msg){
                        if (false){
                            return false;
                        } else {
                        refreshList();

                            return true;
                        }
                    }
                });
}

function quote(){
var symbol = event.target.id;
$.ajax({
                    url: '/quote/' + symbol,
                    type: 'POST',
                    async: false,
                    cache: false,
                    timeout: 30000,
                    error: function(){
                        return true;
                    },
                    success: function(msg){
                        if (false){
                            return false;
                        } else {
                        refreshList();
                            return true;
                        }
                    }
                });
}

//function addStock(stockList, status){
//$.each(stockList, function (index, stock) {
//        if (stock !== "null" && stock !== "") {
//            $('#test').append($('<div>')
//                    .attr({
//                           'id': 'stock',
//                           'data-stock-id': stock.id,
//                          'data-stock-t': stock.t,
//                          'data-stock-e': stock.e,
//                          'data-stock-l': stock.l,
//                          'data-stock-l_fix': stock.l_fix,
//                          'data-stock-l_cur': stock.l_cur,
//                          'data-stock-s': stock.s,
//                          'data-stock-ltt': stock.ltt,
//                          'data-stock-lt': stock.lt,
//                          'data-stock-lt_dts': stock.lt_dts,
//                          'data-stock-c': stock.c,
//                          'data-stock-c_fix': stock.c_fix,
//                          'data-stock-cp': stock.cp,
//                          'data-stock-cp_fix': stock.cp_fix,
//                          'data-stock-ccol': stock.ccol,
//                          'data-stock-pcls_fix': stock.pcls_fix,
//                          'data-stock-eo': stock.eo,
//                          'data-stock-delay': stock.delay,
//                          'data-stock-op': stock.op,
//                          'data-stock-hi': stock.hi,
//                          'data-stock-lo': stock.lo,
//                          'data-stock-vo': stock.vo,
//                          'data-stock-avvo': stock.avvo,
//                          'data-stock-hi52': stock.hi52,
//                          'data-stock-lo52': stock.lo52,
//                          'data-stock-mc': stock.mc,
//                          'data-stock-pe': stock.pe,
//                          'data-stock-fwpe': stock.fwpe,
//                          'data-stock-beta': stock.beta,
//                          'data-stock-eps': stock.eps,
//                          'data-stock-shares': stock.shares,
//                          'data-stock-inst_own': stock.inst_own,
//                          'data-stock-name': stock.name,
//                          'data-stock-type': stock.type,
//                        'data-target': '#stock',
//                    })
//                    .text("Name: " + stock.name + " | Ticker Symbol: " + stock.t + " | Last Price" + stock.l + " | High: " + stock.hi))
//                    ;
//        }
//    });
//}

//function renderPortfolio(msg, status){
// $('#portfolio').empty();
//$.each(msg, function (index, stock) {
//        if (stock !== "null" && stock !== "") {
//            $('#portfolio').append($('<div>')
//                    .attr({
//                           'id': 'stock',
//                           'data-stock-id': stock.id,
//                          'data-stock-t': stock.t,
//                          'data-stock-e': stock.e,
//                          'data-stock-l': stock.l,
//                          'data-stock-l_fix': stock.l_fix,
//                          'data-stock-l_cur': stock.l_cur,
//                          'data-stock-s': stock.s,
//                          'data-stock-ltt': stock.ltt,
//                          'data-stock-lt': stock.lt,
//                          'data-stock-lt_dts': stock.lt_dts,
//                          'data-stock-c': stock.c,
//                          'data-stock-c_fix': stock.c_fix,
//                          'data-stock-cp': stock.cp,
//                          'data-stock-cp_fix': stock.cp_fix,
//                          'data-stock-ccol': stock.ccol,
//                          'data-stock-pcls_fix': stock.pcls_fix,
//                          'data-stock-eo': stock.eo,
//                          'data-stock-delay': stock.delay,
//                          'data-stock-op': stock.op,
//                          'data-stock-hi': stock.hi,
//                          'data-stock-lo': stock.lo,
//                          'data-stock-vo': stock.vo,
//                          'data-stock-avvo': stock.avvo,
//                          'data-stock-hi52': stock.hi52,
//                          'data-stock-lo52': stock.lo52,
//                          'data-stock-mc': stock.mc,
//                          'data-stock-pe': stock.pe,
//                          'data-stock-fwpe': stock.fwpe,
//                          'data-stock-beta': stock.beta,
//                          'data-stock-eps': stock.eps,
//                          'data-stock-shares': stock.shares,
//                          'data-stock-inst_own': stock.inst_own,
//                          'data-stock-name': stock.name,
//                          'data-stock-type': stock.type,
//                        'data-target': '#stock',
//                    })
//                    .text("Name: " + stock.name + " | Ticker Symbol: " + stock.t + " | Last Price" + stock.l + " | High: " + stock.hi)
//                    .append($('<a>').attr({
//                                        'id': 'remove-stock-link',
//                                        'data-stock-t': stock.t,
//                                        'data-target' :'#remove-stock-link',
//                                        'onclick':  'removeStock(' + stock.t + ')'
//                                        }).text('remove')));
//        }
//    });
//}
function radio(){
var symbol = event.target.id;
   // alert(symbol);
  // $('#aa').toggleClass("aaa");
   //$('#aa').toggleClass("aaa");
 $("#aa").prop("class", "red-bg");
  // $('#aa').attr({"class":"aaa"});
  $("#descending").prop("checked", true);

}

//function addFunds(){
//   var amount = Number($("#funds-input").val());
//   //alert(amount);
//            $.ajax({
//                       url: '/funds/' + amount,
//                       type: 'POST',
//                       async: false,
//                       cache: false,
//                       timeout: 30000,
//                       error: function(){
//                           return true;
//                       },
//                       success: function(msg){
//                           if (false){
//                               return false;
//                           } else {
//                                  getFunds();
//                               return true;
//                           }
//                       }
//                   });
//
//}
