var res;

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
            null
        ],
        order: [
            [1, 'asc'],
            [2, 'asc']
        ],
        "bLengthChange": false,
        "dom": '<"top">ct<"top"p><"clear">'
    });
    if (localStorage["searchQuery"]) {
        console.log("entered");
        dataTable.search(localStorage["searchQuery"]).draw();
    }
    localStorage.removeItem("searchQuery");
    $("#filterbox").keyup(function() {
        dataTable.search(this.value).draw();
    });
    $('#cover').fadeOut(1000);
};

$(document).ready(function() {
    $.ajax({
        url: 'http://localhost:8080/public/request/screenings',
        type: 'GET',
        contentType: 'application/json',
        success: function(result) {
            console.log(result);
            res = result;
            pageLoaded();
        },
        error: function(error) {
            console.log(error);
        }
    });
});

function pageLoaded() {
    var list = "";
    for (i = 0; i < res.length; i++) {
        time = res[i]["time"].split(':');
        var screeningTime = time[0] + "h " + time[1] + " mins";
        list = '<tr><td>' + res[i]["movie"]["title"] + '</td><td>' + res[i]["date"] + '</td><td>' + screeningTime + '</td><td>' + res[i]["room"]["name"] + '</td>';
        if (res[i]["status"] == "AVAILABLE") {
            list += '<td><span class="mode mode_on">Upcoming</span></td><td><div class="btn-group"><span class="actionCust"><a href="http://localhost:8080/reservation/' + res[i]["id"] + '"  style="border: none; font-weight:bold; font-size:14px">Book Now </a></span></div></td>';
        } else {
            list += '<td><span class="mode mode_done">Started</span></td><td><div class="btn-group"><span class="actionCust"><a href="http://localhost:8080/reservation/' + res[i]["id"] + '"  style="border: none; font-weight:bold; font-size:14px">Book Now </a></span></div></td>';
        }
        $('#tableData').append(list);
        list = '';
    }
    initTable();
}