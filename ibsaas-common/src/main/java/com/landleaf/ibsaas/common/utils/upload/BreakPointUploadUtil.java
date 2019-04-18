package com.landleaf.ibsaas.common.utils.upload;


import com.landleaf.ibsaas.common.utils.json.JsonFormatUtil;
import com.landleaf.ibsaas.common.utils.number.NumberUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;


/**
 * 断点上传工具类
 * @author jin
 * @date 2016-9-7
 */
public class BreakPointUploadUtil {
	/** 分片大小 **/
    public static long lPiece = 1024 * 1024 * 10;

	/** 类型常量 **/
    public static String CODE = "code";
    public static String FOLDER = "reportfile";
	/** 服务端错误类型 **/
    public static String STATUS_SUCCESS="0";
    public static String STATUS_NETWORK_ERROR="1";
    public static String STATUS_FETCHDATA_ERROR="2";
    public static String STATUS_FILEISEXISTS="3";

    private static final Logger LOGGER = LoggerFactory.getLogger(BreakPointUploadUtil.class);
    

	/**
	 * 根据文件往静态资源服务器断点上传文件
	 * @author Jin
	 * @date 2016-9-6
	 */
    public static String upload(File file,String serviceUrl) throws Exception {
    	String path = "";
    	if(!file.exists()){
    		return "";
    	}
    	//查询起始位置
        Map startMap = getStartPos(file, FOLDER, serviceUrl);
        String strCodetemp = startMap.get(CODE).toString();
        if(!STATUS_SUCCESS.equals(strCodetemp)){
            return "";
        }
        String strStart = startMap.get("startsize").toString();
        String strTot = startMap.get("totsize").toString();
        boolean b = true;
        //循环断点续传文件
        while (b) {
        	Map map = breakPointUpload(file, FOLDER, startMap.get("savename").toString(), strStart, strTot,serviceUrl);
            strStart = map.get("start").toString();
            String strCode = map.get("code").toString();
            if (NumberUtils.double2long(strCode) == 0) {
                if (NumberUtils.double2long(strStart) == -1) {
                    b = false;
                    path = map.get("path").toString();
                }else{
                }
            } else {
                b = false;
            }
        }
    	return path;
    }

	/**
	 * 根据文件路径往静态资源服务器断点上传文件
	 * @author Jin
	 * @date 2016-9-6
	 */
    public static String upload(String path,String serviceUrl) throws Exception {
    	File file = new File(path);
    	return upload(file,serviceUrl);
    }

    /**
     * 获取上传起始点 0 成功！ 1 网球请求异常，请重试！ 2 返回数据失败，请重试！
     * 
     * @author meff
     * @param strLocalFileName
     *            本地文件名 "D:\\cat.jpg"
     * @param strModuleType
     *            模块名 id,sign,image,jzimage,pickup
     * @param strServerURL
     *            请求服务器URL "http://localhost:8080/ksudi-upload/"
     * @return json
     * @throws IOException
     */
    public static Map getStartPos(File file, String strModuleType, String strServerURL)
            throws IOException {
        // 定义全局变量
        CloseableHttpResponse response = null;
        HttpEntity httpEntity = null;
        String strResponseContent = null;
        Map<String, String> retMap = new HashMap<String, String>();

        try {

            // 初始化 File
            String strOldFileName = file.getName();
            CloseableHttpClient httpclient = HttpClients.createDefault();

            HttpGet httpGet = new HttpGet(strServerURL + "?name=" + strOldFileName + "&dirtype=" + strModuleType
                    + "&type=" + strOldFileName.substring(strOldFileName.lastIndexOf('.') + 1) + "&size="
                    + file.length() + "&modified=" + file.lastModified());
            response = httpclient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                httpEntity = response.getEntity();
                if (httpEntity != null) {
                    strResponseContent = EntityUtils.toString(httpEntity, "utf8");
                    Map<?, ?> map = JsonFormatUtil.getMapFromJsonStr(strResponseContent);
                    // 服务器端处理成功
                    if (NumberUtils.double2long(map.get("code")) == 1) {
                        retMap.put("code", "0");
                        retMap.put("savename", map.get("fileName").toString());
                        retMap.put("startsize", String.valueOf(NumberUtils.double2long(map.get("start").toString())));
                        retMap.put("totsize", String.valueOf(file.length()));
                    } else {
                        retMap.put("code", "2");
                        retMap.put("msg", map.get("msg").toString());
                    }
                } else {
                    retMap.put("code", "2");
                }
            } else {
                retMap.put("code", "1");
            }

            file = null;
            response.close();
            httpclient.close();
        } catch (Exception e) {
            retMap.put("code", "1");
            LOGGER.error(e.getMessage(), e);
        }

