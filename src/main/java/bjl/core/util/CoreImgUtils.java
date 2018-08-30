package bjl.core.util;

import bjl.core.upload.FileUploadConfig;
import bjl.tcp.GlobalVariable;
import bjl.websocket.command.GameStatus;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Random;

/**
 * Created by pengyi on 2016/5/27.
 */
public class CoreImgUtils {


    private static FileUploadConfig fileUploadConfig = new FileUploadConfig();

    // 水印透明度
    private static float alpha = 0.5f;
    // 水印横向位置
    private static int positionWidth = 0;
    // 水印纵向位置
    private static int positionHeight = 0;
    // 水印文字字体
    private static Font font = new Font("宋体", Font.BOLD, 30);
    // 水印文字颜色
    private static Color color = Color.red;

    private static final String ICO_FOLDER = "../../resources/images/logo/";

    /**
     * @param alpha          水印透明度
     * @param positionWidth  水印横向位置
     * @param positionHeight 水印纵向位置
     * @param font           水印文字字体
     * @param color          水印文字颜色
     */
    public static void setImageMarkOptions(float alpha, int positionWidth, int positionHeight, Font font, Color color) {
        if (alpha != 0.0f) CoreImgUtils.alpha = alpha;
        if (positionWidth != 0) CoreImgUtils.positionWidth = positionWidth;
        if (positionHeight != 0) CoreImgUtils.positionHeight = positionHeight;
        if (font != null) CoreImgUtils.font = font;
        if (color != null) CoreImgUtils.color = color;
    }

    /**
     * 给图片添加水印图片
     *
     * @param srcImgPath 源图片路径
     * @param targerPath 目标图片路径
     */
    public static void markImageByIcon(String srcImgPath,
                                       String targerPath) {
        markImageByIcon(srcImgPath, targerPath, null);
    }

    /**
     * 给图片添加水印图片、可设置水印图片旋转角度
     *
     * @param srcImgPath 源图片路径
     * @param targerPath 目标图片路径
     * @param degree     水印图片旋转角度
     */
    public static void markImageByIcon(String srcImgPath,
                                       String targerPath, Integer degree) {
        OutputStream os = null;
        try {

            Image srcImg = ImageIO.read(new File(srcImgPath));
            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),
                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);

            // 1、得到画笔对象
            Graphics2D g = buffImg.createGraphics();

