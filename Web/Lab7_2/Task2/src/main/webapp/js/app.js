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
        success: function(response) {
            success(response);
            if (response["redirect"] !== undefined) location.href = response["redirect"];
        }
    });
};