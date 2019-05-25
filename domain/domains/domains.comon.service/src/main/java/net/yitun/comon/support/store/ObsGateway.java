package net.yitun.comon.support.store;

import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.HttpMethodEnum;
import com.obs.services.model.ObjectMetadata;
import com.obs.services.model.TemporarySignatureRequest;
import com.obs.services.model.TemporarySignatureResponse;

import lombok.extern.slf4j.Slf4j;
import net.yitun.basic.utils.IoUtil;
import net.yitun.comon.conf.ObsConfig;

/**
 * <pre>
 * <b>华为OBS存储.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月20日 上午11:54:01 LH
 *         new file.
 * </pre>
 */
@Slf4j
@Component("store.ObsGateway")
public class ObsGateway implements StoreGateway {

    @Autowired
    protected ObsConfig conf;

    protected ThreadLocal<ObsClient> clients;

    @PostConstruct
    protected void init() {
        if (log.isInfoEnabled())
            log.info("{} init ...", this.getClass().getName());

        this.clients = new ThreadLocal<>();
    }

    public void clean() {
        this.clients.remove();
        // IoUtil.close(getClient()); // 暂时不进行关闭处理
    }

    @Override
    public String type() {
        return "hw";
    }

    public ObsClient getClient() {
        ObsClient client;
        if (null == (client = this.clients.get()))
            this.clients.set( // 私有和公有共用同一套密鈅
                    client = new ObsClient(this.conf.getAccessKey(), this.conf.getAccessSecret(), this.conf.getEndpoint()));

        return client;
    }

    @Override
    public String del(String key, boolean isPub) {
        ObsClient client = getClient();

        // 确认将其放置的桶
        String bucket = !isPub ? this.conf.getBucket() : this.conf.getPubBucket();
        try {
            client.deleteObject(bucket, key);

        } catch (ObsException e) {
            if (404 != e.getResponseCode()) {
                log.error("<<< {}.delete() delete key: {} failed:{}, {}" //
                        , this.getClass().getName(), key, e.getResponseCode(), e.getMessage());
                return "删除失败, " + e.getMessage();
            }
        }

        return null;
    }

    @Override
    public String dns(String key, boolean isPub) {
        // 确认将其放置的桶
        String bucket = !isPub ? this.conf.getBucket() : this.conf.getPubBucket();
        String endpoint = !isPub ? this.conf.getEndpoint() : this.conf.getPubEndpoint();

        return String.format("%s://%s.%s", this.conf.getSchema(), bucket, endpoint);
    }

    @Override
    public String view(String key, boolean isPub, long expireTime) {
        if (isPub) // 公共对象直接返回路径
            return String.format("%s://%s.%s/%s" //
                    , this.conf.getSchema(), this.conf.getPubBucket(), this.conf.getPubEndpoint(), key);

        ObsClient client = getClient();

        // 私有文件需要生产临时访问路径
        TemporarySignatureRequest request //
                = new TemporarySignatureRequest(HttpMethodEnum.GET, this.conf.getBucket(), key, null, expireTime);
        TemporarySignatureResponse response = client.createTemporarySignature(request);

        return response.getSignedUrl(); // 获取临时的访问URL
    }

    @Override
    public String sure(String key, boolean isPub, String newKey, String srcBucket) {
        ObsClient client = getClient();

        // 确认将其放置到那个目标桶中
        String dstBucket = !isPub ? this.conf.getBucket() : this.conf.getPubBucket();
        try {
            // 目前仅实现同区域桶免费移动，跨区存在计费暂不考虑
            client.copyObject(srcBucket, key, dstBucket, newKey);
            client.deleteObject(srcBucket, key);

        } catch (ObsException e) {
            if (404 == e.getResponseCode())
                return "对象不存在";

            log.error("<<< {}.sure() sure key: {} failed:{}, {}" //
                    , this.getClass().getName(), key, e.getResponseCode(), e.getMessage());
            return "确认失败, " + e.getMessage();
        }

        return null;
    }

    @Override
    public String store(String key, boolean isPub, InputStream input) {
        // 存储对象的Meta信息
        ObjectMetadata meta = null;
        // meta = new ObjectMetadata();
        // meta.setContentLength(file.getSize());
        // meta.setContentType(file.getContentType());
        // meta.addUserMetadata("name", file.getName());

        // 确认将其放置到那个目标桶中
        String dstBucket = !isPub ? this.conf.getBucket() : this.conf.getPubBucket();

        // 执行文件上传工作, 选择文件存储的桶
        ObsClient client = getClient();
        try {
            client.putObject(dstBucket, key, input, meta);

        } catch (ObsException e) {
            log.error("<<< {}.store() store key: {} failed:{}, {}" //
                    , this.getClass().getName(), key, e.getResponseCode(), e.getMessage());
            return "确认失败, " + e.getMessage();

        } finally {
            IoUtil.close(input);
        }

        return null;
    }

}
