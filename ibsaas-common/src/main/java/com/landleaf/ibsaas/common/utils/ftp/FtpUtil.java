package com.landleaf.ibsaas.common.utils.ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * Ftp操作的工具类
 * @author jin
 * @date 2016-10-21
 */
public class FtpUtil {	
	
	private static Logger LOG = LoggerFactory.getLogger(FtpUtil.class);
	
	public static void main(String[] args){
	}
	
	/** 
	* 查询ftp指定文件内容
	* @param ftpClient ftp对象
	* @param filename 文件名称
	* @return List<String> 文件内容，按行读取
	*/
	public static List<String> getContentList(FTPClient ftpClient,String filename){
		List<String> list = new ArrayList<>();
		InputStream in = null;
		BufferedReader br = null;
		try {
			in = ftpClient.retrieveFileStream(filename);
			if (in != null) {
				br = new BufferedReader(new InputStreamReader(in, "utf-8"));
				String line = "";
				while ((line = br.readLine()) != null) {
					list.add(line);
				}
			}
		} catch (Exception e) {
			LOG.error("ftp解析文件异常:" + e.getMessage(), e);
		} finally {
			//关闭流
			try {
				if (in != null) {
					in.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (Exception e) {
				LOG.error("ftp文件流关闭异常:" + e.getMessage(), e);
			}
		}
		return list;
	}

	/** 
	* 关闭ftp连接
	* @param ftpClient ftp对象
	* @return boolean 是否关闭成功
	*/
	public static boolean disconnect(FTPClient ftpClient){
		boolean result = false;
		try {
			if (ftpClient != null) {
				ftpClient.disconnect();
				result = true;
			}
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
	/** 
	 * 连接到服务器并返回ftp对象，使用后记得调用disconnect方法关闭ftp连接！
	 * @param ip ftp服务器的ip地址
	 * @param port 服务器端口
	 * @param user 用户名
	 * @param password 密码
	 * @return FTPClient ftp操作对象
	 */
	public static FTPClient connectServer(String ip,String port,String user,String password) {
		FTPClient ftpClient = null;
		ftpClient = new FTPClient();
		ftpClient.setControlEncoding("utf-8");
		ftpClient.configure(getFtpConfig());
		try {
			ftpClient.connect(ip, Integer.parseInt(port));
			ftpClient.login(user, password);
			int reply = ftpClient.getReplyCode();
			int timeout = 120000;
			ftpClient.setDataTimeout(timeout);//超时时间，需要根据数据量大小调整
			ftpClient.setConnectTimeout(timeout);
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				LOG.error("FTP server refused connection.");
			}
		} catch (NumberFormatException e) {
			LOG.error("FTP error:"+e.getMessage());
			ftpClient = null;
		} catch (SocketException e) {
			LOG.error("FTP error:"+e.getMessage());
			ftpClient = null;
		} catch (IOException e) {
			LOG.error("FTP error:"+e.getMessage());
			ftpClient = null;
		}
		return ftpClient;
	}
	private static FTPClientConfig getFtpConfig() {
		FTPClientConfig ftpConfig = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
		ftpConfig.setServerLanguageCode(FTP.DEFAULT_CONTROL_ENCODING);
		return ftpConfig;
	}
	/** 
	* 进入到服务器的某个目录下 
	* @param ftpClient ftp对象
	* @param directory 目录路径
	*/
	public static void changeWorkingDirectory(FTPClient ftpClient, String directory) throws Exception {
		ftpClient.changeWorkingDirectory(directory);
	}
	
	/** 
	* 删除文件
	* @param ftpClient ftp对象
	* @param filename 文件名
	* @return boolean 是否删除成功
	*/
	public static boolean deleteFile(FTPClient ftpClient, String filename) {
		boolean flag = true;
		try {
			flag = ftpClient.deleteFile(filename);
		} catch (IOException e) {
			flag = false;
		}
		if (flag) {
			LOG.info("删除文件成功！"+filename);
		} else {
			LOG.info("删除文件失败！"+filename);
		}
		return flag;
	}
}
