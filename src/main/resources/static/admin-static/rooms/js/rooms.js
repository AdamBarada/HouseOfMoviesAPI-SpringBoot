var res;

function initTable (){
    var dataTable = $('#filtertable').DataTable({
        "pageLength":6,
        "aoColumns":[
            null,
            null,
            null
        ],
        order: [0, 'asc'],
        "bLengthChange":false,
        "dom":'<"top">ct<"top"p><"clear">'
    });
    $("#filterbox").keyup(function(){
        dataTable.search(this.value).draw();
    });
    $('#cover').fadeOut(1000);	
};

$(document).ready(function() {
        $.ajax({
            url: 'http://localhost:8080/public/request/rooms',
            type: 'GET',
            contentType: 'application/json',
            success: function (result) {
                res = result;
                console.log(res);
                pageLoaded();
            },
            error: function (error) {
                console.log(error);
                if (error.status == 404)
                    window.location = 'http://localhost:8080/error/notFound';
                else
                    window.location = 'http://localhost:8080/error/internalError';
            }
         });

});

function pageLoaded(){
    var list = "";
    for(i = 0; i<res.length; i++){
    list = '<tr><td>'+res[i]["id"]+'</td><td>'+res[i]["name"];
    list+='<td><div class="btn-group"><span class="actionCust"><a href="http://localhost:8080/reservation/'+res[i]["id"]+'"  style="border: none; font-weight:bold; font-size:14px">Book Now </a></span></div></td>';
    $('#tableData').append(list);
    list = '';
    }
    initTable();
}