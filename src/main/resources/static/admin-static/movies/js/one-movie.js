var movieId;
var res;
var categories;

$("#resetButton").click(function() {
    $("#title").val("");
    $("#director").val("");
    $("#duration").val("");
    $("#releaseDate").val("");
    $("#cast").val("");
    $("#description").val("");
    $("#category").val(0);
    $("#image").val(null);
    $("#landscape").val(null);
});


$("#my-form").submit(function(e) {
    e.preventDefault();
    var urls, method;
    movieId = window.location.href.substring(window.location.href.lastIndexOf('/') + 1);
    token = (localStorage["token"]) ? localStorage["token"] : sessionStorage["token"];
    if (movieId == "add-movie") {
        urls = "";
        method = "POST";
    } else {
        urls = "/" + movieId;
        method = "PUT";
    }
    var form = new FormData($("#my-form")[0]);
    if (token) {
        $.ajax({
            url: 'http://localhost:8080/admin/request/movies' + urls,
            type: method,
            headers: { 'Authorization': 'Bearer ' + token },
            data: form,
            cache: false,
            contentType: false,
            processData: false,
            success: function(result) {
                console.log(result);
            },
            error: async function(error) {
                console.log(error);
                if (error.status == 404)
                    window.location = 'http://localhost:8080/error/notFound';
                else if (error.status == 400)
                    console.log("Catched");
                else
                    window.location = 'http://localhost:8080/error/internalError';

            }
        });
    } else {
        $.ajax({
            url: 'http://localhost:8080/admin/request/movies' + urls,
            type: method,
            data: form,
            cache: false,
            contentType: false,
            processData: false,
            success: function(result) {
                console.log(result);
            },
            error: function(error) {
                console.log(error);
                if (error.status == 401 || error.status == 403)
                    window.location = 'http://localhost:8080/error/accessDenied';
                else if (error.status == 404)
                    window.location = 'http://localhost:8080/error/notFound';
                else
                    window.location = 'http://localhost:8080/error/internalError';

            }
        });
    }
});

$(document).ready(function() {
    $.ajax({
        url: 'http://localhost:8080/public/request/categories',
        type: 'GET',
        contentType: 'application/json',
        success: function(result) {
            categories = result;
            console.log(categories);
            categoriesLoaded();
        },
        error: function(error) {
            console.log(error);
            if (error.status == 404)
                window.location = 'http://localhost:8080/error/notFound';
            else
                window.location = 'http://localhost:8080/error/internalError';
        }
    });
});

function categoriesLoaded() {
    for (i = 0; i < categories.length; i++) {
        $("#category").append("<option value='" + categories[i]["id"] + "'>" + categories[i]["name"] + "<option>");
    }
    movieId = window.location.href.substring(window.location.href.lastIndexOf('/') + 1);
    if (movieId != "add-movie") {
        token = (localStorage["token"]) ? localStorage["token"] : sessionStorage["token"];
        if (token) {
            $.ajax({
                url: 'http://localhost:8080/admin/request/movies/' + movieId,
                type: 'GET',
                contentType: 'application/json',
                headers: { 'Authorization': 'Bearer ' + token },
                success: function(result) {
                    res = result;
                    console.log(res);
                    pageLoaded();
                },
                error: function(error) {
                    console.log(error);
                    if (error.status == 404)
                        window.location = 'http://localhost:8080/error/notFound';
                    else
                        window.location = 'http://localhost:8080/error/internalError';
                }
            });
        } else {
            $.ajax({
                url: 'http://localhost:8080/admin/request/movies/' + movieId,
                type: 'GET',
                contentType: 'application/json',
                success: function(result) {
                    res = result;
                    console.log(res);
                    pageLoaded();
                },
                error: function(error) {
                    if (error.status == 401 || error.status == 403)
                        window.location = 'http://localhost:8080/error/accessDenied';
                    else if (error.status == 404)
                        window.location = 'http://localhost:8080/error/notFound';
                    else
                        window.location = 'http://localhost:8080/error/internalError';
                }
            });
        }
    } else {
        $("#movieImage").remove();
        $("#movieLandscape").remove();
        $('#cover').fadeOut(1000);
    }

}

function pageLoaded() {
    $("#movieImage").attr("src", "http://localhost:8080/" + res["image"]);
    $("#movieLandscape").attr("src", "http://localhost:8080/" + res["landscape"]);
    $("#titleBig").append(" : " + res["title"]);
    $("#title").val(res["title"]);
    $("#director").val(res["director"]);
    $("#duration").val(res["duration"]);
    $("#releaseDate").val(res["releaseDate"]);
    $("#cast").append(res["cast"]);
    $("#description").append(res["description"]);
    $("#category").val(res["category"]["id"]);
    $('#cover').fadeOut(1000);
}