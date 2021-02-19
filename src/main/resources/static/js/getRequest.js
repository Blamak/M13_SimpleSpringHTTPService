$(document).ready(function() {
	ajaxGet(); // Build employees' table on first run
	
	$("#getAllCustomerId").click(function(event) {
		event.preventDefault();
		ajaxGet();
	});

	function ajaxGet() {
		$.ajax({
			type: "GET",
			url: "/employees",
			success: function(result) {
				
				if (result.status == "OK") {
					$('#list').append('<thead>' + '<tr> <th>Id</th> <th>Name</th> <th>Position</th>' + '<th> </th>' + '</thead>');
					
					$.each(result.data, function(index, employee) {
						//Build table row
						let row = $('<tr>').addClass("row");
						row.append($('<td>').append(employee.id));
						row.append($('<td>').append(`<input value=${employee.name}></input>`));
						row.append($('<td>').append(`<input value=${employee.position}></input>`));
						row.append($('<td>').append(`<button class="delete">Delete</button`).append(`<button class="update">Update</button`));

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
})