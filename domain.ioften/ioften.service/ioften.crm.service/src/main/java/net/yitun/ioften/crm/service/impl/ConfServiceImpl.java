package net.yitun.ioften.crm.service.impl;

import org.springframework.stereotype.Service;

import net.yitun.ioften.crm.service.ConfService;

@Service("crm.ConfService")
public class ConfServiceImpl implements ConfService {

    @Override
    public String buildInviteContent(String nick) {
        return "【长鉴】" + nick + " 邀请你加入长鉴，一起品鉴资讯大餐！";
    }

}
