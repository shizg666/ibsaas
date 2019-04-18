
package com.landleaf.ibsaas.common.utils.condition;


import org.springframework.util.CollectionUtils;

import java.util.*;

/** 条件查询对象
 * 用于封装查询条件
 * @author sourny.yang
 * @date 2016年1月15日
 */
public class ConditionUtil {
	public final static int MAX_QUERY_NUMBER = 10000;
	public static final int PAGE_SIZE = 15;
	public static final String RECORD_TOTAL = "recordTotal";
	public final static String RESULT = "list";
	public final static String PAGE = "page";
	private Map<String, List<?>> inMap;
	private Map<String, List<Object>> notInMap;
	private Map<String, Object> equalMap;
	private Map<String, Object> notEqualMap;
	private Map<String, Object> likeMap;
	private Map<String, Object> gtoeMap;
	private Map<String, Object> gtMap;
	private Map<String, Object> ltMap; 
	private Map<String, Object> ltoeMap;
	private Map<String, Object> notLikeMap;
	private Map<String, List<Object>> betweenMap;
	private Map<String, List<Object>> notBetweenMap;
	private List<String> appendSqlList;
	private List<String> andColumnSqlList;
	private List<String> appendMongoSqlList;
	private List<String> andColumnMongoSqlList;

	public static boolean isNull(Map<String, ?> map) {
		return (map == null);
	}

	public static boolean isNotNull(Map<String, ?> map) {
		return !isNull(map);
	}

