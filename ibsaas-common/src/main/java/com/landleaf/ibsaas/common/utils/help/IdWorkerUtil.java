package com.landleaf.ibsaas.common.utils.help;





import com.landleaf.ibsaas.common.utils.date.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class IdWorkerUtil {

	public final static long BASE_TIME = 1422766646843L;

	private static int SEQUENCE = (int) System.currentTimeMillis() % 1000;
	private static AtomicInteger SEQUENCE_ATOMIC = new AtomicInteger(SEQUENCE);

	/**
	 * 生成唯一ID
	 * 
	 * @throws Exception
	 * @author meff
	 */
	public static long getId() {
		long time = System.currentTimeMillis() - BASE_TIME;
		
		if (SEQUENCE_ATOMIC.get() >= 999999) {
			synchronized(IdWorkerUtil.class){
				if (SEQUENCE_ATOMIC.get() >= 999999) {
					SEQUENCE_ATOMIC = new AtomicInteger(0);
				}
			}
		}
		time = time * 1000000 + SEQUENCE_ATOMIC.incrementAndGet();
		return time;
	}

	public static long getId(long time) {
		time -= BASE_TIME;

		if (SEQUENCE_ATOMIC.get() >= 999999) {
			synchronized(IdWorkerUtil.class){
				if (SEQUENCE_ATOMIC.get() >= 999999) {
					SEQUENCE_ATOMIC = new AtomicInteger(0);
				}
			}
		}
		time = time * 1000000 + SEQUENCE_ATOMIC.incrementAndGet();
		return time;
	}

	/**
	 * 生成唯一ID
	 * 
	 * @throws Exception
	 * @author meff
	 */
	public static long getId_bak() {
		long time = System.currentTimeMillis() - BASE_TIME;
		SEQUENCE++;
		if (SEQUENCE == 9999) {
			SEQUENCE = 1000;
		}
		time = time * 10000 + SEQUENCE;
		return time;
	}
	/** 获取俩个日期之间第一个流水号和最后一个流水号
	 * @param firstDate
	 * @param lastDate
	 * @return
	 * @author sourny.yang
	 * @date 2015年12月17日
	 */
	public static List<Long> getBetweenDateFirstAndLastMillisSecondByIdWork(Date firstDate, Date lastDate){
		List<Long> resultList = new ArrayList<>();
		resultList.add(getIdByDate(DateUtil.getDateByThatDayFirstMilliSecond(firstDate).getTime(), true));
		resultList.add(getIdByDate(DateUtil.getDateByThatDayLastMilliSecond(lastDate).getTime(), false));
		return resultList;
	}
	public static long getIdByDate(long time,boolean isMin){
		long time_ = time - BASE_TIME;
		if(isMin)
			time_ = time_ * 10000;
		else
			time_ = time_ * 10000 + 9999;
		return time_;
	}

	/**
	 * 根据创建时间获取每月第一天和每月最后一天的id范围
	 * @param createtime
	 * @return
	 * @author Tony
	 * @date 2016年1月14日上午10:55:37
	 */
	public static List<Long> getBetweenIdsByCreatetime(Long createtime){
		List<Long> ids = new ArrayList<Long>(2);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(createtime);
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		ids.add(getIdByDate(calendar.getTimeInMillis(), true));
		calendar.set(Calendar.MONTH, month+1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		ids.add(getIdByDate(calendar.getTimeInMillis(), false));
		return ids;
	}
	
	public static void main(String[] args) {
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		/*try {
			Date date = sdf.parse("2015-12-16");
			long time = date.getTime();
			Date date2 = sdf.parse("2015-12-17");
			long time2 = date2.getTime();
			System.out.println(IdWorkerUtil.getIdByDate(time,true));
			System.out.println(IdWorkerUtil.getIdByDate(time2-1,false));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
//		IdWorkerUtil.getBetweenIdsByCreatetime(new Date().getTime());
		for (int i = 0; i < 9999999; i++) {
			System.out.println(IdWorkerUtil.getId());
		}
		System.out.println((IdWorkerUtil.getId() + "").length());
	}
}
