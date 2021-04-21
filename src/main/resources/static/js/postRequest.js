$(function() {
	// SUBMIT FORM
	$("#submitButton").click(function(event) {
		// Prevent the form from submitting via the browser.
		event.preventDefault();
		ajaxPost();
	});

	function ajaxPost() {
		try {
			const name = $("#name").val();
			const position = $("#position").val();
			
			// Empty field validation
			if (name.trim() == '' || position.trim() == '') {
				throw " Name and Position are mandatory.";
			}

			const formData = {
				name: name,
				position: position
			}
			
			$.ajax({
				type: "POST",
				url: "/employees/",
				data: JSON.stringify(formData),
				contentType: "application/json; charset=utf-8",
				dataType: "json",
				success: function(result) {
					
					if (result.status == "OK") {
						alert("Post Successfully!" + "  ---> New Employee's Info:"
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
			});

		} catch (e) {
			alert(e);
		}
	}
})