	public static boolean isEmpty(Map<String, Object> map) {
		return (map == null || map.isEmpty());
	}

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		String content = "public void set%s(String key, Object value) {"
				+ "if(isEmpty(%s))" + "%s = new HashMap<String, Object>();"
				+ "this.%s.put(key, value);" + "}";
		List<String> asList = Arrays.asList(new String[] { "inMap", "notInMap",
				"equalMap", "notEqualMap", "likeMap", "gtoeMap", "gtMap",
				"ltMap", "ltoeMap", "notLikeMap", "notInMap", "betweenMap",
				"notBetweenMap", "sqlMap" });
		for (String string : asList) {
			String upperCaseColumn = string.substring(0, 1).toUpperCase()
					+ string.substring(1);
			System.out.println(String.format(content, new Object[] {
					upperCaseColumn, string, string, string }));
		}
		Date date = new Date();
		int date2 = date.getDate();
	}
	/** 新增AppendSql语句
	 * @author sourny.yang
	 * @date 2016年1月15日
	 */
	public void setAppendSql(String sql) {
		if (appendSqlList == null)
			appendSqlList = new ArrayList<String>();
		this.appendSqlList.add(sql);
	}
	/** 新增AndColumnSql语句
	 * @author sourny.yang
	 * @date 2016年1月15日
	 */
	public void setAndColumnMongoSqlList(String sql) {
		if (andColumnMongoSqlList == null)
			andColumnMongoSqlList = new ArrayList<String>();
		this.andColumnMongoSqlList.add(sql);
	}
	/** 新增AppendSql语句
	 * @author sourny.yang
	 * @date 2016年1月15日
	 */
	public void setAppendMongoSqlList(String sql) {
		if (appendMongoSqlList == null)
			appendMongoSqlList = new ArrayList<String>();
		this.appendMongoSqlList.add(sql);
	}
	/** 新增AndColumnSql语句
	 * @author sourny.yang
	 * @date 2016年1月15日
	 */
	public void setAndColumnSql(String sql) {
		if (andColumnSqlList == null)
			andColumnSqlList = new ArrayList<String>();
		this.andColumnSqlList.add(sql);
	}
	/** 设置包含条件
	 * @author sourny.yang
	 * @date 2016年1月15日
	 */
	public void setInMap(String key, List<?> value) {
		if (isNull(inMap))
			inMap = new HashMap<String, List<?>>();
		this.inMap.put(key, value);
	}
	/** 设置不包含条件
	 * @author sourny.yang
	 * @date 2016年1月15日
	 */
	public void setNotInMap(String key, List<Object> value) {
		if (isNull(notInMap))
			notInMap = new HashMap<String, List<Object>>();
		this.notInMap.put(key, value);
	}
	/** 设置等于条件
	 * @author sourny.yang
	 * @date 2016年1月15日
	 */
	public void setEqualMap(String key, Object value) {
		if (isNull(equalMap))
			equalMap = new HashMap<String, Object>();
		this.equalMap.put(key, value);
	}
	/** 设置不等于条件
	 * @author sourny.yang
	 * @date 2016年1月15日
	 */
	public void setNotEqualMap(String key, Object value) {
		if (isNull(notEqualMap))
			notEqualMap = new HashMap<String, Object>();
		this.notEqualMap.put(key, value);
	}
	/** 设置like条件
	 * @author sourny.yang
	 * @date 2016年1月15日
	 */
	public void setLikeMap(String key, Object value) {
		if (isNull(likeMap))
			likeMap = new HashMap<String, Object>();
		this.likeMap.put(key, "%"+ value + "%");
	}

	/**
	 * 设置MongoDB的like条件
	 * @param key
	 * @param value
     */
	public void setMongoLikeMap(String key, Object value) {
		if (isNull(likeMap))
			likeMap = new HashMap<String, Object>();
		this.likeMap.put(key, value);
	}
	/** 设置前置like条件
	 * @author sourny.yang
	 * @date 2016年1月15日
	 */
	public void setFrontLikeMap(String key, Object value) {
		if (isNull(likeMap))
			likeMap = new HashMap<String, Object>();
		this.likeMap.put(key, "%"+ value);
	}
	/** 设置后置like条件
	 * @author sourny.yang
	 * @date 2016年1月15日
	 */
	public void setRearLikeMap(String key, Object value) {
		if (isNull(likeMap))
			likeMap = new HashMap<String, Object>();
		this.likeMap.put(key, value +"%");
	}
	/** 设置不在小于条件
	 * @author sourny.yang
	 * @date 2016年1月15日
	 */
	public void setNotLikeMap(String key, Object value) {
		if (isNull(notLikeMap))
			notLikeMap = new HashMap<String, Object>();
		this.notLikeMap.put(key, value);
	}
	/** 设置大于条件
	 * @author sourny.yang
	 * @date 2016年1月15日
	 */
	public void setGtMap(String key, Object value) {
		if (isNull(gtMap))
			gtMap = new HashMap<String, Object>();
		this.gtMap.put(key, value);
	}
	/** 设置大于等于条件
	 * @author sourny.yang
	 * @date 2016年1月15日
	 */
	public void setGtoeMap(String key, Object value) {
		if (isNull(gtoeMap))
			gtoeMap = new HashMap<String, Object>();
		this.gtoeMap.put(key, value);
	}
	/** 设置小于条件
	 * @author sourny.yang
	 * @date 2016年1月15日
	 */
	public void setLtMap(String key, Object value) {
		if (isNull(ltMap))
			ltMap = new HashMap<String, Object>();
		this.ltMap.put(key, value);
	}
	/** 设置小于等于条件
	 * @author sourny.yang
	 * @date 2016年1月15日
	 */
	public void setLtoeMap(String key, Object value) {
		if (isNull(ltoeMap))
			ltoeMap = new HashMap<String, Object>();
		this.ltoeMap.put(key, value);
	}
	/** 设置两者之间条件
	 * @author sourny.yang
	 * @date 2016年1月15日
	 */
	public void setBetweenMap(String key, Object startValue,Object endValue) {
		if (isNull(betweenMap))
			betweenMap = new HashMap<String, List<Object>>();
		List<Object> l = new ArrayList<>();
		l.add(startValue);
		l.add(endValue);
		this.betweenMap.put(key, l);
	}
	/** 设置两者之间条件
	 * @author sourny.yang
	 * @date 2016年1月15日
	 */
	public void setBetweenMap(String key, List<?> values) {
		if(CollectionUtils.isEmpty(values))
			return;
		if (isNull(betweenMap))
			betweenMap = new HashMap<String, List<Object>>();
		List<Object> _valueList = new ArrayList<>();
		values.forEach((value)-> _valueList.add(value));
		this.betweenMap.put(key, _valueList);
	}

	/** 设置不在两者之间条件
	 * @author sourny.yang
	 * @date 2016年1月15日
	 */
	public void setNotBetweenMap(String key, Object startValue,Object endValue) {
		if (isNull(notBetweenMap))
			notBetweenMap = new HashMap<String, List<Object>>();
		List<Object> l = new ArrayList<>();
		l.add(startValue);
		l.add(endValue);
		this.notBetweenMap.put(key, l);
	}
	/** 设置不在两者之间条件
	 * @author sourny.yang
	 * @date 2016年1月15日
	 */
	public void setNotBetweenMap(String key, List<?> values) {
		if(CollectionUtils.isEmpty(values))
			return;
		if (isNull(notBetweenMap))
			notBetweenMap = new HashMap<String, List<Object>>();
		List<Object> _valueList = new ArrayList<>();
		values.forEach((value)-> _valueList.add(value));
		this.notBetweenMap.put(key, _valueList);
	}

	public Map<String, List<?>> getInMap() {
		return inMap;
	}

	public void setInMap(Map<String, List<?>> inMap) {
		this.inMap = inMap;
	}

	public Map<String, Object> getEqualMap() {
		return equalMap;
	}

	public void setEqualMap(Map<String, Object> equalMap) {
		this.equalMap = equalMap;
	}

	public Map<String, Object> getNotEqualMap() {
		return notEqualMap;
	}

	public void setNotEqualMap(Map<String, Object> notEqualMap) {
		this.notEqualMap = notEqualMap;
	}

	public Map<String, Object> getLikeMap() {
		return likeMap;
	}

	public void setLikeMap(Map<String, Object> likeMap) {
		this.likeMap = likeMap;
	}

	public Map<String, Object> getGtoeMap() {
		return gtoeMap;
	}

	public void setGtoeMap(Map<String, Object> gtoeMap) {
		this.gtoeMap = gtoeMap;
	}

	public Map<String, Object> getGtMap() {
		return gtMap;
	}

	public void setGtMap(Map<String, Object> gtMap) {
		this.gtMap = gtMap;
	}

	public Map<String, Object> getLtMap() {
		return ltMap;
	}

	public void setLtMap(Map<String, Object> ltMap) {
		this.ltMap = ltMap;
	}

	public Map<String, Object> getLtoeMap() {
		return ltoeMap;
	}

	public void setLtoeMap(Map<String, Object> ltoeMap) {
		this.ltoeMap = ltoeMap;
	}

	public Map<String, Object> getNotLikeMap() {
		return notLikeMap;
	}

	public void setNotLikeMap(Map<String, Object> notLikeMap) {
		this.notLikeMap = notLikeMap;
	}

	public Map<String, List<Object>> getNotInMap() {
		return notInMap;
	}

	public void setNotInMap(Map<String, List<Object>> notInMap) {
		this.notInMap = notInMap;
	}

	public Map<String, List<Object>> getBetweenMap() {
		return betweenMap;
	}

	public void setBetweenMap(Map<String, List<Object>> betweenMap) {
		this.betweenMap = betweenMap;
	}

	public Map<String, List<Object>> getNotBetweenMap() {
		return notBetweenMap;
	}

	public void setNotBetweenMap(Map<String, List<Object>> notBetweenMap) {
		this.notBetweenMap = notBetweenMap;
	}

	public List<String> getAppendSqlList() {
		return appendSqlList;
	}

	public void setAppendSqlList(List<String> appendSqlList) {
		this.appendSqlList = appendSqlList;
	}

	public List<String> getAndColumnSqlList() {
		return andColumnSqlList;
	}

	public void setAndColumnSqlList(List<String> andColumnSqlList) {
		this.andColumnSqlList = andColumnSqlList;
	}

	public List<String> getAppendMongoSqlList() {
		return appendMongoSqlList;
	}

	public void setAppendMongoSqlList(List<String> appendMongoSqlList) {
		this.appendMongoSqlList = appendMongoSqlList;
	}

	public List<String> getAndColumnMongoSqlList() {
		return andColumnMongoSqlList;
	}

	public void setAndColumnMongoSqlList(List<String> andColumnMongoSqlList) {
		this.andColumnMongoSqlList = andColumnMongoSqlList;
	}
	
}
