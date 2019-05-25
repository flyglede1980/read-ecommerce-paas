package net.yitun.comon.service.impl;

import org.springframework.stereotype.Service;

import net.yitun.basic.security.SecurityUtil;
import net.yitun.basic.utils.PwdUtil;
import net.yitun.basic.utils.PwdUtil.LEVEL;
import net.yitun.comon.service.CheckService;

@Service("comon.CheckService")
public class CheckServiceImpl implements CheckService {

    @Override
    public LEVEL checkPwd(String passwd) {
        return PwdUtil.level(passwd);
    }

    @Override
    public boolean checkIsLogin() {
        return null != SecurityUtil.getId();
    }

}
