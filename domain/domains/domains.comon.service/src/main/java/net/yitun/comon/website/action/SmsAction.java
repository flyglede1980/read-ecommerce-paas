package net.yitun.comon.website.action;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.yitun.basic.Code;
import net.yitun.basic.model.Result;
import net.yitun.basic.utils.SetUtil;
import net.yitun.comon.SmsApi;
import net.yitun.comon.model.sms.SmsSended;
import net.yitun.comon.service.SmsService;

@Api(tags = "通用 短信发送")
@RestController("basic.SmsApi")
public class SmsAction implements SmsApi {

    @Autowired
    protected SmsService service;

    @Override
    public Result<List<SmsSended>> send(@RequestParam("content") String content, //
            @RequestParam("phone") String... phones) {
        return this.send(content, SetUtil.asSet(phones));
    }

    @Override
    @ApiOperation(value = "短信发送")
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "phone", value = "手机号, 限制一次1~1024个号码", required = true, allowMultiple = true), //
            @ApiImplicitParam(name = "content", value = "短信内容, 长度为8~256个字符", required = true) })
    public Result<List<SmsSended>> send(@RequestParam("content") String content,
            @RequestParam("phone") Collection<String> phones) {
        if (null == phones || phones.isEmpty() || 1024 < phones.size())
            return new Result<>(Code.BAD_REQ, "手机号无效");

        if (StringUtils.isBlank(content) || 8 > content.length() || 256 < content.length())
            return new Result<>(Code.BAD_REQ, "短信内容无效");

        Set<String> _phones = null;
        if (phones instanceof Set)
            _phones = (Set<String>) phones;
        else {
            _phones = new HashSet<>(phones.size());
            for (String phone : phones)
                _phones.add(phone);
        }

        return this.service.send(_phones, content);
    }

}
