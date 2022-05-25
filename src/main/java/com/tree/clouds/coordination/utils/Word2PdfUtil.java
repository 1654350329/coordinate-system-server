package com.tree.clouds.coordination.utils;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.aspectj.weaver.ast.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static com.tree.clouds.coordination.common.Constants.TMP_HOME;


/**
 * @author hww
 * @since 2018-09-27
 */
public class Word2PdfUtil {
    /**
     * dpi越大转换后越清晰，相对转换速度越慢
     */
    private static final Integer DPI = 500;

    /**
     * 转换后的图片类型
     */
    private static final String IMG_TYPE = "png";

    public static void main(String[] args) {
//        Word2PdfUtil.doc2("D:\\南劳鉴病字［2022］第0001号结论书.docx", "D:\\0001号结论书.pdf", SaveFormat.PDF);
        Word2PdfUtil.doc2Img("D:\\南劳鉴病字［2022］第0001号结论书.docx", "D:\\");
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
            os.close();
            // EPUB, XPS, SWF 相互转换
            long now = System.currentTimeMillis();
            System.out.println("Word转换成功，共耗时：" + ((now - old) / 1000.0) + "秒"); // 转化用时
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文档转图片
     *
     * @param inPath 传入文档地址
     * @param outDir 输出的图片文件夹地址
     */
    public static String doc2Img(String inPath, String outDir) {
        String pdfPath = TMP_HOME + UUID.randomUUID() + ".pdf";
        doc2(inPath, pdfPath, SaveFormat.PDF);
        String path = outDir + UUID.randomUUID() + ".png";
        try {
            pdfToImage(new File(pdfPath), path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    /**
     * PDF转图片
     *
     * @param file PDF文件的二进制流
     * @return 图片文件的二进制流
     */
    public static void pdfToImage(File file, String outDir) throws IOException {
        try (PDDocument document = PDDocument.load(file)) {
            PDFRenderer renderer = new PDFRenderer(document);
            for (int i = 0; i < document.getNumberOfPages(); ++i) {
                BufferedImage bufferedImage = renderer.renderImageWithDPI(i, DPI);
                ImageIO.write(bufferedImage, IMG_TYPE, new File(outDir));
            }

        }
    }
}
