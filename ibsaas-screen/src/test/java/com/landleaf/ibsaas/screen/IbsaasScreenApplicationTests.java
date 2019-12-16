package com.landleaf.ibsaas.screen;


import com.landleaf.ibsaas.common.utils.HlVlUtil;
import com.landleaf.ibsaas.common.utils.date.CalendarUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IbsaasScreenApplicationTests {

    @Test
    public void aaaa() {
//        String url = "http://ali-weather.showapi.com/gps-to-weather";
//        Map<String, String> paramMap = new HashMap<String, String>(){{
//            put("from", "5");
//            put("lat","31.2377403799");
//            put("lng", "121.3553827045");
//        }};
//
//        Map<String, String> headerMap = new HashMap<String, String>(){{
//            put("Authorization", "APPCODE " + "411a73d2c0fc46d78020c6b7cff6b00f");
//
//        }};
//        String s = RestTemplateUtil.get(url, headerMap, paramMap);
//        System.err.println(s);

    }


    @Test
    public void xs(){

        LocalDateTime now = LocalDateTime.now();
        Date endMonth = CalendarUtil.localDate2Date(LocalDate.of(now.getYear(), now.getMonth(), 1));

        Date startMonth = CalendarUtil.offsetDate(endMonth, -11, ChronoUnit.MONTHS);
        //月度坐标
        List<String> xs = HlVlUtil.getXs(startMonth, endMonth, 3);

        System.out.println(xs);
    }
}
