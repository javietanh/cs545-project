$(document).ready(function () {
   let loadCart = function () {
       $.ajax({
           method: 'GET',
           url: '/buyer/cart',
           dataType: 'json',
           success: function (cart) {
               $('#cart-item').html("");
               $.each(cart, function (i, item) {
                   $("#cart-item").append('<td>' + item.product.name + '</td>' +
                   '<td>' + item.product.price + '</td>' +
                   '<td>' + item.quantity + '</td>' +
                       '<td><a href="#" class=\'remove-item\' data-id="' + item.id + '">Remove</a></td>');
               });
           },
           error: function () {
               alert('Error while request..');
           }
      });
   };
    $('.remove-item').click(function(event){
        event.preventDefault();
        var itemId = $(this).attr("data");
        $.ajax({
            url: '/cart/remove/',
            type: 'DELETE',
            dataType: "json",
            data: { id: itemId },
            success: function (response) {
                loadCart();
            },
            error: function(){
                alert('Error while request..');

            }
        });
    });
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