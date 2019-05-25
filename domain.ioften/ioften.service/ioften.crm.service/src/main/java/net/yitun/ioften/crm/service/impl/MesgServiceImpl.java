package net.yitun.ioften.crm.service.impl;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;

import lombok.extern.slf4j.Slf4j;
import net.yitun.basic.dict.YesNo;
import net.yitun.basic.security.SecurityUtil;
import net.yitun.basic.utils.IdUtil;
import net.yitun.basic.utils.SetUtil;
import net.yitun.ioften.cms.ShowApi;
import net.yitun.ioften.crm.UserApi;
import net.yitun.ioften.crm.dicts.MesgType;
import net.yitun.ioften.crm.entity.Mesg;
import net.yitun.ioften.crm.entity.vo.mesg.MesgQueryVo;
import net.yitun.ioften.crm.entity.vo.mesg.MesgStatusModifyVo;
import net.yitun.ioften.crm.model.mesg.MesgQuery;
import net.yitun.ioften.crm.model.mesg.MesgStore;
import net.yitun.ioften.crm.model.mesg.MyMesgTotal;
import net.yitun.ioften.crm.repository.MesgRepository;
import net.yitun.ioften.crm.service.MesgService;

@Slf4j
@Service("sys.MesgService")
public class MesgServiceImpl implements MesgService {

    @Autowired
    protected UserApi userApi;

    @Autowired
    protected ShowApi showApi;

    @Autowired
    protected MesgRepository repository;

    @Override
    public Mesg get(Long id) {
        Mesg mesg = this.repository.get(id);
        return this.attach(mesg);
    }

    @Override
    public Page<Mesg> query(MesgQuery query) {
        MesgQueryVo queryVo //
                = new MesgQueryVo(query.getUid(), query.getType());
        Page<Mesg> page = this.repository.query(queryVo);

        page.stream().forEach(mesg -> this.attach(mesg));

        return page;
    }

    @Override
    public MyMesgTotal myTotal(Long uid) {
        int total = this.repository.countByStatus(uid, YesNo.NO);
        return new MyMesgTotal(0 < total, 0 < total ? total : null);
    }

    @Override
    @Transactional
    public boolean store(MesgStore model) {
        Date nowTime = new Date(System.currentTimeMillis());

        Mesg mesg = null;
        Integer times = model.getTimes();
        MesgType type = model.getType();
        Long actor = model.getActor(), target = model.getTarget();
        // 如果是点赞类消息，需要合并统计
        if (type == MesgType.ENJOY) {
            times = 1;
            if (null != (mesg = this.repository.lockByTypeActorTarget(type, actor, target))) {
                mesg.setMtime(nowTime);
                mesg.setTimes(mesg.getTimes() + 1); // 点赞次数加1
            }
        }

        if (null == mesg) {
            // 将消息就行持久化
            mesg = new Mesg(IdUtil.id(), model.getUid(), //
                    type, actor, target, model.getContent(), times, YesNo.NO, nowTime, nowTime);
            if (!this.repository.create(mesg))
                return false;

        } else {
            if (!this.repository.modify(mesg))
                return false;
        }

        // TODO 极光推送到用户终端告知有新消息

        return true;
    }

    @Override
    @Transactional
    public boolean viewed(Set<Long> ids) {
        Long uid = SecurityUtil.getId();
        MesgStatusModifyVo modifyVo //
                = new MesgStatusModifyVo(uid, ids, YesNo.YES, new Date(System.currentTimeMillis()));

        this.repository.viewed(modifyVo);
        if (log.isInfoEnabled())
            log.info("<<< {}.viewed() uid:{} ids:{}", this.getClass().getName(), SecurityUtil.getId(), SetUtil.toString(ids));

        return true;
    }

    protected Mesg attach(Mesg mesg) {
        if (null == mesg)
            return mesg;

        MesgType type = mesg.getType();
        if (type == MesgType.ENJOY && IdUtil.MIN < mesg.getActor())
            mesg.setActorUser(this.userApi.nick(mesg.getActor()).getData());

        if (type == MesgType.ENJOY && IdUtil.MIN < mesg.getTarget())
            mesg.setTargetRefer(this.showApi.major(mesg.getTarget()).getData());

        return mesg;
    }

}
