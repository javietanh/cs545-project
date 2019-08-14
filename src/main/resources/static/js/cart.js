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
                   '<td><button class="remove-item btn btn-primary" data-id="' + item.id + '">Remove</button></td></tr>');
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

});
