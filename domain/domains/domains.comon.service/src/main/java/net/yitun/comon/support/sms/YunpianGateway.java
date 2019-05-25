package net.yitun.comon.support.sms;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;
import net.yitun.basic.utils.IoUtil;
import net.yitun.basic.utils.JsonUtil;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * <pre>
 * <b>云片短信网关.</b>
 * <b>Description:</b>
 * https://github.com/yunpian/yunpian-java-sdk
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月9日 下午8:30:55 LH
 *         new file.
 * </pre>
 */
@Slf4j
@Component("sms.YunpianGateway")
public class YunpianGateway implements SmsGateway, InitializingBean {

    @Value("${basic.sms.yunpian.host: unknown}")
    protected String host; // 网关地址

    @Value("${basic.sms.yunpian.apikey: unknown}")
    protected String apikey; // 应用KEY

    protected static final Pattern ERROR_PATTERN = Pattern
            .compile("<response><error>(-?\\d+)</error><message>(.*[\\\\u4e00-\\\\u9fa5]*)</message></response>");

    protected OkHttpClient client;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.client = new OkHttpClient.Builder() //
                .writeTimeout(5, TimeUnit.SECONDS) //
                .readTimeout(15, TimeUnit.SECONDS) //
                .connectTimeout(5, TimeUnit.SECONDS) //
                .build(); // 初始化Cient配置，设置各超时参数
    }

    @Override
    public String type() {
        return "YunPian";
    }

    public String send(String mobile, String content) {
        RequestBody body = new FormBody.Builder() //
                .add("text", content) //
                .add("mobile", mobile) //
                .add("apikey", this.apikey).build();

        Request request = new Request.Builder() //
                .url(this.host).addHeader("Accept", "application/json;charset=utf-8") //
                .post(body).build();

        String result = null;
        Response response = null;
        Call call = this.client.newCall(request);
        try {
            // 执行调用远端接口
            response = call.execute();
            result = response.body().string();

            // result = {"code":5,"http_status_code":400,"msg":"未找到匹配的模板","detail":"未自动匹配到合适的模板"}
            // result = {"code":15,"http_status_code":400,"msg":"签名不匹配","detail":"签名 【亿豚网】 与设置的签名不匹配"}
            // result = {"code":0,"msg":"发送成功","count":1,"fee":0.05,"unit":"RMB","mobile":"13102393339","sid":27166999062}
        } catch (IOException e) {
            log.error("<<< {}.send() {}", this.getClass().getName(), e.toString());
            return e.toString();

        } finally {
            IoUtil.close(response);
        }

        // 记录短信发送结果，便于后期统计分析
        if (log.isInfoEnabled())
            log.info("<<< {}.send() {}", this.getClass().getName(), result);

        Integer code = null;
        // 判断发送后反馈的结果
        JSONObject json = JsonUtil.toBean(result);
        return (null != json && null != (code = json.getInteger("code")) && 0 == code) ? null
                : (null == json) ? null : json.getString("msg");
    }

}
