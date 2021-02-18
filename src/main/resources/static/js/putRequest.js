$(document).on("click", ".update", function() {
	try {

		const id = $(this).closest('tr').find("th:first").text();
		const name = $(this).closest('tr').find("td:nth-child(2) input").val();
		const position = $(this).closest('tr').find("td:nth-child(3) input").val();

		if (name.trim() == '' || position.trim() == '') {
			throw " Name and Position cannot be empty.";
		}

		const formData = {
			id: id,
			name: name,
			position: position
		}

		$.ajax({
			type: "PUT",
			url: "/employees/" + id,
			data: JSON.stringify(formData),
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function(result) {
				if (result.status == "OK") {
					alert("Put Successfully!" + "  ---> Employee's New Info:"
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

	} catch (e) {
		alert(e);
	}
});

