package net.yitun.ioften.pay.support;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.yitun.basic.utils.JsonUtil;
import net.yitun.ioften.pay.service.dto.DkResDto;

@Slf4j
@Setter
@Component("pay.BlockSupport")
@ConfigurationProperties(prefix = "block")
public class BlockSupport {

    /** 区块节点地址 */
    protected String host;

    /** 获取新区块地址接口 */
    protected String newAddr = "/common/address/%s";

    // RESTful Api 调用模板
    protected static RestTemplate restTmpl;

    @PostConstruct
    protected void init() {
        if (log.isInfoEnabled())
            log.info("{} init ...", this.getClass().getName());

        // 目前仅考虑单个节点，直接对接口地址进行预处理
        this.newAddr = this.host + this.newAddr;

        // 初始化 Api 调用模板
        BlockSupport.restTmpl = new RestTemplate(new SimpleClientHttpRequestFactory() {
            {
                this.setReadTimeout(1000); // 等待响应超时
                this.setConnectTimeout(600); // Socket连接超时
            }
        });
    }

    /**
     * 产生新区块地址
     * 
     * @param uid
     * @return String 新地址
     */
    public String newAddr(Long uid) {
        Object addr = null;
        String api = String.format(this.newAddr, uid);
        if (null == (addr = BlockSupport.invoke(api)))
            return null;

        if (log.isInfoEnabled())
            log.info("<<< {}.newAddr() success, uid:{}, addr:{}", BlockSupport.class.getName(), uid, addr);
        return String.valueOf(addr);
    }

    /**
     * 调用API
     *
     * @param api API
     * @return Object 接口响应数据
     */
    public static Object invoke(String api) {
        DkResDto res = null;
        try {
            res = BlockSupport.restTmpl.getForObject(api, DkResDto.class);
        } catch (Exception e) {
            log.error("<<< {}.invokeApi() failed, api:{}, error:{}", BlockSupport.class.getName(), api, e.toString());
            return null;
        }

        if (log.isInfoEnabled())
            log.info("<<< {}.invokeApi() success, api:{}, result:{}", BlockSupport.class.getName(), api, JsonUtil.toJson(res));
        if (null == res || 0 != res.getCode() || null == res.getData())
            return null;

        return res.getData();
    }
}
