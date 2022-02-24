package com.tree.clouds.coordination.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Data
@Component
public class QiniuUtil {
    private static String accessKey = "BGUpQznLLmK0W230OZBiAoaCuavRh-7OYSnLFdtT";
    private static String secretKey = "x_OuqdffUQwKLDM0bhOJIA6Mv9Ac37qFe1g21ywb";
    private static String bucket = "xpwgh";
    private static String fontUrl = "https://xpwghoss.3dy.me/";
//    @Value("${qiniu.accessKey}")
//    private static String accessKey;
//    @Value("${qiniu.secretKey}")
//    private static String secretKey;
//    @Value("${qiniu.bucket}")
//    private static String bucket;
//    @Value("${qiniu.fontUrl}")
//    private static String fontUrl;

    /**
     * 获取上传凭证
     */
    public static String getUploadCredential() {
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        System.out.println(upToken);
        return upToken;
    }

    //密钥配置
    Auth auth = Auth.create(accessKey, secretKey);

    /**
     * 文件上传
     *
     * @param zone          华东	Zone.zone0()
     *                      华北	Zone.zone1()
     *                      华南	Zone.zone2()
     *                      北美	Zone.zoneNa0()
     * @param upToken       上传凭证
     * @param localFilePath 需要上传的文件本地路径
     * @return
     */
    public static DefaultPutRet fileUpload(Zone zone, String upToken, String localFilePath) {
        // 构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(zone);
        // ...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        // 默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            // 解析上传成功的结果
            return new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                // ignore
            }
        }
        return null;

    }

    public static String fileUpload(String filePath) {
        DefaultPutRet defaultPutRet = fileUpload(Zone.zone2(), QiniuUtil.getUploadCredential(), filePath);
        if (defaultPutRet != null) {
            return fontUrl + defaultPutRet.key;
        }
        return null;
    }

    public static void main(String[] args) {

        DefaultPutRet defaultPutRet = fileUpload(Zone.zone2(), QiniuUtil.getUploadCredential(), "D:\\配置截图.jpg");
        System.out.println("defaultPutRet = " + fontUrl + defaultPutRet.key);
    }

    /**
     * 读取字节输入流内容
     *
     * @param is
     * @return
     */
    private static byte[] readInputStream(InputStream is) {
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        byte[] buff = new byte[1024 * 2];
        int len = 0;
        try {
            while ((len = is.read(buff)) != -1) {
                writer.write(buff, 0, len);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toByteArray();
    }

//    /**
//     * 下载
//     */
//    public void download(String targetUrl) {
//        //获取downloadUrl
//        String downloadUrl = getDownloadUrl(targetUrl);
//        //本地保存路径
//        String filePath = "D:/temp/picture/";
//        download(downloadUrl, filePath);
//    }

//
//    /**
//     * 通过发送http get 请求获取文件资源
//     * @param url
//     * @param filepath
//     * @return
//     */
//    private static void download(String url, String filepath) {
//        OkHttpClient client = new OkHttpClient();
//        System.out.println(url);
//        Request req = new Request.Builder().url(url).build();
//        Response resp = null;
//        try {
//            resp = client.newCall(req).execute();
//            System.out.println(resp.isSuccessful());
//            if(resp.isSuccessful()) {
//                ResponseBody body = resp.body();
//                InputStream is = body.byteStream();
//                byte[] data = readInputStream(is);
//                File imgFile = new File(filepath + "123.png");          //下载到本地的图片命名
//                FileOutputStream fops = new FileOutputStream(imgFile);
//                fops.write(data);
//                fops.close();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("Unexpected code " + resp);
//        }
//    }

    /**
     * 获取下载文件路径，即：donwloadUrl
     *
     * @return
     */
    public String getDownloadUrl(String targetUrl) {
        String downloadUrl = auth.privateDownloadUrl(targetUrl);
        return downloadUrl;
    }


//    /**
//     * 主函数：测试
//     * @param args
//     */
//    public static void main(String[] args) {
//        //构造私有空间的需要生成的下载的链接；
//        //格式： http://私有空间绑定的域名/空间下的文件名
//
//        String targetUrl = "http://p7s6tmhce.bkt.clouddn.com/123.png";         //外链域名下的图片路径
////        new DownLoad().download(targetUrl);
//    }

}
