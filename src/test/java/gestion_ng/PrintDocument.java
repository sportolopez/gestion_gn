//package gestion_ng;
//
//import org.apache.poi.xwpf.usermodel.Document;
//
//public class PrintDocument {
//    public static void main(String[] args)throws Exception {
//        //Load word document
// Document document = new Document();
//        document.loadFromFile("C:UsersAdministratorDesktopDocumentToPrint.docx");
//        //Create a printersettings object
// PrinterSettings printerSettings = new PrinterSettings();
//        //Specify virtual printer
// printerSettings.setPrinterName("Microsoft Print to PDF");
//        //Print to document
// printerSettings.setPrintToFile(true);
//        //Specifies the save path and name of the printed document
// printerSettings.setPrintFileName("output/PrintToPDF.pdf");
//        //Apply print settings
// document.getPrintDocument().setPrinterSettings(printerSettings);
//        //Print execution
// document.getPrintDocument().print();
//    }
//}