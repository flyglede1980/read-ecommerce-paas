package net.yitun.basic.utils.extra;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * <b>二维码工具.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月20日 上午10:17:46 LH
 *         new file.
 * </pre>
 */

@Slf4j
public final class QRCodeUtil {

    // 二维码颜色
    private static final int BLACK = 0xFF000000;

    // 二维码颜色
    private static final int WHITE = 0xFFFFFFFF;

    // LOGO宽度
    private static final int LOGO_PIXELS = 60;

    /**
     * 
     * @param text
     * @param pixels
     * @param format
     * @param logoPath
     * @return BufferedImage
     * @throws Exception
     */
    public static BufferedImage image(String text, int pixels, String format, String logoPath) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.MARGIN, 1); // 白边大小，取值范围0~4
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); // 指定编码格式
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M); // 设置纠错级别

        // 创建比特矩阵(位矩阵)的QR码编码的字符串
        BitMatrix bitMatrix = new MultiFormatWriter() //
                .encode(text, BarcodeFormat.QR_CODE, pixels, pixels, hints);
//        BitMatrix bitMatrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        int width = bitMatrix.getWidth(), height = bitMatrix.getHeight();
        // 开始在缓冲图片中画二维码, 使BufferedImage勾画QRCode (width 是行二维码像素点)
        BufferedImage source = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                source.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
