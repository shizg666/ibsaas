package com.landleaf.ibsaas.common.utils.help;

public class ConstUtil {

	//开发中用常量
	public static String portPath;
	public static int iPageSize;
	public static int iPageNumber;
	
	//测试环境ftp参数
	public static String imgPath;
	public static String ftpIp;
	public static int ftpPort;
	public static String ftpUserName;
	public static String ftpPwd;
	
	//微信用参数
	//第三方用户唯一凭证
	public static String APPID;
	//第三方用户唯一凭证密钥
	public static String APPSECRET;
	//与接口配置信息中的Token要一致
	public static String TOKEN;
	
	//测试专职环境消息推送参数
	public static String appKey;
    public static String masterSecret;
    
    //测试兼职app消息推送参数
  	public static String jzAppKey;
    public static String jzMasterSecret;
    
    //登录是否启用验证码 -1 false ,0 true
    public static int validcode;
    
    //定时消息放到redis的key
    public static String DSMSGKEY="dsmsgkey";
    
    //专职信鸽推送安卓参数
    public static long xgAndAccessId;
    public static String xgAndSecretKey;
    
    //兼职信鸽推送安卓参数
    public static long jzxgAndAccessId;
    public static String jzxgAndSecretKey;
    
    //专职信鸽推送苹果参数
    public static long xgIOSAccessId;
    public static String xgIOSSecretKey;
    
    //兼职信鸽推送苹果参数
    public static long jzxgIOSAccessId;
    public static String jzxgIOSSecretKey;
    
    //企业专职苹果推送极光参数
    public static String comAppKey;
    public static String comMasterSecret;
    
    //企业专职苹果推送信鸽参数
    public static long comxgIOSAccessId;
    public static String comxgIOSSecretKey;
    
    //生成id用常量
    public static int CUTID;
    public static int CUTID_MAX=9999;
    
    //企业兼职苹果推送极光参数
    public static String jzcomAppKey;
    public static String jzcomMasterSecret;
    
    //企业兼职苹果推送信鸽参数
    public static long jzcomxgIOSAccessId;
    public static String jzcomxgIOSSecretKey;
    //信鸽推送ios环境  1生产环境  2开发环境
    public static int iosenv;
    
    //支付url
    public static String paymentUrl;
    
    public static String prefix = "USR";
    public static String splitchar = "#@#";
    
    //楼下100配置
    public static String louxiaappid;
    public static String louxiaappkey;
    public static String louxiaorderlisturl;
    public static String louxiaorderstateurl;
    public static Long louxia;
    
    //mongodb 日志表名
    public static final String LOG_TABLE_NAME="log";
    public static final String OPTION_TABLE_NAME="option";
}
