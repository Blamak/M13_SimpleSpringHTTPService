$(document).on("click", ".delete", function() {
	// Store the id of the current row
	const id = $(this).closest('tr').find("td:first").text();
	ajaxDelete(id);
});


function ajaxDelete(id) {

	$.ajax({
		type: "DELETE",
		url: "/employees/" + id,
		contentType: "application/json; charset=utf-8",
		dataType: "json",
		success: function(result) {
			if (result.status == "OK") {
				alert("Deleted Successfully!" + "  ---> Erased Employee's Info:"
					+ "\nName = " + result.data.name
					+ " , Position = " + result.data.position)
				location.reload();
			} else {
				console.log("Fail: " + result)
			}
		},
		error: function(e) {
			alert("Error!")
			console.log("ERROR: ", e);
		}
	
    })
}



