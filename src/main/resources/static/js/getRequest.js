$(document).ready(function() {
    console.log("ready")
    ajaxGet();
    // GET REQUEST
    $("#getAllCustomerId").click(function(event) {
        event.preventDefault();
        ajaxGet();
    });

    // DO GET
    function ajaxGet() {
        $.ajax({
            type: "GET",
            url: "/employees",
            success: function(result) {
                //                console.log(result)
                // if(result.status == "Done"){
                //$('#getResultDiv ul').empty();
                //var custList = "";
                $('#list').append('<thead>' + '<tr> <th>Id</th> <th>Name</th> <th>Position</th>' + '<th> </th>' + '</thead>')
                // $('#test >thead tr').append('<th> Column 4</th>');
                $.each(result, function(index, employee) {

                    let row = $('<tr>').addClass("row");
                    row.append($('<td>').append(employee.id))
                    row.append($('<td>').append(`<input value=${employee.name}></input>`));
                    row.append($('<td>').append(`<input value=${employee.position}></input>`));
                    row.append($('<td>').append(`<button class="delete">Delete</button`).append(`<button class="update">Update</button`));
                   
                        // row.append( $('<td>').text(`<button class="update">Update</button`));
                        //row.append( $('<td>').text(employee.position) );
                        // let data_name = $('td');

                        // data_name.text(employee.name);
                        // row.append(data_name);
                        $('#list').append(row);

                        /* let empLine = "- Customer with Id = " + employee.id + ", firstname = " + employee.name;
                         empLine += ", lastName = " + employee.position;
                         empLine += `<button id="delete" style="margin-left: 20px" type="button">Delete</button>`;
                         empLine += `<button id="update" style="margin-left: 20px" type="button">Update</button>`;
                         empLine += "<br><br>";*/

                        // $('#getResultDiv .list-group').append(empLine)
                    });

                    console.log("Success: ", result);
                    /*}else{
                        $("#getResultDiv").html("<strong>Error</strong>");
                        console.log("Fail: ", result);
                    }*/
                },
                    error: function(e) {
                        console.log("yeyeyerrrrorr")
                        $("#getResultDiv").html("<strong>Error</strong>");
                        console.log("ERROR: ", e);
                    }
        });
    }
})