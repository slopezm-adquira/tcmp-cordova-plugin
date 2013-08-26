$(document).ready(function(){
  $(".alert").hide();
  $("#div-debug").hide();

  $('#form-login').submit(function(argument) {
    $("#btn-submit").hide();
    $("#alert-connecting").show();

    var user = $("#txtUser").val();
    var password = $("#txtPassword").val();
    app.tcmLogin(user,password,function (message) {
      alert(message);
      $("#alert-connecting").hide();
      $("#btn-submit").show();
    }, function (message) {
      alert("Error" + message);
      $("#alert-connecting").hide();
      $("#btn-submit").show();
    });

    return false;
  });

  $('#form-payment').submit(function(argument) {
    $("#btn-payment").hide();
    $("#alert-connecting").show();

    var server = $("#txtServer").val();
    var amount = $("#txtAmount").val();
    var concept = $("#txtConcept").val();
    var orderid = $("#txtOrderId").val();
    var service = $("#txtService").val();
    var token = $("#txtToken").val();

    $.ajax({
      type: "POST",
      accepts: "application/x-javascript",
      url: server+"/payment/emv",
      data: {
        amount: amount,
        concept: concept,
        orderid: orderid,
        service: service,
        token: token
      },
      dataType: "jsonp",
      success: function(data) {
        console.log(data);
        $("#div-debug").show();
        $("#output").html(JSON.stringify(data));
        $("#alert-connecting").hide();
        $("#btn-payment").show();
      },
      error: function(data) {
        $(".alert").hide();
        $("#alert-wrong").show();
        $("#btn-payment").show();
      }
    });
    return false;
  });

});
