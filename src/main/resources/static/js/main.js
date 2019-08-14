$(document).ready(function () {

    // get the server url
    let serverUrl = window.location.protocol + "//" + window.location.host;

    let queryUserMessages = function () {

        $.ajax({
            method: 'GET',
            url: serverUrl + "/account/messages",
            dataType: 'json',
            contentType: 'application/json',
            success: function (messages) {
                if (messages !== null) {
                    if (messages.length > 0) {
                        $('#user-message-count').html(messages.length).show();
                        let messageHtml = '';
                        $.each(messages, function (index, item) {
                            messageHtml += `<div class="alert alert-warning alert-dismissible fade show" role="alert">
                                              ${item.content}
                                              <button title="Ignored" type="button" class="close message-read" data-id="${item.id}" data-dismiss="alert" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                              </button>
                                            </div> `;
                        });
                        $('#user-messages').empty().html(messageHtml);
                    }
                    else{
                        $('#user-message-count').hide();
                    }
                }else{
                    $('#user-message-count').hide();
                }
            }, error: function (errors) {
                console.log(errors);
            }
        });
    };
    queryUserMessages();

    $(document).on('click', '.message-read', function (event) {

        $.ajax({
            method: 'DELETE',
            url: serverUrl + "/account/messages/read/" + $(this).data('id'),
            contentType: 'application/json',
            success: function () {
                queryUserMessages();
            }, error: function (errors) {
                console.log(errors);
            }
        });
    });

    $(".custom-file-input").on("change", function () {
        let fileName = $(this).val().split("\\").pop();
        $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
    });

    // setup automatic get user messages every 3s.
    setInterval(queryUserMessages, 10000);

    let loadShoppingCart = function () {
        $.ajax({
            method: 'GET',
            url: "/buyer/shoppingCart",
            dataType: 'json',
            success: function (items) {
                if (items.length > 0) {
                    $('#cart-item-count').html(items.length);
                    let itemHtml = '';
                    $.each(items, function (i, item) {
                        itemHtml += `<tr>
                                        <td style="width: 100px">
                                            <a href="/product/${item.id}"><img src="${item.picture}" class="border-0 rounded-circle img-fluid img-thumbnail w-75" /></a>
                                        </td>
                                        <td>
                                            <div class="row">
                                                <span class="text-info font-italic"><a href="/product/${item.id}">${item.productName}</a></span>
                                            </div>
                                            <div class="row">
                                                <span>$${item.productPrice}</span>
                                            </div>
                                        </td>                                        
                                    </tr> `;
                    });
                    $("#shopping-cart-items").empty().append(itemHtml);
                }
            }, error: function (errors) {
                console.log(errors);
            }
        });
    };

    loadShoppingCart();

    // setup gridView
    $('#grid').DataTable({
        "autoWidth": true,
        "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]]
    });

    $("#followBtn").click(function() {
        let action = $("#followBtn").text();
        let sellerId = $("#sellerId").val();

        $.ajax({
            method: 'POST',
            url: serverUrl + "/buyer/follow/"+ action + "/" + sellerId,
            dataType: 'json',
            contentType: 'application/json',
            success: function (result) {
                if(result){
                $("#followBtn").html('Unfollow');

                } else {
                $("#followBtn").html('Follow');

                }

            }, error: function (errors) {
                console.log(errors);
            }
        });
    });

});