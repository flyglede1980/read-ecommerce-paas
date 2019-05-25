package net.yitun.comon.support.store;

import java.io.InputStream;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.yitun.basic.utils.SetUtil;

/**
 * <pre>
 * <b>云存储支持.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月20日 上午11:45:19 LH
 *         new file.
 * </pre>
 */
@Slf4j
@Component("store.StoreSupport")
public class StoreSupport {

    @Autowired
    @Qualifier("store.ObsGateway")
    protected StoreGateway gateway; // 云存储网关

    @Value("${basic.store.bucket: cj-tmp}")
    protected String bucket = "cj-tmp"; // 临时存储桶

    protected Set<String> privClassifys; // 私有分类, 需要将对象放到私有桶中

    @Value("${basic.store.expire-time: 30}")
    protected long expireTime = 30; // 临时URL过期时间, 默认: 30s

    @Value("${basic.store.resure-times: 1}")
    protected long resureTimes = 1; // 临时文件重复确认次数，默认: 1

    /**
     * 是否为公共对象
     * 
     * @param key
     * @return boolean
     */
    public boolean isPub(String key) {
//        if (IocUtil.isDebug())
//            key = RegExUtils.replaceFirst(key, "_/", "");

        String classify //
                = StringUtils.substringBefore(key, "/");
        return !this.privClassifys.contains(classify);
    }

    @Value("${basic.store.priv-classify: my}")
    public void setPrivClassifys(String privClassifys) {
        this.privClassifys = SetUtil.split(privClassifys);
    }

    public String dns(String key) {
        boolean isPub = this.isPub(key);
        return this.gateway.dns(key, isPub);
    }

    public String del(String key) {
        boolean isPub = this.isPub(key);
        return this.gateway.del(key, isPub);
    }

    public String view(String key) {
        boolean isPub = this.isPub(key);
        return this.gateway.view(key, isPub, this.expireTime);
    }

    public String sure(String key, String newKey) {
        if (log.isDebugEnabled())
            log.debug(">>> {}.sure() by {} {}->{}" //
                    , this.getClass().getName(), this.gateway.type(), key, newKey);

        boolean isPub = this.isPub(newKey);
        return this.gateway.sure(key, isPub, newKey, this.bucket);
    }

    public String store(String key, InputStream input) {
        boolean isPub = this.isPub(key);
        return this.gateway.store(key, isPub, input);

        // ByteArrayOutputStream output = new ByteArrayOutputStream();
        // InputStream input = new ByteArrayInputStream(output.toByteArray());
    }

}
