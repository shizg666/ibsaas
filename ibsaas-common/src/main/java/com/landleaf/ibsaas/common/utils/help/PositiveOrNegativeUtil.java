package com.landleaf.ibsaas.common.utils.help;


/**
 * 判断dealorder的金额正负
 * @author Tony
 * @date 2016年1月13日
 */
public class PositiveOrNegativeUtil {
	
	public static boolean isPositive(int worktype){
		int statusInt = worktype/100;
		if(statusInt%2 !=0 ){
			return false;
		}
		return true;
	}
	
	public static String getSign(int worktype){
		if(isPositive(worktype)){
			return "+";
		}else{
			return "-";
		}
	}
	
	public static Integer getSignReturnInt( int worktype ) {
		if( isPositive( worktype ) ) {
			return 0;
		} else {
			return 1;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(PositiveOrNegativeUtil.getSign(401));
	}
}
