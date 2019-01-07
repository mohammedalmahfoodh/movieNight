
const CLIENT_ID = "552100520661-javvlc9vbqenon4daiqam1d409pco69q.apps.googleusercontent.com";





function start() {

    gapi.load('auth2', function() {

        auth2 = gapi.auth2.init({

            client_id: CLIENT_ID,

            scope: "https://www.googleapis.com/auth/calendar.events"

        });

    });

}



$('#signinButton').click(function() {

// signInCallback defined in step 6.

    auth2.grantOfflineAccess().then(signInCallback);

});



function signInCallback(authResult) {

    console.log('authResult', authResult);

    if (authResult['code']) {



        // Hide the sign-in button now that the user is authorized, for example:

        $('#signinButton').attr('style', 'display: none');



        // Send the code to the server

        $.ajax({

            type: 'POST',

            url: 'https://localhost:8080/storeauthcode',

            // Always include an `X-Requested-With` header in every AJAX request,

            // to protect against CSRF attacks.

            headers: {

                'X-Requested-With': 'XMLHttpRequest'

            },

            contentType: 'application/octet-stream; charset=utf-8',

            success: function(result) {

                // Handle or verify the server response.

            },

            processData: false,

            data: authResult['code']

        });

    } else {

        // There was an error.

    }

}