package com.tree.clouds.coordination.model.vo;

import lombok.Data;

import javax.print.PrintService;

@Data
public class PrinterInfoVO {
    PrintService[] PrintServices;
    PrintService defaultService;
}
