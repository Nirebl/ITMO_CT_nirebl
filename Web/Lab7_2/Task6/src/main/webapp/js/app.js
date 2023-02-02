window.notify = function (message) {
    $.notify(message, {
        position: "right bottom",
        className: "success"
    });
}

window.lab7_ajax = function (data, success) {
    $.ajax({
        type: "POST",
        dataType: "json",
        data: data,
        success: function (response) {
            if (response["redirect"] !== undefined) location.href = response["redirect"];
            else {
                success(response);
                if (response["message"])
                    notify(response["message"]);
            }
        }
    });
};