            // 2、设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
            // 3、设置水印旋转
            if (null != degree) {
                g.rotate(Math.toRadians(degree), (double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
            }
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

            //根据图片大小选择水印图片
            String imgName;
            if (srcImg.getWidth(null) < 100) {
                imgName = "10.png";
            } else if (srcImg.getWidth(null) < 500) {
                imgName = "25.png";
            } else {
                imgName = "50.png";
            }
            //获取水印图片路径
            String iconPath = new File(CoreImgUtils.class.getResource("/").getFile(), ICO_FOLDER + imgName).getCanonicalPath();
            // 4、水印图片的路径 水印图片一般为gif或者png的，这样可设置透明度
            ImageIcon imgIcon = new ImageIcon(iconPath);
            // 5、得到Image对象。
            Image img = imgIcon.getImage();
            // 6、水印图片的位置
            g.drawImage(img, 1, 1, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            // 7、释放资源
            g.dispose();

            // 8、生成图片
            os = new FileOutputStream(targerPath);
            ImageIO.write(buffImg, "JPG", os);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 给图片添加水印文字
     *
     * @param logoText   水印文字
     * @param srcImgPath 源图片路径
     * @param targerPath 目标图片路径
     */
    public static void markImageByText(String logoText, String srcImgPath,
                                       String targerPath) {
        markImageByText(logoText, srcImgPath, targerPath, null);
    }

    /**
     * 给图片添加水印文字、可设置水印文字的旋转角度
     *
     * @param logoText
     * @param srcImgPath
     * @param targerPath
     * @param degree
     */
    public static void markImageByText(String logoText, String srcImgPath,
                                       String targerPath, Integer degree) {

        InputStream is = null;
        OutputStream os = null;
        try {
            // 1、源图片
            Image srcImg = ImageIO.read(new File(srcImgPath));
            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null), srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);

            // 2、得到画笔对象
            Graphics2D g = buffImg.createGraphics();
            // 3、设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
            // 4、设置水印旋转
            if (null != degree) {
                g.rotate(Math.toRadians(degree), (double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
            }
            // 5、设置水印文字颜色
            g.setColor(color);
            // 6、设置水印文字Font
            g.setFont(font);
            // 7、设置水印文字透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            // 8、第一参数->设置的内容，后面两个参数->文字在图片上的坐标位置(x,y)
            g.drawString(logoText, positionWidth, positionHeight);
            // 9、释放资源
            g.dispose();
            // 10、生成图片
            os = new FileOutputStream(targerPath);
            ImageIO.write(buffImg, "JPG", os);

            System.out.println("图片完成添加水印文字");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != is)
                    is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 生成表格图片
     * @param list 数据行
     * @param total 合计
     * @param dataType 数据类型 1：投注数据 2：结算数据
     * @return 图片地址
     */
    public static String tableImage(GameStatus gameStatus,ArrayList<Object[]> list, BigDecimal[] total,Integer roomType, Integer dataType){

        //数据行数
        int totalRow = list.size()+3;   //总行数 = 数据行+标题+表头行+合计
        int totalCol = dataType == 1 ? 6 : 4;    //总列数
        int imageWidth = 668;   //图片宽带（像素）
        int imageHeight = totalRow*40+20; //图片高度
        int rowHeight = 40; //行高
        int startHeight = 10;
        int startWidth = 10;
        int colWidth = ((imageWidth-20)/totalCol); //行宽
        String[] headCells = dataType == 1 ? new String[]{"玩家","闲","庄","闲对","庄对","和"} : new String[]{"玩家","本局得分","剩余分","初始分"};
        String[] totalCells = dataType == 1? new String[]{"合计:"+total[0],total[1].toString(),total[2].toString(),total[3].toString(),total[4].toString(),total[5].toString()}
                                           : new String[]{"合计:"+total[0],total[1].toString(),total[2].toString(),total[3].toString()};

        try {
            BufferedImage image = new BufferedImage(imageWidth, imageHeight,BufferedImage.TYPE_INT_RGB);
            Graphics graphics = image.getGraphics();

            graphics.setColor(Color.WHITE);
            graphics.fillRect(0,0, imageWidth, imageHeight);
            graphics.setColor(new Color(239, 240, 176));

            //画横线
            for(int j=0;j<totalRow;j++){
                graphics.setColor(Color.black);
                graphics.drawLine(startWidth, startHeight+(j+1)*rowHeight, imageWidth-startWidth, startHeight+(j+1)*rowHeight);
            }

            //画竖线
            for(int k=0;k<=totalCol;k++){
                graphics.setColor(Color.black);
                graphics.drawLine(startWidth+k*colWidth, startHeight+rowHeight, startWidth+k*colWidth, imageHeight-startHeight);
            }

            //设置字体
            Font font = new Font("华文楷体",Font.BOLD,24);
            graphics.setFont(font);

            //写标题
            String title = (dataType == 1? "投注表-" : "分数表-")+gameStatus.getxNum()+"靴"+gameStatus.getjNum()+"局";
            graphics.drawString(title, imageWidth/3+startWidth, startHeight+rowHeight-10);

            font = new Font("华文楷体",Font.BOLD,22);
            graphics.setFont(font);

            //写入表头

            for(int m=0;m<headCells.length;m++){
                graphics.drawString(headCells[m], startWidth+colWidth*m+5, startHeight+rowHeight*2-10);
            }

            //设置字体
            font = new Font("华文楷体",Font.PLAIN,20);
            graphics.setFont(font);
            //写入内容

            for(int n=0;n<list.size();n++){
                Object[] arr = list.get(n);
                for(int l=0;l<arr.length;l++){
                    graphics.drawString(arr[l].toString(), startWidth+colWidth*l+5, startHeight+rowHeight*(n+3)-10);
                }
            }

            font = new Font("华文楷体",Font.BOLD,20);
            graphics.setFont(font);
            graphics.setColor(new Color(192, 62, 66));

            //写入合计
            for(int i = 0;i<1;i++){
                for(int m=0;m<totalCells.length;m++){
                    graphics.drawString(totalCells[m], startWidth+colWidth*m+5, imageHeight-rowHeight+startHeight*2);
                }
            }

            //年-月-日_房_鞋_局_投注/分数; 2018-1-2_1_1_1_1_时间戳.jpg
            //生成投注表
//            GameStatus gameStatus = GlobalVariable.getGameStatusMap().get(roomType);
            String fileName = CoreDateUtils.formatDate(new Date())+"_"+roomType+"_"+gameStatus.getxNum()+"_"+gameStatus.getjNum()+"_"+dataType+"_"+System.currentTimeMillis()+".jpg";

            return ServiceUtil.serviceUtil.getFileUploadService().uploadBetImg(fileName,image);

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String validateCode(String path){

        try {
            // 图片的宽度。
            int width = 160;
            // 图片的高度。
            int height = 40;
            // 验证码字符个数
            int codeCount = 5;
            // 验证码干扰线数
            int lineCount = 5; //以前是150，现改成5
            // 验证码
            String code = null;
            // 验证码图片Buffer
            BufferedImage buffImg = null;

            // 验证码范围,去掉0(数字)和O(拼音)容易混淆的(小写的1和L也可以去掉,大写不用了)
            char[] codeSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                    'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                    'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
            int x , fontHeight, codeY ;
            int red , green , blue ;

            x = width / (codeCount + 2);//每个字符的宽度(左右各空出一个字符)
            fontHeight = height - 2;//字体的高度
            codeY = height - 4;

            // 图像buffer
            buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = buffImg.createGraphics();
            // 生成随机数
            Random random = new Random();
            // 将图像填充为白色
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);
            // 创建字体,可以修改为其它的
            Font font = new Font("Fixedsys", Font.PLAIN, fontHeight);
//        Font font = new Font("Times New Roman", Font.ROMAN_BASELINE, fontHeight);
            g.setFont(font);

            for (int i = 0; i < lineCount; i++) {
                // 设置随机开始和结束坐标
                int xs = random.nextInt(width);//x坐标开始
                int ys = random.nextInt(height);//y坐标开始
                int xe = xs + random.nextInt(width / 8);//x坐标结束
                int ye = ys + random.nextInt(height / 8);//y坐标结束

                // 产生随机的颜色值，让输出的每个干扰线的颜色值都将不同。
                red = random.nextInt(255);
                green = random.nextInt(255);
                blue = random.nextInt(255);
                g.setColor(new Color(red, green, blue));
                g.drawLine(xs, ys, xe, ye);
            }

            // randomCode记录随机产生的验证码
            StringBuffer randomCode = new StringBuffer();
            // 随机产生codeCount个字符的验证码。
            for (int i = 0; i < codeCount; i++) {
                String strRand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
                // 产生随机的颜色值，让输出的每个字符的颜色值都将不同。
                red = random.nextInt(255);
                green = random.nextInt(255);
                blue = random.nextInt(255);
                g.setColor(new Color(red, green, blue));
                g.drawString(strRand, (i + 1) * x, codeY);
                // 将产生的四个随机数组合在一起。
                randomCode.append(strRand);
            }
            OutputStream os = new FileOutputStream(path);
            ImageIO.write(buffImg, "png", os);
            os.close();
            return randomCode.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
