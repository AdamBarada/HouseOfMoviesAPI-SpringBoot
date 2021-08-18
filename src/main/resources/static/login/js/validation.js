$(function() {
    $(".login-form").validate({
        rules: {
            email: {
                required: true,
                email: true,
            },
            password: {
                required: true,
            },
        },
        messages: {
            email: {
                required: 'Please enter an email address.',
                email: 'Please enter a valid email address.',
            },
            password: {
                required: 'Please enter your password <br>',
            }
        },
    });

    $(".signup-form").validate({
        rules: {
            email: {
                required: true,
                email: true,
            },
            password: {
                required: true,
            },
            fname: {
                required: true,
            },
            lname: {
                required: true,
            },
        },
        messages: {
            email: {
                required: 'Please enter an email address.',
                email: 'Please enter a valid email address.',
            },
            password: {
                required: 'Please enter your password',
            },
            fname: {
                required: 'First name is required',
            },
            lname: {
                required: 'Last name is required',
            }
        }
    });
});