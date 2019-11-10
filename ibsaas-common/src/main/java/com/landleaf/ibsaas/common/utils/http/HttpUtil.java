package com.landleaf.ibsaas.common.utils.http;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * http 的  get 和 post 请求
 * @author Tony
 * @date 2015年6月4日
 */
public class HttpUtil {	
	private static Logger log = LoggerFactory.getLogger(HttpUtil.class);
	
	//通用发送post请求
	public static String doPost(String url,NameValuePair[] body,String type){
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestBody(body);
		String contentType="text/"+type+"; charset=UTF-8";
		postMethod.setRequestHeader("Content-type",contentType);
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
    //通用发送get请求
	public static String doGet(String url){
		HttpClient client = new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		getMethod.getParams().setContentCharset("UTF-8");
		String response="";
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
	//通用发送post请求
	public static String doPost(String url,String body,String type){
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestBody(body);
		//String contentType="text/"+type+"; charset=UTF-8";
		postMethod.setRequestHeader("Content-type",type);
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
     * 向指定 URL 发送POST方法的请求
     * @param url 发送请求的 URL    
     * @param params 请求的参数集合     
     * @return 远程资源的响应结果
	 * @author jin
	 * @date 2016年8月3日
     */
	public static String doPost(String url, Map<String, String> params) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();; 
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn =(HttpURLConnection) realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // POST方法
            conn.setRequestMethod("POST");
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setConnectTimeout(1000 * 30);//设置建立连接的超时时间
            conn.setReadTimeout(1000 * 30);//设置传递数据的超时时间
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数            
            if (params != null) {
		          StringBuilder param = new StringBuilder(); 
		          for (Map.Entry<String, String> entry : params.entrySet()) {
		        	  if(param.length()>0){
		        		  param.append("&");
		        	  }	        	  
		        	  param.append(entry.getKey());
		        	  param.append("=");
		        	  param.append(entry.getValue());		        	  
		          }
		          out.write(param.toString());
		          log.info("posturl="+url+" param="+param.toString());
            }
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        //如果post请求失败，则此处会报错让外层拦截错误做业务处理
        return result.toString();
    }
	 /**
     * 向指定 URL 发送POST方法的请求
     * @param url 发送请求的 URL
     * @param params 请求的参数集合
     * @return 远程资源的响应结果
	 * @author Jason.Li
	 * @date 2017年5月3日
     */
	public static String doPost(String url, TreeMap<String,Object> params) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();;
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn =(HttpURLConnection) realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // POST方法
            conn.setRequestMethod("POST");
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setConnectTimeout(1000 * 30);//设置建立连接的超时时间
            conn.setReadTimeout(1000 * 30);//设置传递数据的超时时间
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数
            if (params != null) {
		          StringBuilder param = new StringBuilder();
		          for (Map.Entry<String, Object> entry : params.entrySet()) {
		        	  Object value=entry.getValue();
		        	  if(value instanceof List){
		        		  List<Map<String,Object>> valueList=(List<Map<String,Object>>)value;
		        		  for(int i=0;i<valueList.size();i++){
		        			  Map<String,Object> valueMap=valueList.get(i);
		        			  for (Map.Entry<String, Object> valueEntry : valueMap.entrySet()) {
		        				  if(param.length()>0){
					        		  param.append("&");
					        	  }
		        				  param.append(entry.getKey()).append("[")
		        				  .append(i).append("]").append("[")
		        				  .append(valueEntry.getKey()).append("]")
		        				  .append("=").append(valueEntry.getValue());
		        			  }
		        		  }
		        	  }else{
		        		  if(param.length()>0){
			        		  param.append("&");
			        	  }
			        	  param.append(entry.getKey())
			        	  .append("=")
			        	  .append(value);
		        	  }
		          }
		          out.write(param.toString());
		          log.info("posturl="+url+" param="+param.toString());
            }
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {            
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        //如果post请求失败，则此处会报错让外层拦截错误做业务处理
        return result.toString();
    }
	 /**
     * 向指定 URL 发送POST方法的请求(不推荐使用，遇见特殊&字符会出现问题)
     * @param url 发送请求的 URL
     * @param params 请求的参数集合
     * @return 远程资源的响应结果
	 * @author jin
	 * @throws Exception
	 * @date 2016年8月3日
     */
	public static String doPostForThrow(String url, Map<String, String> params,int times){
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();;
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn =(HttpURLConnection) realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // POST方法
            conn.setRequestMethod("POST");
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setConnectTimeout(1000 * 30);//设置建立连接的超时时间
            conn.setReadTimeout(1000 * 30);//设置传递数据的超时时间
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数
            if (params != null) {
		          StringBuilder param = new StringBuilder();
		          for (Map.Entry<String, String> entry : params.entrySet()) {
		        	  if(param.length()>0){
		        		  param.append("&");
		        	  }
		        	  param.append(entry.getKey());
		        	  param.append("=");
		        	  param.append(entry.getValue());
		          }
		          log.info("posturl="+url+" param="+param.toString());
		          out.write(param.toString());
            }
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        }catch(ConnectTimeoutException e){
			if(times>1){
				times--;
				return doPostForThrow(url,params, times);
			}
		   log.error(e.getMessage(), e);
		   throw new RuntimeException(e.getMessage());
		} catch (Exception e) {
           throw new RuntimeException(e.getMessage());
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        //如果post请求失败，则此处会报错让外层拦截错误做业务处理
        return result.toString();
    }
	
	/**
	 * 获取https的client
	 * @return
	 * @author justin.li
	 * @date 2017年5月15日
	 */
	public static CloseableHttpClient createSSLClientDefault(){
		try {
             SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                 //信任所有
                 public boolean isTrusted(X509Certificate[] chain,
                                 String authType) throws CertificateException {
                     return true;
                 }
             }).build();
             SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
             return HttpClients.custom().setSSLSocketFactory(sslsf).build();
         } catch (KeyManagementException e) {
	          log.info(e.getMessage(), e);
         } catch (NoSuchAlgorithmException e) {
	          log.info(e.getMessage(), e);
         } catch (KeyStoreException e) {
	          log.info(e.getMessage(), e);
         }
         return  HttpClients.createDefault();
	}
	
