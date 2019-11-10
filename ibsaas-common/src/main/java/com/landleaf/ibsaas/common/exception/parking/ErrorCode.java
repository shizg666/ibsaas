package com.landleaf.ibsaas.common.exception.parking;

import java.io.Serializable;

/**
 * 错误编码公共接口
 */
public interface ErrorCode extends Serializable{
	/**
	 * 错误码
	 * @return
	 */
	String getCode();
	/**
	 * 错误信息
	 * @return
	 */
	String getMsg();
}
