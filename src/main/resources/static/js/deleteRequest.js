$(document).on("click", ".delete", function() {
    const id = $(this).closest('tr').find("td:first").text();
    ajaxDelete(id);
});

function ajaxDelete(id) {

    $.ajax({
        type: "DELETE",
        url: "/employees/" + id,
        success: function(result) {
             window.location.href = "http://localhost:8080/";

        }
    })
}