	/**
	 * https的post请求
	 * @param uri
	 * @param jsonStr
	 * @param httpclient
	 * @return
	 * @author justin.li
	 * @date 2017年5月15日
	 */
	public static String hspost(String uri, String jsonStr, CloseableHttpClient httpclient, int times){
		try {
			HttpPost httpPost = new HttpPost(uri);
			StringEntity entity = new StringEntity(jsonStr, "UTF-8");
			entity.setContentType("application/x-www-form-urlencoded;charset=utf-8");
			
			httpPost.setEntity(entity);
			RequestConfig config= RequestConfig.custom().setConnectTimeout(20000).setSocketTimeout(20000).build();
			httpPost.setConfig(config);
			CloseableHttpResponse response1 = httpclient.execute(httpPost);
			// Comn.pl(response1.getStatusLine());
			HttpEntity entity1 = response1.getEntity();
			BufferedReader br = new BufferedReader(new InputStreamReader(entity1.getContent(),"utf-8"));
			String line = null;
			StringBuilder lineSb = new StringBuilder();
			while((line=br.readLine())!=null){
				lineSb.append(line);
			}
			EntityUtils.consume(entity1);
			return lineSb.toString();
		}catch(ConnectTimeoutException e){
			if(times>1){
				times--;
				return hspost(uri, jsonStr, httpclient, times);
			}
			  log.error(e.getMessage(), e);
		} catch(Exception e) {
	          log.error(e.getMessage(), e);
		} finally {
			try{
			httpclient.close();
			} catch(Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		return null;
	}
	
	
	/**
	 * https的post请求
	 * @param uri
	 * @param jsonStr
	 * @param httpclient
	 * @return
	 * @author justin.li
	 * @date 2017年5月15日
	 */
	public static String hspostParam(String uri, Map<String, String> paramMap, CloseableHttpClient httpclient){
		
		try {
			List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
			if(MapUtils.isNotEmpty(paramMap)){
				for(Map.Entry<String, String> enty : paramMap.entrySet()){
					formparams.add(new BasicNameValuePair(enty.getKey(), enty.getValue()));
				}
			}
			
			
			HttpPost httpPost = new HttpPost(uri);
			UrlEncodedFormEntity entity=new UrlEncodedFormEntity(formparams, "UTF-8");
			entity.setContentType("application/x-www-form-urlencoded;charset=utf-8");
			
			httpPost.setEntity(entity);
			CloseableHttpResponse response1 = httpclient.execute(httpPost);
			// Comn.pl(response1.getStatusLine());
			HttpEntity entity1 = response1.getEntity();
			BufferedReader br = new BufferedReader(new InputStreamReader(entity1.getContent(),"utf-8"));
			String line = null;
			StringBuilder lineSb = new StringBuilder();
			while((line=br.readLine())!=null){
				lineSb.append(line);
			}
			EntityUtils.consume(entity1);
			log.info("hspostParam retn="+lineSb);
			return lineSb.toString();
		} catch(Exception e) {
	          log.info(e.getMessage(), e);
		} finally {
			try{
			httpclient.close();
			} catch(Exception e) {
				log.info(e.getMessage(), e);
			}
		}
		return null;
	}
	public static String hspostParamForThrow(String uri, Map<String, String> paramMap, CloseableHttpClient httpclient, int times){
		
		try {
			List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
			if(MapUtils.isNotEmpty(paramMap)){
				for(Map.Entry<String, String> enty : paramMap.entrySet()){
					formparams.add(new BasicNameValuePair(enty.getKey(), enty.getValue()));
				}
			}
			log.info("hspostParamForThrow uri:"+uri+";param:"+formparams);
			
			HttpPost httpPost = new HttpPost(uri);
			UrlEncodedFormEntity entity=new UrlEncodedFormEntity(formparams, "UTF-8");
			entity.setContentType("application/x-www-form-urlencoded;charset=utf-8");
			
			httpPost.setEntity(entity);
			RequestConfig config= RequestConfig.custom().setConnectTimeout(30000).setSocketTimeout(30000).build();
			httpPost.setConfig(config);
			CloseableHttpResponse response1 = httpclient.execute(httpPost);
			// Comn.pl(response1.getStatusLine());
			HttpEntity entity1 = response1.getEntity();
			BufferedReader br = new BufferedReader(new InputStreamReader(entity1.getContent(),"utf-8"));
			String line = null;
			StringBuilder lineSb = new StringBuilder();
			while((line=br.readLine())!=null){
				lineSb.append(line);
			}
			EntityUtils.consume(entity1);
			log.info("hspostParam retn="+lineSb);
			return lineSb.toString();
		}catch(ConnectTimeoutException e){
			if(times>1){
				times--;
				return hspostParamForThrow(uri, paramMap, httpclient, times);
			}
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage());
		} catch(Exception e) {
	          log.error(e.getMessage(), e);
	          throw new RuntimeException(e.getMessage());
		} finally {
			try{
			httpclient.close();
			} catch(Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}
	public static String hspostJsonParamForThrow(String uri, String jsonParam, CloseableHttpClient httpclient, int times){
		
		try {
			
			log.info("hspostJsonParamForThrow uri:"+uri+";param:"+jsonParam);
			
			HttpPost httpPost = new HttpPost(uri);
			httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8");
			StringEntity se = new StringEntity(jsonParam, ContentType.APPLICATION_JSON);
			httpPost.setEntity(se);
			RequestConfig config= RequestConfig.custom().setConnectTimeout(30000).setSocketTimeout(30000).build();
			httpPost.setConfig(config);
			CloseableHttpResponse response1 = httpclient.execute(httpPost);
			HttpEntity entity1 = response1.getEntity();
			BufferedReader br = new BufferedReader(new InputStreamReader(entity1.getContent(),"utf-8"));
			String line = null;
			StringBuilder lineSb = new StringBuilder();
			while((line=br.readLine())!=null){
				lineSb.append(line);
			}
			EntityUtils.consume(entity1);
			log.info("hspostJsonParamForThrow retn="+lineSb);
			return lineSb.toString();
		}catch(ConnectTimeoutException e){
			if(times>1){
				times--;
				return hspostJsonParamForThrow(uri, jsonParam, httpclient, times);
			}
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage());
		}catch(Exception e) {
	        log.error(e.getMessage(), e);
	        throw new RuntimeException(e.getMessage());
		} finally {
			try{
			httpclient.close();
			} catch(Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}
	public static String doPost(String url,String jsonParam,int times){
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();;
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn =(HttpURLConnection) realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // POST方法
            conn.setRequestMethod("POST");
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setConnectTimeout(1000 * 30);//设置建立连接的超时时间
            conn.setReadTimeout(1000 * 30);//设置传递数据的超时时间
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数
            out.write(jsonParam);
            log.info("HttpUtil_doPost_request_posturl="+url+" param="+jsonParam);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        }catch(ConnectTimeoutException e){
			if(times>1){
				times--;
				return doPost(url, jsonParam, times);
			}
		   log.error(e.getMessage(), e);
		   throw new RuntimeException(e.getMessage());
		} catch (Exception e) {
           throw new RuntimeException(e.getMessage());
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        //如果post请求失败，则此处会报错让外层拦截错误做业务处理
        log.info("HttpUtil_doPost_response="+result.toString());
        return result.toString();
    }
}