        return retMap;
    }

    /**
     * 断点续传 0 成功！ 1 网球请求异常，请重试！ 2 返回数据失败，请重试！
     * 
     * @author meff
     * @param strLocalFileName
     *            本地文件名 "D:\\cat.jpg"
     * @param strModuleType
     *            模块名 id,sign,image,jzimage,pickup
     * @param strReturnFileName
     *            远程文件名从getStartPos返回值中得到
     * @param strStartSize
     *            起始字节数 1234(字节)
     * @param strTotSize
     *            文件总大小 234433(字节)
     * @param strServerURL
     *            请求服务器URL "http://localhost:8080/ksudi-upload/"
     * @return json(start=-1 表示上传完成)
     * @throws IOException
     */
    public static Map<String, Object> breakPointUpload(File file, String strModuleType, String strReturnFileName,
            String strStartSize, String strTotSize, String strServerURL) throws IOException {
        // 定义全局变量
        HttpPost httppost = null;
        CloseableHttpResponse response = null;
        HttpEntity httpEntity = null;
        String strResponseContent = null;
        Map<?, ?> map = null;
        Map<String, Object> retMap = new HashMap<String, Object>();

        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            httppost = new HttpPost(strServerURL + "?saveName=" + strReturnFileName + "&dirtype=" + strModuleType);

            // 计算本次上传的范围
            long lStart = Long.parseLong(strStartSize);
            long lEnd = lStart + lPiece;
            long lTotSize = Long.parseLong(strTotSize);

            if (lEnd >= lTotSize) {
                lEnd = lTotSize;
            }

            if (lStart == lTotSize) {
                retMap.put("start", -1);
                retMap.put("code",0);
                return retMap;
            }

            String strRange = strStartSize + "-" + String.valueOf(lEnd) + "/" + strTotSize;
            strRange = "bytes " + strRange;
            httppost.addHeader("Content-Range", strRange);

            // 把文件一定范围内的字节数据放到字节数组中
            int iLenght = (int) (lEnd - lStart);
            byte[] bytes = new byte[iLenght];
            RandomAccessFile raf = new RandomAccessFile(file.getPath(), "r");// 负责读取数据
            raf.seek(lStart);
            raf.read(bytes, 0, (int) iLenght);
            raf.close();

            ByteArrayEntity bEntity = new ByteArrayEntity(bytes);
            httppost.setEntity(bEntity);

            response = httpclient.execute(httppost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                httpEntity = response.getEntity();
                if (httpEntity != null) {
                    strResponseContent = EntityUtils.toString(httpEntity, "utf8");
                    map = JsonFormatUtil.getMapFromJsonStr(strResponseContent);

                    // 服务器端处理成功
                    if (NumberUtils.double2long(map.get("code")) == 1) {
                        retMap.put("path", map.get("path").toString());
                        retMap.put("start", NumberUtils.double2long(map.get("start")));
                        retMap.put("code", "0");
                    } else {
                        retMap.put("code", "2");
                        retMap.put("msg", map.get("msg").toString());
                    }

                } else {
                    retMap.put("code", "2");
                }
            } else {
                retMap.put("code", "1");
            }

            response.close();
            httpclient.close();
        } catch (Exception e) {
            retMap.put("code", "1");
            e.printStackTrace();
        }

        return retMap;
    }
}
