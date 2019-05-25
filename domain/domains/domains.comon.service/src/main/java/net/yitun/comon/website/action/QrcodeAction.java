package net.yitun.comon.website.action;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.yitun.basic.Code;
import net.yitun.basic.model.Result;
import net.yitun.basic.utils.WebUtil;
import net.yitun.basic.utils.extra.QRCodeUtil;
import net.yitun.comon.QrcodeApi;

@Slf4j
@Api(tags = "通用 二维码")
@RestController("basic.QrcodeApi")
public class QrcodeAction implements QrcodeApi {

    @Value("${basic.qrcode.logo-path: 1.png}")
    protected String logoPath = "1.png";

    @Autowired
    protected HttpServletResponse response;

    @Override
    @ApiOperation(value = "显示二维码")
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "data", value = "数据包( 请先UrlEncode编码, UTF-8 ), 二维码中的数据", required = true), //
            @ApiImplicitParam(name = "pixels", value = "二维码像素尺寸，默认: 320个像素", required = true, defaultValue = "320"), //
            @ApiImplicitParam(name = "format", value = "图片格式, jpg, png, jpeg, 默认:png", required = true, defaultValue = "png") })
    public void view(@RequestParam("data") String data, //
            @RequestParam(value = "pixels", defaultValue = "320") int pixels,
            @RequestParam(value = "format", defaultValue = "png") String format) {
        if (StringUtils.isBlank(data)) {
            WebUtil.outJson(new Result<>(Code.BAD_REQ, "数据包无效"), this.response);
            return;
        } else if (StringUtils.isBlank(format) || !StringUtils.equalsAnyIgnoreCase(format, "jpg", "png", "jpeg")) {
            WebUtil.outJson(new Result<>(Code.BAD_REQ, "二维码格式无效"), this.response);
            return;
        }

        try {
            data = URLDecoder.decode(data, "UTF-8");
            QRCodeUtil.outToStream(data, pixels, format, this.logoPath, this.response);
        } catch (Exception e) {
            log.error("<<< {}.view() {} ", this.getClass().getName(), e.toString());
            WebUtil.outJson(new Result<>(Code.BIZ_ERROR, "二维码图片生成失败"), this.response);
        }
    }

}
