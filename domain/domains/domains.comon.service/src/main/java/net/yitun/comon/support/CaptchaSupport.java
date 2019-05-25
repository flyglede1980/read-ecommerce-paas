package net.yitun.comon.support;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.google.code.kaptcha.GimpyEngine;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.impl.WaterRipple;
import com.google.code.kaptcha.util.Config;

import lombok.extern.slf4j.Slf4j;
import net.yitun.basic.utils.IoUtil;

/**
 * <pre>
 * <b>验证码支持.</b>
 * <b>Description:</b>
 *  Captcha
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年8月23日 下午8:31:42 LH
 *         new file.
 * </pre>
 */
@Slf4j
@Component("code.CaptchaSupport")
public class CaptchaSupport {

    protected DefaultKaptcha kaptcha;

    @PostConstruct
    protected void init() {
        this.kaptcha = new DefaultKaptcha();
        this.kaptcha.setConfig(new _Config());
    }

//    @Override 
//      implements InitializingBean
//    public void afterPropertiesSet() throws Exception {
//        this.kaptcha = new DefaultKaptcha();
//        this.kaptcha.setConfig(new _Config());
//    }

    /**
     * 输出渲染图片
     * 
     * @param code     验证码
     * @param response HTTP响应
     * @return boolean 是否输出图片成功
     */
    public boolean outImage(String code, HttpServletResponse response) {
        OutputStream out = null;
        BufferedImage image = this.kaptcha.createImage(code);

        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        // Set standard HTTP/1.1 no-cache
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set to expire far in the past.
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg"); // return a jpeg
        // Set IE extended HTTP/1.1 no-cache headers
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");

        // store the text in the session
        // request.getSession().setAttribute(KAPTCHA_SESSION_KEY, code);
        try {
            out = response.getOutputStream();
            ImageIO.write(image, "jpg", out); // write the data out

        } catch (IOException e) {
            log.error("<<< {}.outImage() {} ", this.getClass().getName(), e.toString());
            return false;

        } finally {
            IoUtil.close(true, out);
        }

        return true;
    }

    class _Config extends Config {

        public _Config() {
            this(new Properties());
        }

        public _Config(Properties properties) {
            super(properties);
        }

        @Override // kaptcha.border
        public boolean isBorderDrawn() {
            return false;
        }

        @Override // kaptcha.image.width
        public int getWidth() {
            return 120;
        }

        @Override // kaptcha.image.height
        public int getHeight() {
            return 44;
        }

        @Override // kaptcha.textproducer.font.size
        public int getTextProducerFontSize() {
            return 32;
        }

        @Override // kaptcha.obscurificator.impl
        public GimpyEngine getObscurificatorImpl() {
            WaterRipple engine = new WaterRipple();
            engine.setConfig(this);
            return engine;
        }

//    kaptcha.border  是否有边框  默认为true  我们可以自己设置yes，no  
//    kaptcha.border.color   边框颜色   默认为Color.BLACK  
//    kaptcha.border.thickness  边框粗细度  默认为1  

//    kaptcha.producer.impl   验证码生成器  默认为DefaultKaptcha: com.google.code.kaptcha.impl.DefaultKaptcha
//    kaptcha.textproducer.impl   验证码文本生成器  默认为DefaultTextCreator: com.google.code.kaptcha.text.impl.DefaultTextCreator
//    kaptcha.textproducer.char.string   验证码文本字符内容范围  默认为abcde2345678gfynmnpwx  
//    kaptcha.textproducer.char.length   验证码文本字符长度  默认为5  
//    kaptcha.textproducer.font.names    验证码文本字体样式  默认为new Font("Arial", 1, fontSize), new Font("Courier", 1, fontSize)  : Arial, Courier
//    kaptcha.textproducer.font.size   验证码文本字符大小  默认为40  
//    kaptcha.textproducer.font.color  验证码文本字符颜色  默认为Color.BLACK  
//    kaptcha.textproducer.char.space  验证码文本字符间距  默认为2  

//    kaptcha.noise.impl    验证码噪点生成对象  默认为DefaultNoise  
//    kaptcha.noise.impl    取消干扰线  com.google.code.kaptcha.impl.NoNoise
//    kaptcha.noise.color   验证码噪点颜色   默认为Color.BLACK  
//    kaptcha.obscurificator.impl   验证码渲染效果样式引擎  默认为WaterRipple: com.google.code.kaptcha.impl.WaterRipple, 水纹：WaterRipple；鱼眼：FishEyeGimpy；阴影：ShadowGimpy
//    kaptcha.word.impl   验证码文本字符渲染   默认为DefaultWordRenderer  

//    kaptcha.background.impl   验证码背景生成器   默认为DefaultBackground : com.google.code.kaptcha.impl.DefaultBackground
//    kaptcha.background.clear.from   验证码背景颜色渐进   默认为Color.LIGHT_GRAY  
//    kaptcha.background.clear.to   验证码背景颜色渐进   默认为Color.WHITE  

//    kaptcha.image.width   验证码图片宽度  默认为200  
//    kaptcha.image.height  验证码图片高度  默认为50

//    kaptcha.session.key  session缓存KEY
//    kaptcha.session.date  session缓存有效期

    }

}
