package net.yitun.ioften.crm.service.impl;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;

import lombok.extern.slf4j.Slf4j;
import net.yitun.basic.Code;
import net.yitun.basic.exp.BizException;
import net.yitun.basic.model.Result;
import net.yitun.basic.security.SecurityUtil;
import net.yitun.basic.utils.JsonUtil;
import net.yitun.basic.utils.SetUtil;
import net.yitun.comon.CobjsApi;
import net.yitun.comon.model.cobj.Cobj;
import net.yitun.ioften.crm.conf.Constant;
import net.yitun.ioften.crm.entity.Identity;
import net.yitun.ioften.crm.entity.User;
import net.yitun.ioften.crm.entity.vo.user.UserQueryVo;
import net.yitun.ioften.crm.entity.vo.user.UserStatusModifyVo;
import net.yitun.ioften.crm.model.user.PwdModify;
import net.yitun.ioften.crm.model.user.UserModify;
import net.yitun.ioften.crm.model.user.UserQuery;
import net.yitun.ioften.crm.model.user.UserStatusModify;
import net.yitun.ioften.crm.repository.UserRepository;
import net.yitun.ioften.crm.service.UserService;

@Slf4j
@Service("sys.UserService")
@CacheConfig(cacheNames = Constant.CK_USER)
public class UserServiceImpl implements UserService {

    protected Cache cache;

    @Autowired
    protected CobjsApi cobjsApi;

    @Autowired
    protected UserRepository repository;

    @Autowired
    protected CacheManager cacheManager;

    @PostConstruct
    protected void init() {
        if (log.isInfoEnabled())
            log.info("{} init ...", this.getClass().getName());

        this.cache = this.cacheManager.getCache(Constant.CK_USER);
    }

    @Override
    @Cacheable(key = "#id")
    public User get(Long id) {
        return this.repository.get(id);
    }

    @Override
    public User lock(Long id) {
        return this.repository.lock(id);
    }

    @Override
    @Cacheable(key = "'invite:'+#invite")
    public User getByInvite(String invite) {
        return this.repository.findByInvite(invite);
    }

    @Override
    @Cacheable(key = "'phone:'+#phone")
    public User getByPhone(String phone) {
        return this.repository.findByPhone(phone);
    }

    @Override
    public Page<User> query(UserQuery query) {
        UserQueryVo queryVo = new UserQueryVo(query.getId(), query.getNick(), query.getName(), query.getPhone(),
                query.getCity(), query.getIdcard(), query.getOperator(), query.getInvite(), query.getDevice(),
                query.getCategoryId(), query.getSubCategoryId(), query.getStime(), query.getEtime(), query.getSex(),
                query.getType(), query.getLevel(), query.getStatus());
        return this.repository.query(queryVo);
    }

    @Override
    @Transactional
    public Result<?> modifyPwd(PwdModify model) {
        User oldUser = null;
        Long id = SecurityUtil.getId();
        if (null == (oldUser = this.repository.lock(id)))
            throw new BizException(Code.NOT_FOUND, "账户不存在");

        // 获取旧密码判断是否正确
        String oldPasswd = oldUser.getPasswd();
        if (StringUtils.isNotBlank(oldPasswd) // 原密码为空时直接忽略检查旧密码是否相同
                && !SecurityUtil.ENCODER.matches(model.getPasswd(), oldPasswd))
            return new Result<>(Code.BIZ_ERROR, "旧密码输入错误");

        // 对新密码就行加密处理后再更新
        Date nowTime = new Date(System.currentTimeMillis());
        String newPasswd = SecurityUtil.encode(model.getNewPasswd());
        User user = new User(null /* id */, oldUser.getPhone(), newPasswd, null /* payPasswd */, nowTime);

        // 持久化用户的最新的密码
        if (!this.repository.modifyPwd(user))
            return new Result<>(Code.BIZ_ERROR, "登录密码修改失败");

        return this.evictCache(oldUser);
    }

    @Override
    @Transactional
    public Result<?> modifyPhone(String phone) {
        User oldUser = null;
        Long id = SecurityUtil.getId();
        if (null == (oldUser = this.repository.lock(id)))
            throw new BizException(Code.NOT_FOUND, "账户不存在");

        // 判断新手号是否已被其他账号使用
        if (null != this.repository.findByPhone(phone))
            return new Result<>(Code.BIZ_ERROR, "新手机号已被使用");

        User user = new User(id, phone, new Date(System.currentTimeMillis()));
        if (!this.repository.modifyPhone(user))
            return new Result<>(Code.BIZ_ERROR, "新密码修改失败");

        return this.evictCache(oldUser);
    }

