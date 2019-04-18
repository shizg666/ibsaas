package com.landleaf.ibsaas.common.utils.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 此类相当于登录校验，确保该邮箱有发送邮件的权利
 * @author Administrator
 */
public class MyAuthenticator extends Authenticator{
	 String userName = null;
	 String password = null;
	 public MyAuthenticator() {
	 }

	 public MyAuthenticator(String username, String password) {
		 this.userName = username;
		 this.password = password;
	 }

	 protected PasswordAuthentication getPasswordAuthentication() {
		 return new PasswordAuthentication(userName, password);
	 }

}
