function PrintDiv(data, redirect) {
    var frame1 = document.createElement('iframe');
    frame1.name = "frame1";
    frame1.style.position = "absolute";
    frame1.style.top = "-1000000px";
    document.body.appendChild(frame1);
    var frameDoc = (frame1.contentWindow) ? frame1.contentWindow : (frame1.contentDocument.document) ? frame1.contentDocument.document : frame1.contentDocument;
    frameDoc.document.open();
    frameDoc.document.write('<html><head><script src="https://code.jquery.com/jquery-3.3.1.js"integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60="crossorigin="anonymous"></script><script src="http://localhost:8080/shared/ticket/ticket.js"></script><link href="http://localhost:8080/shared/ticket/ticket.css" rel="stylesheet" type="text/css"/><style>@page { size: auto;  margin: 0mm; }</style><title></title>');
    (redirect) ?
    frameDoc.document.write('</head><body onafterprint="afterPrint()">'): frameDoc.document.write('</head><body>');
    frameDoc.document.write(data);
    frameDoc.document.write('</body></html>');
    frameDoc.document.close();
    setTimeout(function() {
        window.frames["frame1"].focus();
        window.frames["frame1"].print();
        document.body.removeChild(frame1);
    }, 500);
    return false;
}