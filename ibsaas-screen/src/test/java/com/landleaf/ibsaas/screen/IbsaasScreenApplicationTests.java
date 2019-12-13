package com.landleaf.ibsaas.screen;


import com.landleaf.ibsaas.screen.util.RestTemplateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IbsaasScreenApplicationTests {

    @Test
    public void contextLoads() {
        String url = "http://ali-weather.showapi.com/gps-to-weather";
        Map<String, String> paramMap = new HashMap<String, String>(){{
            put("from", "5");
            put("lat","31.2377403799");
            put("lng", "121.3553827045");
        }};

        Map<String, String> headerMap = new HashMap<String, String>(){{
            put("Authorization", "APPCODE " + "411a73d2c0fc46d78020c6b7cff6b00f");

        }};
        String s = RestTemplateUtil.get(url, headerMap, paramMap);
        System.err.println(s);

    }

}
