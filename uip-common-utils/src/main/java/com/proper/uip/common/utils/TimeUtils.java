package com.proper.uip.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.FastDateFormat;

public class TimeUtils {

	public static final Date END_OF_THE_WORLD;

	static {
		Calendar c = Calendar.getInstance();
		c.set(2100, 1, 1);
		END_OF_THE_WORLD = c.getTime();
	}

	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	private static final FastDateFormat dateFormat = FastDateFormat.getInstance(DEFAULT_DATE_FORMAT);

	public static final String DEFAULT_TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final FastDateFormat timestampFormat = FastDateFormat.getInstance(DEFAULT_TIMESTAMP_FORMAT);

	/**
	 * 将日期格式化为 'yyyy-MM-dd'
	 * @param date 
	 */
	public static String formatDate(Date date) {
		return dateFormat.format(date);
	}

	/**
	 * 将日期格式化为 'yyyy-MM-dd HH:mm:ss'
	 * @param date
	 */
	public static String formatTimeStamp(Date date) {
		return timestampFormat.format(date);
	}

	/**
	 * 返回目标日期加上n天后的日期, n可以为负数
	 * @param target
	 * @param n
	 * @return
	 */
	public static Date addDays(Date target, int n) {
		if (n == 0) {
			return target;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(target);
		c.add(Calendar.DAY_OF_YEAR, n);
		return c.getTime();
	}

	/**
	 * 返回目标日期加上n年后的日期, n可以为负数
	 * @param target
	 * @param n
	 * @return
	 */
	public static Date addYears(Date target, int n) {
		if (n == 0) {
			return target;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(target);
		c.add(Calendar.YEAR, n);
		return c.getTime();
	}

	/**
	 * 取得当前年份的上下年份
	 * @param offset 偏移多少
	 * @return
	 */
	public static List<Integer> getRoundYear(int offset) {
		int nowYear = Calendar.getInstance().get(Calendar.YEAR);
		List<Integer> years = new ArrayList<Integer>();
		for (int i = nowYear - offset; i <= nowYear + offset; i++) {
			years.add(i);
		}
		return years;
	}

	/**
	 * 取得两个日期之间的天数差
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return
	 */
	public static int getDaysBetween(Date start, Date end) {
		Calendar cNow = Calendar.getInstance();
		Calendar cReturnDate = Calendar.getInstance();
		cNow.setTime(start);
		cReturnDate.setTime(end);
		setTimeToMidnight(cNow);
		setTimeToMidnight(cReturnDate);
		long todayMs = cNow.getTimeInMillis();
		long returnMs = cReturnDate.getTimeInMillis();
		long intervalMs = todayMs - returnMs;
		return millisecondsToDays(intervalMs);
	}

	private static int millisecondsToDays(long intervalMs) {
		return (int) (intervalMs / (1000 * 86400));
	}

	private static void setTimeToMidnight(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
	}

	/**
	 * 根据出生年月获得年龄
	 * @param birthday 出生年月(yyyy-MM-dd格式)
	 * @return 年龄
	 */
	public static String getAge(String birthday) {
		AssertUtils.hasLength(birthday);
		String birth = birthday.substring(0, 4);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String now = sdf.format(new Date());
		Integer value = Integer.parseInt(now) - Integer.parseInt(birth);
		String age = value.toString();
		return age;
	}

	public static Date parse(String source) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = sdf.parse(source);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return d;

	}

	public static Date parseTimeStamp(String source) {
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_TIMESTAMP_FORMAT);
		Date d = null;
		try {
			d = sdf.parse(source);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return d;

	}

	/**
	 * 计算日期的年份差
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return
	 */
	public static int getYearBetween(Date start, Date end) {
		int i = 0;
		Calendar s = Calendar.getInstance();
		s.setTime(start);
		Calendar e = Calendar.getInstance();
		e.setTime(end);
		i = e.get(Calendar.YEAR) - s.get(Calendar.YEAR);
		return i;
	}

	public static void main(String[] args) {
		Date date = new Date();
		System.out.println(date);
		System.out.println(addDays(date, -2));
	}

}
