$(document).ready(function () {

    // get the server url
    let serverUrl = window.location.protocol + "//" + window.location.host + "/account/messages";

    let queryUserMessages = function () {

        $.ajax({
            method: 'GET',
            url: serverUrl,
            dataType: 'json',
            contentType: 'application/json',
            success: function (messages) {
                if (messages !== null) {
                    // display total unread messages.
                    if (messages.length > 0) {
                        $('#user-message-count').html(messages.length);

                        // clear the message list.
                        $('#user-messages').empty();

                        // display message items.
                        $.each(messages, function (index, item) {
                            let msgItem = `<li class="list-group-item d-flex justify-content-between align-items-center">${item.content} <a class="message-read" href="javascript:void(0)" data-id="${item.id}">&times;</a></li>`;
                            $('#user-messages').append(msgItem);
                        });
                    }
                }
            }, error: function (errors) {
                console.log(errors);
            }
        });
    };

    $('.dropdown-menu a.message-read').click(function (e) {

        console.log(1)
        e.stopPropagation();

    });

    // set message read.
    $(".message-read").click(function (event) {
        let id = $(this).attr('id');
        console.log(id);

        if (id !== null) {
            $.ajax({
                method: 'DELETE',
                url: serverUrl,
                dataType: 'json',
                data: {id: id},
                contentType: 'application/json',
                success: function () {
                    queryUserMessages();
                }, error: function (errors) {
                    console.log(errors);
                }
            });
        }
    });

    $(".custom-file-input").on("change", function() {
        let fileName = $(this).val().split("\\").pop();
        $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
    });

    // setup automatic get user messages every 3s.
    setInterval(queryUserMessages, 10000);

});