package com.landleaf.ibsaas.common.utils.check;



import com.landleaf.ibsaas.common.utils.string.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtil {
	/**
	 * 验证手机号格式
	 * @param cellphone 手机号
	 * @return 是否通过验证
	 * @author jason.li
	 * @date 2015-10-27
	 */
	public static Boolean checkCellphone(String cellphone) {
		if (StringUtil.isEmpty(cellphone)) {
			return false;
		}
		if (cellphone.length() != 11){
			return false;
		}
		Pattern p = Pattern.compile("1[0-9]{10}");
		Matcher matcher = p.matcher(cellphone);
		if (!matcher.matches()) {
			return false;
		}
		return true;
	}
	/**
	*  验证手机号和座机号
	 * @param cellphone  手机号
	 * @return
	 * @author Hubing
	 * @date  2016年6月6日 下午5:50:08
	 */
	public static Boolean checkPhone(String cellphone) {
		if (StringUtil.isEmpty(cellphone)) {
			return false;
		}
		Pattern p = Pattern.compile("1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}");
		Matcher matcher = p.matcher(cellphone);
		if (!matcher.matches()) {
			return false;
		}
		return true;
	}
    /**
     * 邮箱验证
     * @param email  邮箱
     * @return
     * @author jason.li
     * @date 2015年10月27日 上午9:55:40
     */
	public static Boolean checkEmail(String email){
		if (StringUtil.isEmpty(email)) {
			return false;
		}
		if (email.indexOf("@") < 0 || email.indexOf(".") < 0) {
			return false;
		}
		return true;
	}
}
