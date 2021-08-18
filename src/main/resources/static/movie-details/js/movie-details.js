var movieId;
var res;

$(document).ready(function() {
    movieId = window.location.href.substring(window.location.href.lastIndexOf('/') + 1);
    $.ajax({
        url: 'http://localhost:8080/public/request/movies/' + movieId,
        contentType: 'application/json',
        type: 'GET',
        crossDomain: true,
        dataType: 'json',
        success: function(result) {
            res = result;
            console.log(result);
            pageLoaded();
        },
        error: function(err) {
            if (err.status == 404) {
                console.log("entered");
                window.location = "/error/notFound";
            } else {
                window.location = "/error";
            }
        }
    });
});


function pageLoaded() {
    $('#movieImage').attr("src", "http://localhost:8080/" + res["landscape"]);
    $('#title').append(res["title"]);
    $('#date').append('<b>Release Date: </b> ' + res["releaseDate"]);
    $('#description').append('<b>Description:</b> ' + res["description"]);
    $('#runtime').append('<b>Runtime: </b> ' + res["duration"] + ' mins');
    $('#genres').append('<b>Genres:</b> ' + res["category"]["name"]);
    $('#director').append(' <b>Director: </b> ' + res["director"]);
    $('#cast').append(' <b>Cast: </b> ' + res["cast"]);
    $('#cover').fadeOut(1000);
}

function goToScreenings() {
    localStorage.setItem("searchQuery", res["title"]);
}