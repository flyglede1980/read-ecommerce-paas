package net.yitun.ioften.crm.service.impl;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import net.yitun.basic.Code;
import net.yitun.basic.amqp.support.AmqpSender;
import net.yitun.basic.cache.support.AtomicOperations;
import net.yitun.basic.dict.Sex;
import net.yitun.basic.dict.Status;
import net.yitun.basic.model.Result;
import net.yitun.basic.security.SecurityUtil;
import net.yitun.basic.utils.IdUtil;
import net.yitun.ioften.crm.conf.Conf;
import net.yitun.ioften.crm.dicts.Level;
import net.yitun.ioften.crm.dicts.Type;
import net.yitun.ioften.crm.entity.User;
import net.yitun.ioften.crm.model.user.join.Signup;
import net.yitun.ioften.crm.model.user.login.Login;
import net.yitun.ioften.crm.model.user.login.LosePwd;
import net.yitun.ioften.crm.model.user.login.SmsLogin;
import net.yitun.ioften.crm.repository.UserRepository;
import net.yitun.ioften.crm.service.SignupService;

@Slf4j
@Service("sys.SignupService")
public class SignupServiceImpl implements SignupService {

    @Autowired
    protected AmqpSender amqp;

    @Autowired
    protected UserRepository repository;

    @Value("${ioften.crm.login.lock-ttl: 1800}")
    protected long lockTTL = -1; // 登录异常锁定时长, 默认: 1800s

    @Value("${ioften.crm.login.fire-tips-times: 2}")
    protected long fireTipsTimes = -1; // 登录异常触发提示次数, 默认: 2

    @Value("${ioften.crm.login.fire-lock-times: 5}")
    protected long fireLockTimes = -1; // 登录异常触发锁定次数, 默认: 5

    @Autowired // 校验错误次数缓存器
    protected AtomicOperations<String, Long> atomicTimes;

    @Override
    public Result<?> login(Login model, HttpServletResponse response) {
        // 获取手机号, 密码
        Long failTtimes = null;
        String phone = model.getPhone(), timesKey = "login:fail:times" + phone;
        if (1 <= this.fireTipsTimes || 1 <= this.fireLockTimes) {
            failTtimes = this.atomicTimes.getIncr(timesKey); // 之前已经校验失败 ？次
            if (null != failTtimes && 1 <= this.fireLockTimes && this.fireLockTimes <= failTtimes)
                return new Result<>(Code.BIZ_ERROR, "登录太频繁, 请30分钟后再试", "USER_LOCKED:" + this.lockTTL);
        }

        User user = null;
        // 通过手机号提取用户信息
        if (null == (user = this.repository.findByPhone(phone))) {
            failTtimes = this.atomicTimes.incr(timesKey, 1, this.lockTTL, TimeUnit.SECONDS); // 累计一次当前登录校验已失败
            return new Result<>(Code.BIZ_ERROR, "手机号非注册用户");
        }

        // 判断登录密码是否匹配
        String passwd = model.getPasswd();
        if (!SecurityUtil.ENCODER.matches(passwd, user.getPasswd())) {
            failTtimes = this.atomicTimes.incr(timesKey, 1, this.lockTTL, TimeUnit.SECONDS); // 累计一次当前登录校验已失败
            return new Result<>(Code.BIZ_ERROR, "登录密码错误", failTtimes < this.fireTipsTimes ? null : "TIPS_LOSE_PWD");
        }

        // 密码验证通过后，移除登录错误计数器
        this.atomicTimes.delete(timesKey);

        // 判断用户是否被禁用/冻结
        if (Status.DISABLE == user.getStatus())
            return new Result<>(Code.BIZ_ERROR, "您账号已被禁用, 请联系平台客服", "USER_FREEZE");

        if (log.isInfoEnabled())
            log.info("<<< {}.login() phone:{}", this.getClass().getName(), phone);

        // 构建认证的令牌
        return this.bindLoginToken(user, response);
    }

