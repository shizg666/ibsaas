package com.landleaf.ibsaas.common.exception.parking;


import com.landleaf.ibsaas.common.exception.BaseException;

/**
 * 消息异常
 * .<br/>
 */
public class MsgProcessorException extends BaseException {

	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 无参构造函数
	 */
	public MsgProcessorException() {
		super();
	}
	public MsgProcessorException(Throwable e) {
		super(e);
	}
	public MsgProcessorException(ErrorCode errorType) {
		super(errorType);
	}
	
	public MsgProcessorException(ErrorCode errorCode, String ... errMsg) {
		super(errorCode, errMsg);
	}
	/**
	 * 封装异常
	 * @param errorCode
	 * @param errMsg
	 * @param isTransfer 是否转换异常信息，如果为false,则直接使用errMsg信息
	 */
	public MsgProcessorException(ErrorCode errorCode, String errMsg, Boolean isTransfer) {
		super(errorCode, errMsg,isTransfer);
	}
	
	public MsgProcessorException(ErrorCode errCode, Throwable cause, String ... errMsg) {
		super(errCode,cause, errMsg);
	}
}
