package com.landleaf.ibsaas.common.utils.sms;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.landleaf.ibsaas.common.utils.date.DateUtil;
import com.landleaf.ibsaas.common.utils.encode.EncryptMD5Util;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SMSsantiUtil {
    private static Logger LOGGER = LoggerFactory.getLogger(SMSsantiUtil.class);

    /**
     * 普通短信发送
     *
     * @param appId  必填参数。应用ID号
     * @param appKey 平台获取
     * @param modeId 必填参数。模板ID，需后台审核通过，发送内容加企业签名和变量内容必须小于670个字符。
     *               格式：“验证码：{$var1},信息{$var2}分钟有效!”
     *               UTF-8编码
     * @param phones 必填参数。11位标准手机号码。若为多个号码用英文逗号分隔，最多支持500个手机号。
     * @param vars   选填参数。若modeId有变量内容则必填。变量内容：“123|十”英文竖线分割，内容必须小于670个字符。
     *               UTF-8编码，若vars包含超链接或者特殊字符，则需要对vars做urlencode编码
     * @return
     */
    public static int send(String appId, String appKey, String url, String modeId, String phones, String vars) {
        return send(appId, appKey, url, modeId, phones, vars, null, null);
    }

    /**
     * 普通短信发送
     *
     * @param appId     必填参数。应用ID号
     * @param appKey    平台获取
     * @param url       请求地址
     * @param modeId    必填参数。模板ID，需后台审核通过，发送内容加企业签名和变量内容必须小于670个字符。
     *                  格式：“验证码：{$var1},信息{$var2}分钟有效!”
     *                  UTF-8编码
     * @param phones    必填参数。11位标准手机号码。若为多个号码用英文逗号分隔，最多支持500个手机号。
     * @param vars      选填参数。若modeId有变量内容则必填。变量内容：“123|十”英文竖线分割，内容必须小于670个字符。
     *                  UTF-8编码，若vars包含超链接或者特殊字符，则需要对vars做urlencode编码
     * @param sendTime  选填参数。发送时间。若不传，视为立即发送；若传值，则会在sendTime表示的时间点开始发送。格式为：yyyyMMddHHmmss如：20161123143022
     * @param notifyUrl 选填参数。状态通知地址。若传值，则按照传入notifyUrl做状态通知。需作Urlencode后传入
     * @param sign      必填参数。用户签名。(appKey+appId+ mobile)生成MD5转小写字母，appKey在平台获取。
     * @return
     */
    public static int send(String appId, String appKey, String url, String modeId, String phones, String vars, String sendTime, String notifyUrl) {
        String type = MediaType.APPLICATION_JSON_UTF8_VALUE;
        String body = null;
        try {
            Map<String, String> requestParm = Maps.newHashMap();
            /****************************************必填参数**********************************************************/
            requestParm.put("appId", appId);
            requestParm.put("modeId", modeId);
            requestParm.put("mobile", phones);
            requestParm.put("sign", EncryptMD5Util.encryptMD5(appKey + appId + phones).toLowerCase());
            /***************************************选填参数**********************************************************/
            if (StringUtils.isNotBlank(vars)) {
                requestParm.put("vars", URLEncoder.encode(vars, "UTF-8"));
            }
            if (StringUtils.isNotBlank(sendTime)) {
                requestParm.put("sendTime", sendTime);
            }
            if (StringUtils.isNotBlank(notifyUrl)) {
                requestParm.put("notifyUrl", notifyUrl);
            }
            /****************************************请求参数********************************************************/
            body = getUrlParam(requestParm);

            LOGGER.info(String.format("三体短信发送-----》请求地址：%s;请求参数:[%s],请求时间:%s", url, body, DateUtil.format(new Date())));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String result = doGet(url + "?" + body, type);
        LOGGER.info(String.format("三体短信发送返回结果-----》%s", result));
        if (StringUtils.isEmpty(result)) {
            return -2;
        } else {
            JSONObject obj = JSONObject.parseObject(result);
            String code = obj.getString("code");
            if (StringUtils.equals("0", code)) {
                return 0;
            } else {
                return -2;
            }
        }
    }


    /**
     * 组织参数
     *
     * @param map
     * @return
     */
    public static String getUrlParam(Map<String, String> map) {
        if (MapUtils.isEmpty(map)) {
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        Set<String> keySet = map.keySet();
        Iterator<String> iter = keySet.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            buffer.append(key).append("=").append(map.get(key));
            if (iter.hasNext()) {
                buffer.append("&");
            }
        }
        return buffer.toString();
    }

    /**
     * post请求
     *
     * @param url
     * @param body
     * @param type
     * @return
     */
    public static String doPost(String url, String body, String type) {
        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        postMethod.setRequestBody(body);
        postMethod.setRequestHeader("Content-type", type);
        postMethod.getParams().setContentCharset("UTF-8");
        String response = "";
        try {
            client.executeMethod(postMethod);
            if (postMethod.getStatusCode() == HttpStatus.SC_OK) {
                response = postMethod.getResponseBodyAsString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            postMethod.releaseConnection();
        }
        return response;
    }

    /**
     * httpclient发送get请求
     *
     * @param url
     * @return
     */
    public static String doGet(String url, String type) {
        HttpClient client = new HttpClient();
        GetMethod getMethod = new GetMethod(url);
        getMethod.getParams().setContentCharset("utf-8");
        getMethod.setRequestHeader("Content-type", type);
        String response = null;
        try {
            client.executeMethod(getMethod);
            if (getMethod.getStatusCode() == HttpStatus.SC_OK) {
                response = getMethod.getResponseBodyAsString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            getMethod.releaseConnection();
        }
        return response;
    }

    public static void main(String[] args) {
//		String sign = EncryptMD5Util.encryptMD5(appKey + appId + "19945657236").toLowerCase();
//		System.out.println(sign);
//        int send1 = sendNormalSms("10448", "a2d4fd0b5c", "http://sms.smspaas.com/mt.php", "209395", "19945657236", "中文验证码123456");
        int send2 = send("10448", "a2d4fd0b5c", "http://sms.smspaas.com/mt.php", "209395", "13816337094,19945657236", String.format("多个手机号提交时间%s", DateUtil.format(new Date())));
//        int send3 = sendNormalSms("10448", "a2d4fd0b5c", "http://sms.smspaas.com/mt.php", "209395", "13816337094", String.format("提交时间%s",DateUtil.format(new Date())));
    }
}
