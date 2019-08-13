(function ($) {
    $.fn.serializeFormJSON = function () {
        var o = {};
        var a = this.serializeArray();
        $.each(a, function () {
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };
})(jQuery);

$(document).ready(function () {
   let loadCart = function () {
       $.ajax({
           method: 'GET',
           url: '/buyer/shoppingCart',
           dataType: 'json',
           success: function (cart) {
               $('#cart-item').html("");
               $.each(cart, function (i, item) {
                   $("#cart-item").append('<tr><td>' + item.productName + '</td>' +
                   '<td>' + item.productPrice + '</td>' +
                   '<td>' + item.quantity + '</td>' +
                       '<td><a href="javascript:void(0)" class="remove-item" data-id="' + item.id + '">Remove</a></td></tr>');
               });
           },
           error: function () {
               alert('Error while request..');
           }
       });
   };

   loadCart();

   $(document).on('click', '.remove-item', function(){
        console.log('call remove function');
        var itemId = $(this).data("id");
       console.log('call remove function' + itemId);
        $.ajax({
            url: '/cart/remove/' + itemId,
            type: 'DELETE',
            contentType: "application/json",
            dataType: "json",
            success: function (response) {
                loadCart();
            },
            error: function(error){
                console.log(error);
            }
        });
   });

   function usePoints () {
       let data = JSON.stringify($("#employeeForm").serializeFormJSON());
       $.ajax({

       });
   }
});



// function usePoints(){
//     // let data = serializeObject($("#employeeForm"));
//     let data = JSON.stringify($("#employeeForm").serializeFormJSON());
//
//     $.ajax({
//         type: "POST",
//         url: "http://localhost:8888/api/addEmployee",
//         data: data,
//         contentType: "application/json",
//         dataType: "json",
//         success: function(data) {
//             $('#formInput').html("");
//             $("#formInput").append( '<h3 align="center"> New Employee Information <H3>');
//             $('#formInput').append("<h4 align='center'>First Name:  " + data.firstName  + "</h4>"  );
//             $('#formInput').append("<h4 align='center'>Last Name: " +  data.lastName + "</h4>" );
//             $('#formInput').append("<h4 align='center'>Email: " + data.email  + "</h4>");
//             $("#formInput").append('<h4 align="center"> <a href="#" onclick="toggle_visibility(\'formInput\');resetForm(\'formInput\');"><b>EXIT</b> </a> </h4>');
//             make_visible('formInput');
//             make_hidden('errors');
//         },
//
//         error: function(XMLHttpRequest){
//             console.log(XMLHttpRequest.responseJSON);
//             $("#errors").empty();
//             if (XMLHttpRequest.responseJSON.errorType == "ValidationError") {
//                 let errorMsg = '<h3> Error(s)!! </h3>';
//                 errorMsg += "<p>";
//                 var errorList = XMLHttpRequest.responseJSON.errors;
//                 $.each(errorList, function (i, error) {
//                     errorMsg = errorMsg + error.message + '<br>';
//                 });
//                 errorMsg += '</p';
//                 $('#errors').append(errorMsg);
//                 make_hidden('formInput');
//                 make_visible('errors');
//             } else {
//                 alert(errorObject.responseJSON.errors(0));
//             }
//
//         }
//
//     });
// }