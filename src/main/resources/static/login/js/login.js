$(function() {
    var tab = $('.tabs h3 a');
    tab.on('click', function(event) {
        event.preventDefault();
        tab.removeClass('active');
        $(this).addClass('active');
        tab_content = $(this).attr('href');
        $('div[id$="tab-content"]').removeClass('active');
        $(tab_content).addClass('active');
    });
});

// SLIDESHOW
$(function() {
    $('#slideshow > div:gt(0)').hide();
    setInterval(function() {
        $('#slideshow > div:first')
            .fadeOut(1000)
            .next()
            .fadeIn(1000)
            .end()
            .appendTo('#slideshow');
    }, 3850);
});

(function($) {
    'use strict';
    $.fn.swapClass = function(remove, add) {
        this.removeClass(remove).addClass(add);
        return this;
    };
}(jQuery));

$(function() {
    $('.button').on('click', function(event) {
        $(this).stop();
        event.preventDefault();
        return false;
    });
});

$(function() {
    $('#sign-up-button').on('click', function(event) {
        if ($(".signup-form").valid()) {
            console.log("entered!");
            signup();
        }
    });
});


$(function() {
    $('#sign-in-button').on('click', function(event) {
        var jsonData = {
            "email": $('#user_login').val(),
            "password": $('#user_pass_login').val()
        };
        if ($(".login-form").valid()) {
            login(jsonData);
        }
    });
});

window.onload = function() {
    if (sessionStorage["token"] || localStorage["token"])
        window.location = "/"
    else {
        sessionStorage.removeItem("token");
        sessionStorage.removeItem("token");
    }

};

$(document).ready(function() {

    $("#cover").fadeOut(1000);
});


function login(jsonData) {
    $.ajax({
        url: 'http://localhost:8080/token/generate-token',
        contentType: 'application/json',
        type: 'POST',
        crossDomain: true,
        dataType: 'json',
        data: JSON.stringify(jsonData),
        success: function(result) {
            if ($('#remember_me').is(":checked"))
                localStorage.setItem('token', result["token"]);
            else
                sessionStorage.setItem('token', result["token"]);
            window.location = '/';
        },
        error: function() {
            swal({
                title: "Invalid Credentials.",
                icon: "error",
                timer: 3000,
                button: true,
                dangerMode: true,
            })
        }
    });
}

function signup() {
    var jsonData = {
        "firstName": $('#user_fname').val(),
        "lastName": $('#user_lname').val(),
        "email": $('#user_email').val(),
        "password": $('#user_pass').val()
    };
    $.ajax({
        url: 'http://localhost:8080/public/request/sign-up',
        contentType: 'application/json',
        type: 'POST',
        crossDomain: true,
        dataType: 'json',
        data: JSON.stringify(jsonData),
        success: function(result) {
            console.log(result);
            var jsonDataLogin = {
                "email": $('#user_email').val(),
                "password": $('#user_pass').val()
            };
            login(jsonDataLogin);
        },
        error: function(err) {
            if (err.status == 409) {
                swal({
                    title: "Email already in use.",
                    text: "Sign in instead?",
                    icon: "error",
                    timer: 3000,
                    button: true,
                    dangerMode: true,
                })
            }
            console.log(err.status);
        }
    });
}