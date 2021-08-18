var counter = 0;
var total = 0;
var res = null;

$(document).ready(function() {
    $.ajax({
        url: 'http://localhost:8080/public/request/movies',
        contentType: 'application/json',
        type: 'GET',
        crossDomain: true,
        dataType: 'json',
        success: function(result) {
            total = result.length;
            res = result;
            pageLoaded();
        },
        error: function(err) {
            console.log("entered error");
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
    var category = new Array();
    res.forEach(element => {
        category[parseInt(element.category.id - 1)] = category[parseInt(element.category.id - 1)] || [];
        category[parseInt(element.category.id - 1)].push(element);
    });
    var list = "";
    list = '<section id="latest"><h2 class="latest-heading" id="searchResult"></h2><ul id="autoWidthSearch" ></ul></section>';
    $("#allPage").append(list);
    for (i = 0; i < category.length; i++) {
        list = '<section id="latest"><h2 class="latest-heading">' + category[i][0]["category"]["name"] + '</h2><ul id="autoWidth' + i + '" >';
        for (j = 0; j < category[i].length; j++) {
            var index = category[i][j]["title"].length;
            var title = category[i][j]["title"];
            if (index > 23) {
                title = category[i][j]["title"].substring(0, 20) + '...';
            }
            list += '<li><div class="latest-box" onclick="window.location=\'/movies/' + category[i][j]["id"] + '\';"><div class="latest-b-img"><img src="' + category[i][j]["image"] + '"></div><div class="latest-b-text"><strong>' + title + '</strong><p>' + category[i][j]["category"]["name"] + '</p></div></div></li>';
        }
        list += '</ul></section>';
        $("#allPage").append(list);
        list = "";
    }
    for (i = 0; i < category.length; i++) {
        $('#autoWidth' + i).lightSlider({
            autoWidth: true,
            loop: true,
            onSliderLoad: function() {
                $('#autoWidth' + i).removeClass('cS-hidden');
            }
        });
    }
    displayQuery();
    $('#cover').fadeOut(1000);
}

function displayQuery() {
    var list = "";
    var resultFound = false;
    console.log("Entered!");
    var query = localStorage["queryVal"];
    console.log(query);
    if (query) {
        $("#searchResult").append("Search result for " + query + " : ");
        res.forEach(element => {
            if (element.title.toLowerCase().includes(query.toLowerCase())) {
                resultFound = true;
                var index = element["title"].length;
                var title = element["title"];
                if (index > 23) {
                    title = element["title"].substring(0, 20) + '...';
                }
                list = '<li><div class="latest-box" onclick="window.location=\'/movies/' + element["id"] + '\';"><div class="latest-b-img"><img src="' + element["image"] + '"></div><div class="latest-b-text"><strong>' + title + '</strong><p>' + element["category"]["name"] + '</p></div></div></li>';
                $("#autoWidthSearch").append(list);
                list = "";
            }
        });
        if (!resultFound) {
            $("#searchResult").append("<br>No results Found");
        } else {
            $('#autoWidthSearch').lightSlider({
                autoWidth: true,
                loop: true,
                onSliderLoad: function() {
                    $('#autoWidthSearch').removeClass('cS-hidden');
                }
            });
        }
        localStorage.removeItem("queryVal");
    }
};