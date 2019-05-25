package net.yitun.ioften.sys.service.impl;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

import lombok.extern.slf4j.Slf4j;
import net.yitun.basic.Code;
import net.yitun.basic.dict.Status;
import net.yitun.basic.model.Result;
import net.yitun.basic.security.SecurityUtil;
import net.yitun.ioften.sys.conf.Constant;
import net.yitun.ioften.sys.entity.Admin;
import net.yitun.ioften.sys.entity.vo.AdminQueryVo;
import net.yitun.ioften.sys.model.admin.AdminQuery;
import net.yitun.ioften.sys.model.admin.Logins;
import net.yitun.ioften.sys.repository.AdminRepository;
import net.yitun.ioften.sys.service.AdminService;

@Slf4j
@Service("sys.AdminService")
@CacheConfig(cacheNames = Constant.CK_ADMIN)
public class AdminServiceImpl implements AdminService {

    @Autowired
    protected AdminRepository repository;

    @Override
    @Cacheable(key = "#id")
    public Admin get(Long id) {
        return this.repository.get(id);
    }

    @Override
    public Page<Admin> query(AdminQuery query) {
        AdminQueryVo queryVo //
                = new AdminQueryVo(query.getId(), query.getName(), query.getAccount(), query.getPhone(), query.getEmail(),
                        query.getStime(), query.getEtime(), query.getStatus());
        return this.repository.query(queryVo);
    }

    @Override
    public Result<?> login(Logins model, HttpServletResponse response) {
        Admin admin = null;
        String account = model.getAccount();
        if (null == (admin = this.repository.findByAccount(account)))
            return new Result<>(Code.BIZ_ERROR, "账号不存在");

        // 判断登录密码是否匹配
        String passwd = model.getPasswd();
        if (!SecurityUtil.ENCODER.matches(passwd, admin.getPasswd()))
            return new Result<>(Code.BIZ_ERROR, "登录密码错误");

        // 判断账号是否被禁用/冻结
        if (Status.DISABLE == admin.getStatus())
            return new Result<>(Code.BIZ_ERROR, "您账号已被禁用, 请联系平台客服", "USER_FREEZE");

        // 默认普通用户
        StringBuffer roles = new StringBuffer("ADMIN");
        roles.append(",").append(admin.getRoles());

        // 构建认证令牌并绑定到Http响应中
        SecurityUtil.bindToken(String.valueOf(admin.getId()), account, roles.toString(), response);

        if (log.isInfoEnabled())
            log.info("<<< {}.login() account:{}", this.getClass().getName(), account);

        return Result.OK;
    }

}
