package com.landleaf.ibsaas.common.tcp.code;


public class ModbusServerException extends Exception
{
    private static final long serialVersionUID = 1L;
    private String errorMessage;
   
	public ModbusServerException(String msg)
	 {
		super(msg);
		this.errorMessage = msg;
	 }
	
	public ModbusServerException(String moduleName, String msg)
	 {
		super(msg);
		this.errorMessage = msg;				
	 }
	
	
	public ModbusServerException(StringBuffer moduleName, StringBuffer msg)
	 {		
		super(msg.toString());
		this.errorMessage = msg.toString();		
	 }
	
	public String getErrorMessage()
	{
		return this.errorMessage;
	}
}