//                image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);

        if (null != logoPath && 0 != logoPath.length())
            addLogo(pixels, source, logoPath, true /* compress */);

        return source;
    }

    /**
     * 输出字符串二维码
     * 
     * @param text
     * @param pixels
     * @param format
     * @param logoPath
     * @return data:image/png;base64,${binary}
     */
    public static String outToStr(String text, int pixels, String format, String logoPath) throws Exception {
        // 1、读取文件转换为字节数组
        BufferedImage image = image(text, pixels, format, logoPath);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        // 转换成png格式的IO流
        ImageIO.write(image, "png", out);
        byte[] bytes = out.toByteArray();

        // 2、将字节数组转为二进制
        Encoder encoder = Base64.getEncoder();
        String binary = encoder.encodeToString(bytes).trim();

        return "data:image/" + format + ";base64," + binary;
    }

    /**
     * 根据内容，生成指定宽高、指定格式的二维码图片
     *
     * @param text     内容
     * @param pixels   像素
     * @param format   图片格式, eg: png, jpg, gif
     * @param logoPath
     * @param filePath 存储文件
     * @return 生成的二维码图片路径
     * @throws Exception
     */
    public static String outToFile(String text, int pixels, String format, String logoPath, String filePath) throws Exception {
        BufferedImage image = image(text, pixels, format, logoPath);

        if (!ImageIO.write(image, format, new File(filePath)))
            throw new IOException("Could not write an image of format " + format + " to " + filePath);

        return filePath;
    }

    /**
     * 输出二维码图片流
     * 
     * @param text     内容
     * @param pixels   像素
     * @param format   图片格式, eg: png, jpg, gif
     * @param logoPath
     * @param output   OutputStream
     * @throws Exception
     */
    public static void outToStream(String text, int pixels, String format, String logoPath, OutputStream output)
            throws Exception {
        BufferedImage image = image(text, pixels, format, logoPath);

        if (!ImageIO.write(image, format, output))
            throw new IOException("Could not write an image of format " + format);
    }

    /**
     * 输出二维码图片流
     * 
     * @param text     内容
     * @param pixels   像素
     * @param format   图片格式, eg: png, jpg, gif
     * @param logoPath
     * @param response HttpServletResponse
     * @throws Exception
     */
    public static void outToStream(String text, int pixels, String format, String logoPath, HttpServletResponse response)
            throws Exception {
        response.setDateHeader("Expires", 0L);
        response.setContentType("image/" + format + ";charset=UTF-8");
        response.addHeader("Pragma", "no-cache");
        response.addHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");

        outToStream(text, pixels, format, logoPath, response.getOutputStream());
    }

    /**
     * 插入LOGO
     *
     * @param pixels   像素
     * @param source   二维码图片
     * @param logoPath LOGO图片地址
     * @param compress 是否压缩
     * @throws Exception
     */
    private static void addLogo(int pixels, BufferedImage source, String logoPath, boolean compress) throws Exception {
        URL resource //
                = QRCodeUtil.class.getClassLoader().getResource(logoPath);
        // InputStream input = QRCodeUtil.class.getResourceAsStream(logoPath);
        if (null == resource)
            return;

        InputStream input = null;
        Image logoImage = null;
        String realPath = resource.getFile();
        if (realPath.contains(".jar!/")) {
            input = resource.openStream();
            logoImage = ImageIO.read(input);

        } else {
            File file = new File(realPath);
            if (!file.exists())
                return; // throw new Exception("logo file not found.");

            logoImage = ImageIO.read(file);
        }

        int width = logoImage.getWidth(null), height = logoImage.getHeight(null);
        if (compress) { // 压缩LOGO
            width = width > LOGO_PIXELS ? LOGO_PIXELS : width;
            height = height > LOGO_PIXELS ? LOGO_PIXELS : height;

            Image image = logoImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            logoImage = image;
        }

        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (pixels - width) / 2, y = (pixels - height) / 2;
        graph.drawImage(logoImage, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 解析指定路径下的二维码图片
     *
     * @param filePath 二维码图片路径
     * @return String
     */
    public static String parse(String filePath) {
        String content = "";
        try {
            File file = new File(filePath);
            BufferedImage image = ImageIO.read(file);
            // BufferedImage image = ImageIO.read(inputStream);

            LuminanceSource source = new QrcSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);

            Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
            hints.put(DecodeHintType.CHARACTER_SET, "utf-8");

            MultiFormatReader formatReader = new MultiFormatReader();
            Result result = formatReader.decode(binaryBitmap, hints);

            log.info("result 为：" + result.toString());
            log.info("resultFormat 为：" + result.getBarcodeFormat());
            log.info("resultText 为：" + result.getText());
            // 设置返回值
            content = result.getText();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return content;
    }

    public static void main(String[] args) {
        int pixels = 320; // 二维码图片的宽 、 高
        String format = "png"; // 二维码图片的格式
        String content = "https://www.changjian.com/share?code=MWTTVV"; // + UrlUtil.encode("SD23A"); // 随机生成验证码
        String logoFile = "D:/Studio/Workspace/ioften/basic/basic.core/src/test/java/net/yitun/basic/logo.png";
        System.out.println("内容： " + content);

        try {
            String binary = outToStr(content, pixels, format, null);
            System.out.println("生成二维码的字符： " + binary);

            // 生成二维码图片，并返回图片路径
            String file = outToFile(content, pixels, format, null, "D:/MWTTVV.png");
            System.out.println("生成二维码的图片路径： " + file);

            // 生成二维码图片，并返回图片路径
            String _file = outToFile(content, pixels, format, logoFile, "D:/MWTTVV2.png");
            System.out.println("生成二维码的图片路径： " + _file);

            String _content = parse(file);
            System.out.println("解析出二维码的图片的内容为： " + _content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class QrcSource extends LuminanceSource {

    private final int left;

    private final int top;

    private final BufferedImage image;

    public QrcSource(BufferedImage image) {
        this(image, 0, 0, image.getWidth(), image.getHeight());
    }

    public QrcSource(BufferedImage image, int left, int top, int width, int height) {
        super(width, height);
        int sourceWidth = image.getWidth();
        int sourceHeight = image.getHeight();
        if (left + width > sourceWidth || top + height > sourceHeight)
            throw new IllegalArgumentException("Crop rectangle does not fit within image data.");

        for (int y = top; y < top + height; y++)
            for (int x = left; x < left + width; x++)
                if ((image.getRGB(x, y) & 0xFF000000) == 0)
                    image.setRGB(x, y, 0xFFFFFFFF); // = white

        this.left = left;
        this.top = top;
        this.image = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_BYTE_GRAY);
        this.image.getGraphics().drawImage(image, 0, 0, null);
    }

    @Override
    public byte[] getRow(int y, byte[] row) {
        if (y < 0 || y >= getHeight())
            throw new IllegalArgumentException("Requested row is outside the image: " + y);

        int width = getWidth();
        if (row == null || row.length < width)
            row = new byte[width];

        image.getRaster().getDataElements(left, top + y, width, 1, row);
        return row;
    }

    @Override
    public byte[] getMatrix() {
        int width = getWidth();
        int height = getHeight();
        int area = width * height;
        byte[] matrix = new byte[area];
        image.getRaster().getDataElements(left, top, width, height, matrix);
        return matrix;
    }

    @Override
    public boolean isCropSupported() {
        return true;
    }

    @Override
    public LuminanceSource crop(int left, int top, int width, int height) {
        return new QrcSource(image, this.left + left, this.top + top, width, height);
    }

    @Override
    public boolean isRotateSupported() {
        return true;
    }

    @Override
    public LuminanceSource rotateCounterClockwise() {
        int sourceWidth = image.getWidth();
        int sourceHeight = image.getHeight();
        AffineTransform transform = new AffineTransform(0.0, -1.0, 1.0, 0.0, 0.0, sourceWidth);
        BufferedImage rotatedImage = new BufferedImage(sourceHeight, sourceWidth, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g = rotatedImage.createGraphics();
        g.drawImage(image, transform, null);
        g.dispose();
        int width = getWidth();
        return new QrcSource(rotatedImage, top, sourceWidth - (left + width), getHeight(), width);
    }
}