    @Override
    public Result<?> loginBySms(SmsLogin model, HttpServletResponse response) {
        // 获取手机号
        User user = null;
        String phone = model.getPhone();

        // 在控制层需要实现对手机短信验证码的校验处理

        // 通过手机号提取用户信息
        if (null == (user = this.repository.findByPhone(phone)))
            return new Result<>(Code.BIZ_ERROR, "该手机号未注册");

        if (log.isInfoEnabled())
            log.info("<<< {}.loginBySms() phone:{}", this.getClass().getName(), phone);

        return this.bindLoginToken(user, response);
    }

    /**
     * 构建凭证信息
     * 
     * @param user
     * @param response
     * @return Result<?>
     */
    protected Result<?> bindLoginToken(User user, HttpServletResponse response) {
        long id = user.getId();
        String nick = user.getNick();

        // 默认普通用户
        Type type = user.getType();
        StringBuffer roles = new StringBuffer("USER");
        if (Type.PE == type) // 个人实名用户
            roles.append(",USER_").append(type.name());

        else if (Type.EN == type) // 企业实名用户
            roles.append(",USER_").append(type.name());

        else if (Type.EW == type) // 长见号实名用户
            roles.append(",USER_").append(type.name());

        // 构建认证令牌并绑定到Http响应中
        SecurityUtil.bindToken(String.valueOf(id), nick, roles.toString(), response);

        return new Result<>(type);
    }

    @Override
    @Transactional
    public Result<?> losePwd(LosePwd model) {
        // 获取手机号, 密码
        String phone = model.getPhone();
        // 对密码就行安全加密，避免明文存档
        String passwd = SecurityUtil.encode(model.getPasswd());
        User user = new User(null /* id */, phone, passwd, null /* payPasswd */, new Date(System.currentTimeMillis()));

        // 持久化用户的最新的密码
        if (!this.repository.modifyPwd(user))
            return new Result<>(Code.BIZ_ERROR, "修改密码失败");

        if (log.isInfoEnabled())
            log.info("<<< {}.losePwd() phone:{}", this.getClass().getName(), phone);

        return Result.OK;
    }

    // ==============================================================================
    @Override
    @Transactional
    public Result<?> signup(Signup model, HttpServletResponse response) {
        // 构建新注册用户信息
        long id = IdUtil.id();
        Date nowTime = new Date(System.currentTimeMillis());
        String phone = model.getPhone(), nick = String.valueOf(phone).substring(6); // 默认昵称为手机号后5位

        // 对密码就行安全加密，避免明文存档
        String passwd = SecurityUtil.encode(model.getPasswd());

        User user = new User(id, nick /* nick */, null /* name */
                , phone, passwd, null /* payPasswd */, Sex.N /* 默认未知 */, Type.N /* 默认未知, 认证后再改 */
                , Level.N /* 默认普通级 */, null /* city */, null /* birthday */ , null /* idcard */, null /* operator */
                , null /* intro */ , null /* device */, null /* invite */ , null /* categoryId */ ,
                null /* categoryName */
                , null /* subCategoryId */ , null /* subCategoryName */ , null /* remak */ , Status.ENABLE /* 默认正常 */,
                nowTime, nowTime);

        // 持久化用户注册信息
        if (!this.repository.create(user))
            return new Result<>(Code.BIZ_ERROR, "手机已被注册");

        // 注册成功后需要生成对应的访问令牌
        SecurityUtil.bindToken(String.valueOf(id), phone, "USER", response);

        // 下行消息通知用户注册成功以及判断是否为邀请用户并派发挖矿奖励
        this.amqp.syncSend(Conf.MQ_EXCHANGE, Conf.MQ_ROUTEKEY_USER_SIGNUP + ".byphone",
                new long[] { id, Long.valueOf(phone) });

        if (log.isInfoEnabled())
            log.info("<<< {}.signup() phone:{}", this.getClass().getName(), phone);

        return Result.OK;
    }

}
