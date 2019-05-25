package net.yitun.comon.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.yitun.basic.Code;
import net.yitun.basic.model.Result;
import net.yitun.comon.model.sms.SmsSended;
import net.yitun.comon.service.SmsService;
import net.yitun.comon.support.sms.SmsSupport;

@Service("comon.SmsService")
public class SmsServiceImpl implements SmsService {

    @Autowired // 短信网关支撑
    protected SmsSupport sms;

    @Override
    public Result<List<SmsSended>> send(Set<String> phones, String content) {
        boolean isOk = true;
        List<SmsSended> sendeds //
                = new ArrayList<>(phones.size());

        for (String phone : phones) {
            String error = this.sms.send(phone, content);

            sendeds.add(new SmsSended(phone, content, error));
            if (null != error) // 发送有错误输出，说明有发送失败
                isOk = false;
        }

        return new Result<>(isOk ? Code.OK : Code.BIZ_ERROR, isOk ? null : "部分发送失败", sendeds);
    }

}
