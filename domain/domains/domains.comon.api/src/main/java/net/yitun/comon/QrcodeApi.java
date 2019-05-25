package net.yitun.comon;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.yitun.comon.conf.Conf;

/**
 * <pre>
 * <b>通用 二维码.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月8日 下午7:49:51 LH
 *         new file.
 * </pre>
 */
public interface QrcodeApi {

    /**
     * 显示二维码
     * 
     * @param data   数据包( 请先UrlEncode编码, UTF-8 ), 二维码中的数据
     * @param pixels 二维码像素尺寸，默认: 320个像素
     * @param format 图片格式, jpg, png, jpeg
     */
    @GetMapping(value = Conf.ROUTE //
            + "/qrcode", consumes = MediaType.ALL_VALUE, produces = MediaType.ALL_VALUE)
    void view(@RequestParam("data") String data, //
            @RequestParam(value = "pixels", defaultValue = "320") int pixels,
            @RequestParam(value = "format", defaultValue = "png") String format);

}
