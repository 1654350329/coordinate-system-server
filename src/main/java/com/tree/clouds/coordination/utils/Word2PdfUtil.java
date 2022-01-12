package com.tree.clouds.coordination.utils;

import com.aspose.words.Document;
import com.aspose.words.License;
import org.aspectj.weaver.ast.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


/**
 * @author hww
 * @since 2018-09-27
 */
public class Word2PdfUtil {

    public static void main(String[] args) {
        Word2PdfUtil.doc2("D:\\workhome\\server2\\coordination-system-server\\file\\通知书202112工-1.docx", "D:\\workhome\\server2\\coordination-system-server\\file\\通知书202112工-1.pdf", 40);
    }

    public static boolean getLicense() {
        boolean result = false;
        try {
            InputStream is = Test.class.getClassLoader().getResourceAsStream("license.xml"); // license.xml应放在..\WebRoot\WEB-INF\classes路径下
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void doc2(String inPath, String outPath, int type) {
        if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
            return;
        }
        try {
            long old = System.currentTimeMillis();
            File file = new File(outPath); // 新建一个空白文档
            FileOutputStream os = new FileOutputStream(file);
            Document doc = new Document(inPath); // Address是将要被转化的word文档
            doc.save(os, type);// 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF,
            // EPUB, XPS, SWF 相互转换
            long now = System.currentTimeMillis();
            System.out.println("Word转换成功，共耗时：" + ((now - old) / 1000.0) + "秒"); // 转化用时
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
