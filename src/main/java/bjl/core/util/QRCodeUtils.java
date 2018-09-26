package bjl.core.util;

import bjl.core.upload.FileUploadConfig;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.File;
import java.nio.file.Path;
import java.util.Hashtable;

/**
 * 生成二维码 Google ZXing
 * Created by zhangjin on 2017/12/14.
 */
public class QRCodeUtils {


    private final static String FORMAT = "png";  //图片格式
    private final static int WIDTH = 169;  //二维码宽
    private final static int HEIGHT = 169;  //二维码高

    /**
     * 根据内容，生成指定宽高、指定格式的二维码图片
     *
     * @param text   内容
     * @return 生成的二维码图片路径
     * @throws Exception
     */

    public static String createQRCode(String text, FileUploadConfig fileUploadConfig) {

        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");  //文字编码
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);//容错级别
        hints.put(EncodeHintType.MARGIN, 1);//图片边距
        //生成二维码
        try {
            File folder = new File(fileUploadConfig.getPath(), fileUploadConfig.getQRCode());//创建工作目录
            folder.mkdirs();
            //创建工作目录
                String fileName = folder+"/"+text+".png";
                String fileUrl = "http://www.ying906.com/" + fileUploadConfig.getQRCode()+text+"."+FORMAT;
                String content = new String(text.getBytes("utf-8"), "iso-8859-1"); //微信,UC可识别的编码格式
                BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
                Path file = new File(fileName).toPath();
                MatrixToImageWriter.writeToPath(bitMatrix, FORMAT, file);
                return fileUrl;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
