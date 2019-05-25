package net.yitun.ioften.crm.service.impl;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;

import lombok.extern.slf4j.Slf4j;
import net.yitun.basic.Code;
import net.yitun.basic.model.Result;
import net.yitun.basic.model.vo.DeleteVo;
import net.yitun.basic.security.SecurityUtil;
import net.yitun.basic.utils.IdUtil;
import net.yitun.basic.utils.JsonUtil;
import net.yitun.ioften.crm.entity.Shield;
import net.yitun.ioften.crm.entity.vo.ShieldQueryVo;
import net.yitun.ioften.crm.model.shield.ShieldQuery;
import net.yitun.ioften.crm.repository.ShieldRepository;
import net.yitun.ioften.crm.service.ShieldService;
import net.yitun.ioften.crm.service.UserService;

@Slf4j
@Service("sys.ShieldService")
public class ShieldServiceImpl implements ShieldService {

    @Autowired
    protected UserService userService;

    @Autowired
    protected ShieldRepository repository;

    @Override
    public Page<Shield> query(ShieldQuery query) {
        Long uid = SecurityUtil.getId();
        ShieldQueryVo queryVo //
                = new ShieldQueryVo(query.getId(), uid, query.getSide(), query.getSideNick());
        return this.repository.query(queryVo);
    }

    @Override
    @Transactional
    public Result<?> stamp(Long target) {
        // 判断对方用户是否存在
        if (null == this.userService.get(target))
            return new Result<>(Code.BIZ_ERROR, "对方账号已不存在");

        Long id = IdUtil.id(), uid = SecurityUtil.getId();
        Date nowTime = new Date(System.currentTimeMillis());
        Shield shield = new Shield(id, uid, target, nowTime, nowTime);
        this.repository.create(shield);

        // TODO 保存成功后，需要将对方账号进行缓存处理，便于搜索、推荐时自动启用屏蔽

        return Result.OK;
    }

    @Override
    @Transactional
    public Result<?> delete(Long uid, Set<Long> ids) {
        DeleteVo delVo //
                = new DeleteVo(ids, uid, new Date(System.currentTimeMillis()));
        int size = this.repository.delete(delVo);

        // TODO 保存成功后，需要将对方账号移除缓存处理，便于搜索、推荐时自动放开屏蔽

        if (log.isInfoEnabled())
            log.info("<<< {}.delete() uid:{} ids:{} size:{}", this.getClass().getName(), uid, JsonUtil.toJson(ids), size);

        return Result.OK;
    }

}
