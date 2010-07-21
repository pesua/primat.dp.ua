function showForPrint(pr) {
    newWin=window.open('','printWindow','width=800,height=600');
    newWin.document.open();

    newWin.document.write("<html><head><title>Version for print");
    newWin.document.write("</title></head><body>");
    newWin.document.write("<a href='javascript:window.print();'>Print</a>");
    newWin.document.write(pr);
    newWin.document.write("</body></html>");

    newWin.document.close();
    return 0;
}