    @Override
    @Transactional
    public Result<?> modifyPayPwd(String payPwd) {
        User oldUser = null;
        Long id = SecurityUtil.getId();
        if (null == (oldUser = this.repository.lock(id)))
            throw new BizException(Code.NOT_FOUND, "账户不存在");

        // 对新的支付密码进行加密处理
        String newPayPwd = SecurityUtil.encode(payPwd);
        Date nowTime = new Date(System.currentTimeMillis());
        User user = new User(id, null /* phone */, null /* newPasswd */, newPayPwd /* newPayPwd */, nowTime);

        // 持久化用户的最新支付的密码
        if (!this.repository.modifyPayPwd(user))
            return new Result<>(Code.BIZ_ERROR, "支付密码修改失败");

        return this.evictCache(oldUser);
    }

    @Override
    @Transactional
    public Result<?> modifyInvite(String invite) {
        User user = null;
        Long id = SecurityUtil.getId();
        if (null == (user = this.repository.lock(id)))
            throw new BizException(Code.NOT_FOUND, "账户不存在");

        user.setInvite(invite);
        user.setMtime(new Date(System.currentTimeMillis()));

        // 派单邀请码是否已经重复
        if (this.repository.existsInvite(user) //
                || !this.repository.modifyInvite(user))
            return new Result<>(Code.BIZ_ERROR, "邀请码更新失败");

        return this.evictCache(user);
    }

    @Override
    @Transactional
    public Result<?> modifyDatum(UserModify model) {
        User _user = null;
        Long id = SecurityUtil.getId();
        if (null == (_user = this.repository.lock(id)))
            throw new BizException(Code.NOT_FOUND, "账户不存在");

        Date nowTime = new Date(System.currentTimeMillis());
        User user = new User(id, model.getNick(), model.getSex(), model.getCity(), model.getBirthday(), model.getIntro(),
                null /* invite */, model.getDevice(), nowTime);

        // 持久化用户的资料
        if (!this.repository.modifyDatum(user))
            return new Result<>(Code.BIZ_ERROR, "资料修改失败");

        String header = null;
        // 对用户新提交的照片就行固化处理
        if (StringUtils.isNotBlank(header = model.getHeader())) {
            Result<?> result = null;
            Cobj cobj = new Cobj(header, "header/" + id); // 放到公共取域
            if (null == (result = this.cobjsApi.sure(cobj)) || !result.ok()) {
                if (log.isInfoEnabled())
                    log.info("<<< {}.modifyDatum() sure cobj faild, {}", this.getClass().getName(), JsonUtil.toJson(result));
                throw new BizException(Code.BIZ_ERROR, "头像保存失败");
            }
        }

        return this.evictCache(_user);
    }

    @Override
    @Transactional
    public boolean modifyIdentity(Identity identity) {
        User oldUser = null;
        Long uid = identity.getUid();
        if (null == (oldUser = this.repository.lock(uid)))
            return false;

        // 将认证的信息覆盖到用户资料中
        User user = oldUser.clone();
        user.setCity(identity.getCity());
        user.setName(identity.getName());
        // user.setPhone(identity.getPhone()); //  暂时不支持认证时同步修改登录手机号
        user.setType(identity.getType());
        user.setIdcard(identity.getIdcard());
        user.setOperator(identity.getOperator());

        user.setCategoryId(identity.getCategoryId());
        user.setCategoryName(identity.getCategoryName());
        user.setSubCategoryId(identity.getSubCategoryId());
        user.setSubCategoryName(identity.getSubCategoryName());
        user.setMtime(identity.getMtime());

        if (log.isInfoEnabled())
            log.info(">>> {}.modifyIdentity() json:{}", this.getClass().getName(), JsonUtil.toJson(identity));
        boolean result = this.repository.modifyIdentity(user);

        this.evictCache(oldUser);
        return result;
    }

    @Override
    @Transactional
    public Result<?> modifyStatus(UserStatusModify model) {
        UserStatusModifyVo modifyVo //
                = new UserStatusModifyVo(model.getIds(), model.getStatus(), model.getRemark(),
                        new Date(System.currentTimeMillis()));

        this.repository.modifyStatus(modifyVo);
        // TODO 如果引入缓存需要将对应ID的用户资料缓存更新

        if (log.isInfoEnabled())
            log.info("<<< {}.modifyStatus() id:{} status:{} ids:{}", this.getClass().getName(), SecurityUtil.getId(),
                    model.getStatus(), SetUtil.toString(model.getIds()));

        for (Long id : model.getIds())
            this.cache.evict("id." + id);

        return Result.OK;
    }

    /**
     * 清除对应的缓存
     * 
     * @param user 旧用户信息
     */
    protected Result<?> evictCache(User user) {
        this.cache.evict(user.getId());
        this.cache.evict("invite:" + user.getInvite());
        this.cache.evict("phone:" + user.getPhone());
        return Result.OK;
    }

}
