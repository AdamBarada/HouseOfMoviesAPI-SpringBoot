var id;
var token;
var price;
var screeningTime;
var res = null;
var screenMap = new Array();
var sc;

(function() {
    "use strict";
    $("html").niceScroll({ styler: "fb", cursorcolor: "#000", cursorwidth: '4', cursorborderradius: '10px', background: '#ccc', spacebarenabled: false, cursorborder: '0', zindex: '1000' });
    $(".scrollbar1").niceScroll({ styler: "fb", cursorcolor: "#000", cursorwidth: '4', cursorborderradius: '0', autohidemode: 'false', background: '#ccc', spacebarenabled: false, cursorborder: '0' });
    $(".scrollbar1").getNiceScroll();
    if ($('body').hasClass('scrollbar1-collapsed')) {
        $(".scrollbar1").getNiceScroll().hide();
    }
})(jQuery);

function initSeats() {
    var $counter = $('#counter'),
        $total = $('#total');
    sc = $('#seat-map').seatCharts({
        map: screenMap,
        naming: {
            top: false,
            getLabel: function(character, row, column) {
                return column;
            }
        },
        legend: {
            node: $('#legend'),
            items: [
                ['a', 'available', 'Available'],
                ['a', 'unavailable', 'Sold'],
                ['a', 'selected', 'Selected']
            ]
        },
        click: function() {
            if (this.status() == 'available') {
                $counter.text(sc.find('selected').length + 1);
                $total.text(recalculateTotal(sc) + price);
                return 'selected';
            } else if (this.status() == 'selected') {
                $counter.text(sc.find('selected').length - 1);
                $total.text(recalculateTotal(sc) - price);
                return 'available';
            } else if (this.status() == 'unavailable') {
                return 'unavailable';
            } else {
                return this.style();
            }
        }
    });
    var takenSeats = new Array();
    var j = 0;
    res["room"]["seats"].forEach(element => {
        if (element["taken"]) {
            takenSeats[j++] = element["row"] + "_" + element["number"];
        }
    });
    sc.get(takenSeats).status('unavailable');
    $('#cover').fadeOut(1000);
};

function recalculateTotal(sc) {
    var total = 0;
    sc.find('selected').each(function() {
        total += price;
    });
    return total;
}

$(document).ready(function() {
    id = window.location.href.substring(window.location.href.lastIndexOf('/') + 1);
    token = (localStorage["token"]) ? localStorage["token"] : sessionStorage["token"];
    if (token) {
        $.ajax({
            url: 'http://localhost:8080/user/request/screening/' + id,
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
            url: 'http://localhost:8080/user/request/screening/' + id,
            type: 'GET',
            contentType: 'application/json',
            success: function(result) {
                res = result;
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
});

function pageLoaded() {

    price = res["price"];
    $('#roomName').append("Theatre Showing " + res["room"]["name"]);
    $('#movieName').append(": " + res["movie"]["title"]);
    time = res["time"].split(':');
    screeningTime = time[0] + ":" + time[1];
    $('#date').append(": " + res["date"]);
    $('#screeningDate').append(": " + screeningTime);
    for (i = 0; i < res["room"]["nbRows"]; i++) {
        screenMap[i] = "";
        for (j = 0; j < res["room"]["nbColumns"]; j++) {
            screenMap[i] += 'a';
        }
    }
    initSeats();

}

$('#addReservation').click(function() {
    var array = new Array();
    var seats = new Array();
    var data = '';
    array = res["room"]["seats"];
    var obj;
    var i = 0;
    sc.find('selected')["seatIds"].forEach(element => {
        rowCol = element.split('_');
        obj = array.find(items => items.row === parseInt(rowCol[0]) && items.number === parseInt(rowCol[1]));
        seats[i++] = obj.id;
    });
    if (seats.length == 0) {
        swal({
            title: "Select a seat before adding your reservation.",
            icon: "error",
            timer: 3000,
            button: true,
            dangerMode: true,
        });
        return;
    }

    swal({
        title: "Are you sure?",
        text: "Proceeding will confirm your reservation.",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then(function(willProceed) {
        if (willProceed) {
            var jsonData = {
                "screening": id,
                "seats_ids": seats
            }
            console.log(jsonData);
            for (i = 0; i < seats.length; i++) {
                var room = res["room"]["name"].split(" ");
                obj = array.find(items => items.id === parseInt(seats[i]));
                console.log(obj.row + " " + obj.number);
                data += '<div class="ticket" style="page-break-before: always"><div class="holes-top"></div><div class="title"><p class="cinema">House Of Movies Present</p><p class="movie-title">' + res["movie"]["title"] + '</p></div><div class="poster"><img src="http://localhost:8080/' + res["movie"]["landscape"] + '" alt="" /></div><div class="info"><table><tr><th>ROOM</th><th>ROW</th><th>SEAT</th></tr><tr><td class="bigger">' + room[1] + '</td><td class="bigger">' + obj.row + '</td><td class="bigger">' + obj.number + '</td></tr></table><table><tr><th>PRICE</th><th>DATE</th><th>TIME</th></tr><tr><td>$' + res["price"] + '.00</td><td>' + res["date"] + '</td><td>' + screeningTime + '</td></tr></table></div><div class="holes-lower"></div><div class="serial"><table class="barcode"><tr></tr></table><table class="numbers"><tr><td>9</td><td>1</td><td>7</td><td>3</td><td>7</td><td>5</td><td>4</td><td>4</td><td>4</td><td>5</td><td>4</td><td>1</td><td>4</td><td>7</td><td>8</td><td>7</td><td>3</td><td>4</td><td>1</td><td>4</td><td>5</td><td>2</td></tr></table></div></div>';
            }
            addRes(jsonData, data);
        }
    });
});

function addRes(jsonData, data) {
    if (token) {
        $.ajax({
            url: 'http://localhost:8080/user/request/reservations',
            type: 'POST',
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify(jsonData),
            headers: {
                'Authorization': 'Bearer ' + token
            },
            success: function() {
                swal({
                    title: "Your reservation has been successfuly added.",
                    icon: "success",
                    text: "Do you want to print your tickets?",
                    buttons: ["Exit", "Print"],
                    closeModal: false,
                }).then(function(accept) {
                    if (accept)
                        PrintDiv(data, true);
                    else
                        window.location = "/";
                });
            },
            error: function(error) {
                if (error.status == 404)
                    window.location = 'http://localhost:8080/error/notFound';
                else
                    window.location = 'http://localhost:8080/error/internalError';
            }
        });
    } else {
        $.ajax({
            url: 'http://localhost:8080/user/request/reservations',
            type: 'POST',
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify(jsonData),
            success: function() {
                PrintDiv(data);
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
}