function showForPrint(pr) {
    newWin=window.open('','printWindow','width=800,height=600');
    newWin.document.open();

    newWin.document.write("<html><head><title>Version for print");
    newWin.document.write("</title>");
    newWin.document.write("<link rel='stylesheet' type='text/css' href='/Curriculum/print.css'>");
    newWin.document.write("</head><body>");
    newWin.document.write("<a href='javascript:window.print();'><img border=0 src='http://www.iconsearch.ru/uploads/icons/gnomeicontheme/24x24/stock_print.png' /></a>");
    newWin.document.write(pr);
    newWin.document.write("</body></html>");
    
    var list = newWin.document.getElementsByClassName("notPrintable");
    for (var i=0; i<list.length; i++) {
        list[i].parentNode.removeChild(list[i]);
    }
    
    list = newWin.document.getElementsByClassName("tab-row");
    for (var i=0; i<list.length; i++) {
        list[i].parentNode.removeChild(list[i]);
    }

    newWin.document.close();
}