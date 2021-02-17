$(function() {

	// SUBMIT FORM
	$("#submitButton").click(function(event) {
		// Prevent the form from submitting via the browser.
		event.preventDefault();
		ajaxPost();
	});


	function ajaxPost() {

		// PREPARE FORM DATA
		var formData = {
			name: $("#name").val(),
			position: $("#position").val()
		}

		// DO POST
		$.ajax({
			type: "POST",
			url: '/save',
			data: JSON.stringify(formData),
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function(result) {
				if (result.status == "OK") {
					alert("Post Successfully!" + "  ---> New Employee's Info:"
					                           + "\nName = " +result.data.name
                                               + " , Position = " + result.data.position)
					location.reload();
				} else {
					console.log("Fail: " +result)
				}
			},
			error: function(e) {
				alert("Error!")
				console.log("ERROR: ", e);
			}
		});
	}
})