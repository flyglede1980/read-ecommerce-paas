package net.yitun.comon.website.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.yitun.basic.Code;
import net.yitun.basic.model.Result;
import net.yitun.basic.utils.PwdUtil.LEVEL;
import net.yitun.comon.CheckApi;
import net.yitun.comon.service.CheckService;

@Api(tags = "通用 辅助检查")
@RestController("basic.CheckApi")
public class CheckAction implements CheckApi {

    @Autowired
    protected CheckService service;

    @Override
    @ApiOperation(value = "密码强度")
    @ApiImplicitParam(name = "passwd", value = "密码, 正则规则: [a-zA-Z0-9]{6,32}", required = true, defaultValue = "aBc123") //
    @ApiResponses({ //
            @ApiResponse(code = Code.OK, message = "强度说明: 简单: EASY, 中等: MIDIUM, 强壮: STRONG, 非常强壮: VERY_STRONG, 超级强壮: EXTREMELY_STRONG") })
    public Result<LEVEL> checkPwd(@RequestParam(value = "passwd", required = false) String passwd) {
        LEVEL level = this.service.checkPwd(passwd);
        return new Result<>(level);
    }

    @Override
    @ApiOperation(value = "是否已登录", notes = "判断当前请求的终端是否已经登录，通过其提供的请求头中令牌判断, 返回Boolean, true: 已登录; false: 未登录") //
    @ApiResponses({ //
            @ApiResponse(code = Code.OK, message = "标识说明: true: 已登录; false: 未登录") })
    public Result<Boolean> checkIsLogin() {
        boolean isLogin = this.service.checkIsLogin();
        return new Result<Boolean>(Code.OK, isLogin ? "已登录" : "未登录", isLogin);
    }

}
