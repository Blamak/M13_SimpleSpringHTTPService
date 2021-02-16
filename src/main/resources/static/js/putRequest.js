$(document).on("click", ".update", function() {
    const id = $(this).closest('tr').find("td:first").text();
    const name = $(this).closest('tr').find("td:nth-child(2) input").val();
    const position = $(this).closest('tr').find("td:nth-child(3) input").val();

    var formData = {
        id: id,
        name: name,
        position: position
    }

    $.ajax({
        type: "PUT",
        url: "/employees/" + id,
        data: JSON.stringify(formData),
        contentType: "application/json; charset=utf-8",
        dataType: "text",
        success: function(result) {
            console.log(result)
            window.location.href = "http://localhost:8080/";

        }
    })

})

