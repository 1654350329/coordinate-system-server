package com.tree.clouds.coordination.controller;

import com.tree.clouds.coordination.common.Result;
import com.tree.clouds.coordination.common.aop.Log;
import com.tree.clouds.coordination.model.vo.PrinterInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

@RestController
@RequestMapping("/Printer")
@Api(value = "Printer", tags = "打印机模块")
public class PrinterController {
    @PostMapping("/getPrinterInfo")
    @ApiOperation(value = "打印机信息查询")
    @Log("打印机信息查询")
    public Result getPrinterInfo() {
        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(flavor, aset);
        PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
        PrinterInfoVO printerInfoVO = new PrinterInfoVO();
        printerInfoVO.setDefaultService(defaultService);
        printerInfoVO.setPrintServices(printServices);
        return Result.succ(printerInfoVO);
    }
}
