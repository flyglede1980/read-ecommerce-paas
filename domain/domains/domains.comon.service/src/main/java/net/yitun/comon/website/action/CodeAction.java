package net.yitun.comon.website.action;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.yitun.basic.Code;
import net.yitun.basic.Util;
import net.yitun.basic.model.Result;
import net.yitun.basic.utils.WebUtil;
import net.yitun.comon.CodeApi;
import net.yitun.comon.dicts.Type;
import net.yitun.comon.service.CodeService;

@Api(tags = "通用 验证码")
@RestController("basic.CodeApi")
public class CodeAction implements CodeApi {

    @Autowired
    protected CodeService service;

    @Autowired
    protected HttpServletResponse response;

    @Override
    @ApiOperation(value = "图形验证码", notes = "6个数字字符, 有效期120秒，刷新频率最快3秒/次")
    @ApiImplicitParam(name = "mark", value = "账号或手机号", required = true)
    public void img(@PathVariable("mark") String mark) {
        if (StringUtils.isBlank(mark) || 2 > mark.length()) {
            WebUtil.outJson(new Result<>(Code.BAD_REQ, "账号或手机号无效"), this.response);
            return;
        }

        Result<?> result = this.service.outImg(true, mark, this.response);
        if (null != result && !result.ok())
            WebUtil.outJson(Code.OK, result, this.response);
    }

    @Override
    @ApiOperation(value = "短信验证码" //
            , notes = "发送短信为: 4个数字字符, 有效期300秒，发送频率最快30秒/次, !!!特别: NEED_IMG_CODE: 代表发短信需要校验图形验证码; SMS_LOCKED:600, 代表短信发送被锁 600秒")
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "phone", value = "手机号", required = true), //
            @ApiImplicitParam(name = "code", value = "图形验证码, 前两次不对此进行校验, 长度为6个字符", required = false) }) //
    @ApiResponses({ //
            @ApiResponse(code = Code.OK, message = "发送成功"), //
            @ApiResponse(code = Code.BAD_REQ, message = "请求参数无效"), //
            @ApiResponse(code = Code.BIZ_ERROR, message = "发送限制或异常, !!!特别: NEED_IMG_CODE: 代表发短信需要校验图形验证码; SMS_LOCKED:600, 代表短信发送被锁 600秒") })
    public Result<String> sms(@RequestParam(value = "phone", required = true) String phone,
            @RequestParam(value = "code", required = false) String code) {
        if (StringUtils.isBlank(phone) //
                || !Util.matche(phone, Util.REGEX_PHONE))
            return new Result<>(Code.BAD_REQ, "手机号无效");

        if (StringUtils.isNotBlank(code) && 6 != code.length())
            return new Result<>(Code.BAD_REQ, "图形验证码无效");

        return this.service.sendSms(true, phone, code);
    }

    @Override
    @ApiOperation(value = "验证码匹配")
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "type", value = "类型", required = true),
            @ApiImplicitParam(name = "key", value = "检查KEY", required = true),
            @ApiImplicitParam(name = "code", value = "验证码", required = true) })
    public Result<?> match(@RequestParam("type") Type type, @RequestParam("key") String key,
            @RequestParam("code") String code) {
        return this.service.match(type, key, code);
    }

}
