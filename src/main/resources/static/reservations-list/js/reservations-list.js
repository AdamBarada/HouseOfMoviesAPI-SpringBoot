var res;
var token;
var timeTicket;

function initTable() {
    var dataTable = $('#filtertable').DataTable({
        "pageLength": 6,
        columnDefs: [
            { type: 'date-dd-mm-yyyy', aTargets: [2] }
        ],
        "aoColumns": [
            null,
            null,
            null,
            null,
            null,
            null,
            null
        ],
        order: [
            [1, 'desc'],
            [2, 'desc']
        ],
        "bLengthChange": false,
        "dom": '<"top">ct<"top"p><"clear">'
    });
    $("#filterbox").keyup(function() {
        dataTable.search(this.value).draw();
    });
    $('#cover').fadeOut(1000);
    addClick();
};

$(document).ready(function() {
    token = (localStorage["token"]) ? localStorage["token"] : sessionStorage["token"];
    if (token) {
        $.ajax({
            url: 'http://localhost:8080/user/request/reservations',
            type: 'GET',
            contentType: 'application/json',
            headers: {
                'Authorization': 'Bearer ' + token
            },
            success: function(result) {
                res = result;
                console.log(res);
                pageLoaded();
            },
            error: function() {
                window.location = 'http://localhost:8080/error/internalError';
            }
        });
    } else {
        $.ajax({
            url: 'http://localhost:8080/user/request/reservations',
            type: 'GET',
            contentType: 'application/json',
            success: function(result) {
                res = result;
                pageLoaded();
            },
            error: function(error) {
                console.log(error);
                if (error.status == 401 || error.status == 403)
                    window.location = 'http://localhost:8080/error/accessDenied';
                else
                    window.location = 'http://localhost:8080/error/internalError';
            }
        });
    }
});

function pageLoaded() {
    var list = "";
    for (i = 0; i < res.length; i++) {
        time = res[i]["screening"]["time"].split(':');
        timeTicket = time[0] + ":" + time[1];
        var screeningTime = time[0] + "h " + time[1] + " mins";
        list = '<tr><td>' + res[i]["screening"]["movie"]["title"] + '</td><td>' + res[i]["screening"]["date"] + '</td><td>' + screeningTime + '</td><td>' + res[i]["screening"]["room"]["name"] + '</td><td>' + res[i]["total"] + '$</td>';
        if (res[i]["status"] == "AVAILABLE") {
            list += '<td><span class="mode mode_on">Upcoming</span></td><td><div class="btn-group"><span class="actionCust" id="' + i + '"><a href="javascript:void(0);"><i class="fa fa-print"></i></a></span><span class="actionCust2" id="delete' + i + '"><a href="javascript:void(0);"><i class="fa fa-trash"></i></a></span></div></td>';
        } else {
            list += '<td><span class="mode mode_off">Past</span></td><td><div class="btn-group"><span class="actionCust" id="' + i + '"><a href="javascript:void(0);"><i class="fa fa-print"></i></a></span></div></td>';
        }
        $('#tableData').append(list);
        list = '';
    }
    initTable();
}

function addClick() {
    $("#tableData").on("click", ".actionCust", function() {
        var j = $(this).attr('id');
        var data = "";
        for (i = 0; i < res[j]["seats_reserved"].length; i++) {
            var room = res[j]["screening"]["room"]["name"].split(" ");
            data += '<div class="ticket" style="page-break-before: always"><div class="holes-top"></div><div class="title"><p class="cinema">House Of Movies Present</p><p class="movie-title">' + res[j]["screening"]["movie"]["title"] + '</p></div><div class="poster"><img src="http://localhost:8080/' + res[j]["screening"]["movie"]["landscape"] + '" alt="" /></div><div class="info"><table><tr><th>ROOM</th><th>ROW</th><th>SEAT</th></tr><tr><td class="bigger">' + room[1] + '</td><td class="bigger">' + res[j]["seats_reserved"][i]["seat"]["row"] + '</td><td class="bigger">' + res[j]["seats_reserved"][i]["seat"]["number"] + '</td></tr></table><table><tr><th>PRICE</th><th>DATE</th><th>TIME</th></tr><tr><td>$' + res[j]["screening"]["price"] + '.00</td><td>' + res[j]["date"] + '</td><td>' + timeTicket + '</td></tr></table></div><div class="holes-lower"></div><div class="serial"><table class="barcode"><tr></tr></table><table class="numbers"><tr><td>9</td><td>1</td><td>7</td><td>3</td><td>7</td><td>5</td><td>4</td><td>4</td><td>4</td><td>5</td><td>4</td><td>1</td><td>4</td><td>7</td><td>8</td><td>7</td><td>3</td><td>4</td><td>1</td><td>4</td><td>5</td><td>2</td></tr></table></div></div>';
        }
        PrintDiv(data, false);
    });

    $("#tableData").on("click", ".actionCust2", function() {
        swal({
                title: "Are you sure?",
                text: "Once deleted, you will not be able to recover your reservation.",
                icon: "warning",
                buttons: true,
                dangerMode: true,
            })
            .then((willDelete) => {
                if (willDelete) {
                    var id = $(this).attr('id').charAt($(this).attr('id').length - 1);
                    deleteRes(id);
                }
            });
    })
}

function deleteRes(id) {
    if (token) {
        $.ajax({
            url: 'http://localhost:8080/user/request/reservations/' + res[id]["id"],
            type: 'DELETE',
            contentType: 'application/json',
            headers: {
                'Authorization': 'Bearer ' + token
            },
            success: function(result) {
                res = result;
                swal("Your reservation has been successfuly deleted. ", {
                    icon: "success",
                }).then(function() {
                    location.reload();
                });
            },
            error: function() {
                window.location = 'http://localhost:8080/error/internalError';
            }
        });
    } else {
        $.ajax({
            url: 'http://localhost:8080/user/request/reservations/' + res[id]["id"],
            type: 'DELETE',
            contentType: 'application/json',
            headers: {
                'Authorization': 'Bearer ' + token
            },
            success: function(result) {
                res = result;
                location.reload();
            },
            error: function(error) {
                if (error.status == 401 || error.status == 403)
                    window.location = 'http://localhost:8080/error/accessDenied';
                else
                    window.location = 'http://localhost:8080/error/internalError';
            }
        });
    }
}