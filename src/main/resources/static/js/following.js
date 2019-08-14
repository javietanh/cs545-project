$(document).ready(function () {
   let loadFollowing = function () {
       $.ajax({
           method: 'GET',
           url: '/buyer/followings',
           dataType: 'json',
           success: function (sellers) {
               $('#seller').html("");
               $.each(sellers, function (i, seller) {
                   $("#seller").append('<tr><td>' + seller.name + '</td>' +
                   '<td>' + seller.phone + '</td>' +
                   '<td>' + seller.email + '</td>' +
                   '<td>' + seller.address + '</td>' +
                   '<td><button class="unfollow btn btn-primary" data-id="' + seller.id + '">Unfollow</button></td></tr>');
               });
           },
           error: function () {
               alert('Error while request..');
           }
       });
   };

    loadFollowing();

   $(document).on('click', '.unfollow', function(){
        var sellerId = $(this).data("id");
        $.ajax({
            url: '/buyer/following/unfollow/' + sellerId,
            type: 'DELETE',
            contentType: "application/json",
            dataType: "json",
            success: function (response) {
                loadFollowing();
            },
            error: function(error){
                console.log(error);
            }
        });
   });

});


