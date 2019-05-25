package net.yitun.ioften.sys.service.impl;

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
import net.yitun.ioften.sys.entity._Tmp;
import net.yitun.ioften.sys.entity.vo._TmpQueryVo;
import net.yitun.ioften.sys.model._tmp._TmpCreate;
import net.yitun.ioften.sys.model._tmp._TmpModify;
import net.yitun.ioften.sys.model._tmp._TmpQuery;
import net.yitun.ioften.sys.repository._TmpRepository;
import net.yitun.ioften.sys.service._TmpService;

@Slf4j
@Service("sys._TmpService")
public class _TmpServiceImpl implements _TmpService {

    @Autowired
    protected _TmpRepository repository;

    @Override
    public _Tmp get(Long id) {
        return this.repository.get(id);
    }

    @Override
    public Page<_Tmp> query(_TmpQuery query) {
        _TmpQueryVo queryVo = new _TmpQueryVo(query.getId(), //
                query.getName(), query.getModifier(), query.getStatuss(), query.getStime(), query.getEtime());

        return this.repository.query(queryVo);
    }

    @Override
    @Transactional
    public Result<?> create(_TmpCreate model) {
        long id = IdUtil.id();
        String modifier = SecurityUtil.getName();
        Date nowTime = new Date(System.currentTimeMillis());
        _Tmp config = new _Tmp(id, model.getName(), modifier, model.getStatus(), nowTime, nowTime);

        if (!this.repository.create(config))
            return new Result<>(Code.BIZ_ERROR, "新建失败, Tmp是否重复");

        return Result.OK;
    }

    @Override
    @Transactional
    public Result<?> modify(_TmpModify model) {
        String modifier = SecurityUtil.getName();
        Date nowTime = new Date(System.currentTimeMillis());
        _Tmp config = new _Tmp(model.getId(), model.getName(), modifier, model.getStatus(), null, nowTime);

        if (!this.repository.modify(config))
            return new Result<>(Code.BIZ_ERROR, "修改失败, Tmp是否存在");

        return Result.OK;
    }

    @Override
    @Transactional
    public Result<?> delete(Set<Long> ids) {
        String modifier = SecurityUtil.getName();
        DeleteVo delVo = new DeleteVo(ids, modifier, new Date(System.currentTimeMillis()));

        int size = this.repository.delete(delVo);
        if (log.isInfoEnabled())
            log.info("<<< {}.delete() uid:{} ids:{} size:{}", this.getClass().getName(), SecurityUtil.getId(),
                    JsonUtil.toJson(ids), size);

        return Result.OK;
    }

}
