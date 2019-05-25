package net.yitun.comon.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.yitun.basic.Code;
import net.yitun.basic.model.Result;
import net.yitun.comon.model.cobj.Cobj;
import net.yitun.comon.model.cobj.CobjDetail;
import net.yitun.comon.model.cobj.CobjModify;
import net.yitun.comon.model.cobj.CobjStream;
import net.yitun.comon.service.CobjsService;
import net.yitun.comon.support.store.StoreSupport;

@Service("comon.CobjsService")
public class CobjsServiceImpl implements CobjsService {

    @Autowired // 对象存储支撑
    protected StoreSupport store;

    @Override
    public String dns(String path) {
        return this.store.dns(path);
    }

    @Override
    public String view(String path) {
        return this.store.view(path);
    }

    @Override
    public Result<Collection<CobjDetail>> sure(Collection<Cobj> objs) {
        boolean isOk = true;
        List<CobjDetail> sureCobjs //
                = new ArrayList<>(objs.size());

        for (Cobj cobj : objs) {
            String key = cobj.getKey();
            String newKey = cobj.getNewKey();
//            if (IocUtil.isDebug())
//                newKey = "_/" + newKey;

            String error = this.store.sure(key, newKey);
            sureCobjs.add(new CobjDetail(key, newKey, error));
            if (null != error) // 确认有错误输出，说明有确认失败
                isOk = false;
        }

        return new Result<>(isOk ? Code.OK : Code.BIZ_ERROR, isOk ? null : "部分确认失败", sureCobjs);
    }

    @Override
    public Result<Collection<CobjDetail>> store(Collection<CobjStream> streams) {
        boolean isOk = true;
        List<CobjDetail> storeCobjs //
                = new ArrayList<>(streams.size());

        for (CobjStream stream : streams) {
            String key = stream.getKey();
            InputStream input = stream.getStream();
            String newKey = stream.getKey();
//            if (IocUtil.isDebug())
//                newKey = "_/" + newKey;

            String error = this.store.store(newKey, input);
            storeCobjs.add(new CobjDetail(key, newKey, error)); // 注意修旧KEY都是一样的
            if (null != error) // 存储有错误输出，说明有存储失败
                isOk = false;
        }

        return new Result<>(isOk ? Code.OK : Code.BIZ_ERROR, isOk ? null : "部分存储失败", storeCobjs);
    }

    @Override
    public Result<Collection<CobjDetail>> parse(Collection<String> keys) {
        List<CobjDetail> viewCobjs //
                = new ArrayList<>(keys.size());

        for (String key : keys) {
            String newKey = this.store.view(key);
            viewCobjs.add(new CobjDetail(key, newKey, null));
        }

        return new Result<>(viewCobjs);
    }

    @Override
    public Result<Collection<CobjDetail>> delete(Collection<String> keys) {
        List<CobjDetail> delCobjs //
                = new ArrayList<>(keys.size());

        boolean isOk = true;
        for (String key : keys) {
            String error = this.store.del(key);
            delCobjs.add(new CobjDetail(key, null, error));
            if (null != error) // 删除有错误输出，说明有删除失败
                isOk = false;
        }

        return new Result<>(isOk ? Code.OK : Code.BIZ_ERROR, isOk ? null : "部分删除失败", delCobjs);
    }

    @Override
    public Result<Collection<CobjDetail>> modify(CobjModify model) {

        boolean isOk = true;
        List<CobjDetail> cobjs = new ArrayList<>();

        Collection<Cobj> sures = model.getSures();
        if (null != sures && 0 != sures.size()) {
            Result<Collection<CobjDetail>> result = this.sure(model.getSures());
            if (null != result && !result.ok()) {
                isOk = false;
                if (null != result && null != result.getData())
                    cobjs.addAll(result.getData());
            }
        }

        Collection<String> deletes = model.getDeletes();
        if (null != deletes && 0 != deletes.size()) {
            Result<Collection<CobjDetail>> result = this.delete(deletes);
            if (null != result && !result.ok()) {
                isOk = false;
                if (null != result && null != result.getData())
                    cobjs.addAll(result.getData());
            }
        }

        return new Result<>(isOk ? Code.OK : Code.BIZ_ERROR, isOk ? null : "部分编辑失败", cobjs);
    }

}
