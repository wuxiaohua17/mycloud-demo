package cn.com.ut.util;

import java.util.Calendar;
import java.util.Collection;

/**
 * @author wuxiaohua
 * @version 1.0
 * @date 2018/12/14 15:28
 */
public class CommonUtil {

	/**
	 * 判断字符串类型为空
	 *
	 * @param str
	 * @return 字符串类型为空
	 */
	public static boolean isEmpty(String str) {

		return str == null || "".equals(str.trim());
	}

	/**
	 * 判断集合是否空的
	 *
	 * @param collection
	 * @return 集合是否空的
	 */
	public static boolean isEmptyCollection(Collection<?> collection) {

		if (collection == null || collection.isEmpty())
			return true;
		return false;
	}

	/**
	 * 对指定的时间延长指定秒数
	 *
	 * @param date
	 * @param seconds
	 * @return java.util.Date
	 */
	public static java.util.Date addSeconds(java.util.Date date, int seconds) {

		if (date == null) {
			return date;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.SECOND, seconds);
		return cal.getTime();
	}
}
