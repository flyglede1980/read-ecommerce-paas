package net.yitun;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

public class ApplicationTest {

    String host = "http://127.0.0.1:8080/";

    RestTemplate restTmpl = new RestTemplate();

    @Test
    public void test() {

        List<MediaType> mTypes = new ArrayList<>();
        mTypes.add(MediaType.TEXT_PLAIN);
        mTypes.add(MediaType.APPLICATION_JSON_UTF8);

        FastJsonConfig config = new FastJsonConfig();
        config.setDateFormat("yyyy-MM-dd HH:mm:ss");
        config.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect);

        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        converter.setFastJsonConfig(config);
        converter.setSupportedMediaTypes(mTypes);
        restTmpl = new RestTemplateBuilder().additionalMessageConverters(converter).build();

//        ResponseEntity<Member> response = restTmpl.getForEntity(host + "/api/members/1", Member.class);
//        System.out.println(response.getStatusCodeValue() + ": " + response.getBody());
    }

}
