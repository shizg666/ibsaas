package com.landleaf.ibsaas.screen.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 * @author Lokiy
 * @date 2019/12/12 19:18
 * @description:
 */
@Component
public class RestTemplateUtil {
    private static RestTemplateUtil restTemplateUtil;

    @PostConstruct
    public void init() {
        restTemplateUtil = this;
    }

    private static final RestTemplate INSTANCE = new RestTemplate();

    public static RestTemplate getInstance() {
        return RestTemplateUtil.INSTANCE;
    }

    public static String get(String url){
        ResponseEntity<String> entity = RestTemplateUtil.getInstance().
                getForEntity(url, String.class);
        return entity.getBody();
    }

    public static <T> String post(String url, T data) throws Exception {
        //复杂构造函数的使用
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        // 设置超时
        requestFactory.setConnectTimeout(10000);
        requestFactory.setReadTimeout(10000);


        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept-Charset", MediaType.APPLICATION_JSON.toString());

        HttpEntity<T> object = new HttpEntity<>(data, headers);
        //利用复杂构造器可以实现超时设置，内部实际实现为 HttpClient
        RestTemplate restTemplate = RestTemplateUtil.getInstance();
        restTemplate.setRequestFactory(requestFactory);
        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(url, object, String.class);
        String result = responseEntity.getBody();
        return result;
    }


}
