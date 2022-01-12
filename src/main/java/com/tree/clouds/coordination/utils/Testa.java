package com.tree.clouds.coordination.utils;

import javax.print.*;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Testa {


    /**
     * 调用打印机功能
     *
     * @author Administrator
     */

    public static void main(String[] args) {
        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(flavor, aset);
        PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
        PrintService service = ServiceUI.printDialog(null, 200, 200, printServices,
                defaultService, flavor, aset);

        String fileName = "D:" + File.separator + "zkyzl.txt";

        if (service != null) {
            try {
                DocPrintJob pj = service.createPrintJob();
                FileInputStream fis = new FileInputStream(fileName);
                DocAttributeSet das = new HashDocAttributeSet();
                Doc doc = new SimpleDoc(fis, flavor, das);
                pj.print(doc, aset);
            } catch (FileNotFoundException | PrintException fe) {
                fe.printStackTrace();
            }
        } else {
            System.out.println("打印失败");
        }
    }
}