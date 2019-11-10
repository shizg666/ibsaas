package com.landleaf.ibsaas.web.web.constant;

/**
 * @Description: 系统级常量
 */
public class IbsaasWebConstants {
	/**
	 * 可用
	 */
	public static final Integer ACTIVE = 1;

	/**
	 * 不可用
	 */
	public static final Integer INACTIVE = 0;


	/**
	 * 是
	 */
	public static final Integer YES = 1;

	/**
	 * 否
	 */
	public static final Integer NO = 0;

	/**
	 * 本系统编号
	 */
	public static final String LEO_SYSTEM_CODE = "LEO";

	/**
	 * SessionContext中存放用户当前部门编码的key
	 */
	public static final String KEY_CURRENT_DEPART_CODE = "LEO_KEY_CURRENT_DEPART_CODE";

	/**
	 * SessionContext中存放用户当前部门名称的key
	 */
	public static final String KEY_CURRENT_DEPART_NAME = "LEO_KEY_CURRENT_DEPART_NAME";

	/**
	 * SessionContext中存放用户当前工号的key
	 */
	public static final String FRAMEWORK_KEY_EMPLOYEE_CODE = "FRAMEWORK_KEY_EMPLOYEE_CODE";
	
	/**
	 * 前台树根节点id
	 */
	public static final String TREE_ROOT_ID = "root";
	
	/**
	 * web权限资源树根节点code
	 */
	public static final String RESOURCE_TREE_WEB_ROOT_CODE = "1";

	/**
	 * 权限维护界面各个子系统判断是否加载根节点权限的标示
	 */
	public static final String RESOURCE_PAGE_ROOT_NODE = "RESOURCE_ROOT_NODE";
}
