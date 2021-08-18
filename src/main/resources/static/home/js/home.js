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
            console.log(res);
            pageLoaded();
        },
        error: function(err) {
            console.log("Error: " + err);
        }
    });
});

function pageLoaded() {
    var list = "";
    for (i = 0; i < 8; i++) {
        list = '<li><div class="showcase-box" onclick="window.location=\'/movies/' + res[i]["id"] + '\';"><img src="http://localhost:8080/' + res[i]["landscape"] + '"/></div></li>';
        $("#autoWidth").append(list);
        list = "";
    }

    var arr = [];
    while (arr.length < 12) {
        var r = Math.floor(Math.random() * res.length);
        if (arr.indexOf(r) === -1)
            arr.push(r);
    }

    for (j = 0; j < 12; j++) {
        var i = arr[j];
        var index = res[i]["title"].length;
        var title = res[i]["title"];
        if (index > 24) {
            title = res[i]["title"].substring(0, 20) + ' ...';
        }
        list = '<li><div class="latest-box" onclick="window.location=\'/movies/' + res[i]["id"] + '\';"><div class="latest-b-img"><img src="http://localhost:8080/' + res[i]["image"] + '"></div><div class="latest-b-text" id=' + res[i]["id"] + '><strong>' + title + '</strong><p>' + res[i]["category"]["name"] + '</p></div></div></li>';
        $("#autoWidth2").append(list);
        list = "";
    }

    for (i = 0; i < 8; i++) {
        var index = res[i]["title"].length;
        var title = res[i]["title"];
        if (index > 30) {
            title = res[i]["title"].substring(0, 26) + ' ...';
        }
        list = '<div class="movies-box"><div class="movies-img"><div class="quality">HDRip</div><img src="http://localhost:8080/' + res[i]["image"] + '"></div><a href="/movies/' + res[i]["id"] + '">' + title + '<br>Full Movie [In English] With English/Arabic Subtitles | HDRip 1080p </a></div>';
        $("#movies-list").append(list);
        list = "";
    }

    $('#autoWidth,#autoWidth2').lightSlider({
        autoWidth: true,
        loop: true,
        onSliderLoad: function() {
            $('#autoWidth').removeClass('cS-hidden');
            $('#autoWidth2').removeClass('cS-hidden');
        }
    });
    $('#cover').fadeOut(1000);
}

$("#previous").click(function() {
    var list = "";
    if (counter != 0) {
        counter--;
        var index = counter * 8;
        $("#movies-list").empty();
        for (i = index; i < index + 8; i++) {
            var index2 = res[i]["title"].length;
            var title = res[i]["title"];
            if (index2 > 30) {
                title = res[i]["title"].substring(0, 26) + ' ...';
            }
            list = '<div class="movies-box"><div class="movies-img"><div class="quality">HDRip</div><img src="http://localhost:8080/' + res[i]["image"] + '"></div><a href="/movies/' + res[i]["id"] + '">' + title + '<br>Full Movie [In English] With English/Arabic Subtitles | HDRip 1080p </a></div>';
            $("#movies-list").append(list);
            list = "";
        }
    }
});

$("#next").click(function() {
    var maxTabs = Math.floor(total / 8);
    var max;
    var list = "";
    if (counter != maxTabs) {
        counter++;
        var index = counter * 8;
        if (counter == maxTabs) {
            max = index + total % 8
            index = max - 8;
        } else
            max = index + 8
        $("#movies-list").empty();
        for (i = index; i < max; i++) {
            var index2 = res[i]["title"].length;
            var title = res[i]["title"];
            if (index2 > 30) {
                title = res[i]["title"].substring(0, 26) + ' ...';
            }
            list = '<div class="movies-box"><div class="movies-img"><div class="quality">HDRip</div><img src="http://localhost:8080/' + res[i]["image"] + '"></div><a href="/movies/' + res[i]["id"] + '">' + title + '<br>Full Movie [In English] With English/Arabic Subtitles | HDRip 1080p </a></div>';
            $("#movies-list").append(list);
            list = "";
        }
    }
});