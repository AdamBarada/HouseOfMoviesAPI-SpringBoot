var code = '11010010000100111011001011101111011010001110101110011001101110010010111101110111001011001001000011011000111010110001001110111101101001011010111000101101'

$(document).ready(function() {

    table = $('.barcode tr');
    for (var i = 0; i < code.length; i++) {
        if (code[i] == 1) {
            table.append('<td class="black">')
        } else {
            table.append('<td class="white">')
        }
    }
});

function afterPrint() {
    window.parent.location = "/";
}