$(document).ready(function() {
	ajaxGet(); // Build employees' table on first run
})

function ajaxGet() {
	$.ajax({
		type: "GET",
		url: "/employees",
		success: function(result) {
			if (result.status == "OK") {
				$('#list').append('<tbody>');
				$.each(result.data, function(index, employee) {
					let row = $('<tr>');
					row.append($(`<th scope="row">`).append(employee.id))
					row.append($('<td>').append(`<input type="text" value=${employee.name}>`));
					row.append($('<td>').append(`<input type="text" value=${employee.position}>`));
					row.append($('<td>').append(`<button class="delete btn btn-outline-danger">Delete</button`));
					row.append($('<td>').append(`<button class="update btn btn-outline-primary">Update</button`));

					$('#list').append(row);
				});
				console.log("Success: ", result);
			} else {
				console.log("Fail: ", result);
			}
		},
		error: function(e) {
			console.log("ERROR: ", e);
		}
	});
}