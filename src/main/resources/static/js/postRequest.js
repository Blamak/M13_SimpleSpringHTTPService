$(function() {

    // SUBMIT FORM
    $("#submitButton").click(function(event) {
        console.log("submit")
        console.log($('#name').val())
        console.log($('#position').val())
         var formData = {
            name: $("#name").val(),
            position: $("#position").val()
        }
        console.log(JSON.stringify(formData))
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
            dataType: "text",
            success: function(result) {
                console.log("success")
                window.location.href = "http://localhost:8080/";
                /* if(result.status == "Done"){*/
                /* $("#postResultDiv").html("<p style='background-color:#7FA7B0; color:white; padding:20px 20px 20px 20px'>" + 
                                             "Post Successfully! <br>" +
                                             "---> Customer's Info: FirstName = " + 
                                             result.data.firstname + " ,LastName = " + result.data.lastname + "</p>");*/
                /*}else{
                    $("#postResultDiv").html("<strong>Error</strong>");
                }*/

                //console.log(result);
            },
            error: function(e) {
                alert("Error!")
                console.log("ERROR: ", e);
            }
        });

        // Reset FormData after Posting
        //resetData();
        //window.location

    }


    function resetData() {
        $("#name").val("");
        $("#position").val("");
    }

})