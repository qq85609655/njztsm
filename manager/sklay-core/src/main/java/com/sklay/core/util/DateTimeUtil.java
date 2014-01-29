package com.sklay.core.util;

import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateTimeUtil extends org.apache.commons.lang.time.DateUtils {

	private static SimpleDateFormat FORMATTIME_DATE;

	private static SimpleDateFormat FORMATTIME_TIME;

	private static SimpleDateFormat FORMATTIME_DATE_TIME;

	private static SimpleDateFormat FORMATTIME_DATETIME;

	private static SimpleDateFormat FORMATTIME_ONLY_TIME;

	private static SimpleDateFormat FORMATTIME;

	static {
		FORMATTIME = new SimpleDateFormat("yyyyMMddHHmmss");
		FORMATTIME_DATE = new SimpleDateFormat("yyyy-MM-dd");
		FORMATTIME_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		FORMATTIME_DATE_TIME = new SimpleDateFormat("yyyy/MM/dd");
		FORMATTIME_DATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		FORMATTIME_ONLY_TIME = new SimpleDateFormat("HH:mm:ss");
	}

	public static Calendar getCalendar(Date date) {
		Calendar calendar = GregorianCalendar.getInstance();
		if (date == null) {
			calendar.setTime(new Date());
		} else {
			calendar.setTime(date);
		}
		return calendar;
	}

	public static void setToStartTimeOfTheDay(Calendar c) {
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
	}

	public static void setToEndTimeOfTheDay(Calendar c) {
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
	}

	public static Calendar getFirstDayOf(Date date, int field) {
		Calendar c = getCalendar(date);
		setToStartTimeOfTheDay(c);
		int index = c.getActualMinimum(field);
		if (Calendar.DAY_OF_WEEK == field) {
			index++;
		} else if (Calendar.DAY_OF_WEEK_IN_MONTH == field) {
			// TODO
		}
		c.set(field, index);
		return c;
	}

	public static Calendar getFirstDayOfNext(Date date, int field) {
		Calendar c = getFirstDayOf(date, field);
		c.add(field, 1);
		return c;
	}

	public static Calendar getLastDayBegining(Date date, int field) {
		Calendar c = getCalendar(date);
		setToStartTimeOfTheDay(c);
		c.set(field, c.getActualMaximum(field));
		return c;
	}

	public static Calendar getLastDayEnding(Date date, int field) {
		Calendar c = getCalendar(date);
		setToEndTimeOfTheDay(c);
		c.set(field, c.getActualMaximum(field));
		return c;
	}

	public static int get(Date date, int field) {
		return getCalendar(date).get(field);
	}

	public static String getDateTime(Timestamp sourceDate) {
		if (sourceDate == null) {
			return "";
		}
		return FORMATTIME_DATETIME.format(sourceDate);
	}

	public static String getDateTimeString(Date sourceDate) {
		if (sourceDate == null) {
			return "";
		}
		return FORMATTIME_DATE_TIME.format(sourceDate);
	}

	public static String getDateWithoutTime(Timestamp sourceDate) {
		if (sourceDate == null) {
			return "";
		}
		return FORMATTIME_DATE.format(sourceDate);
	}

	public static String getDateWithoutTime(Date sourceDate) {
		if (sourceDate == null) {
			return "";
		}
		return FORMATTIME_DATE.format(sourceDate);
	}

	public static String getDateTime(Date sourceDate) {
		if (sourceDate == null) {
			return "";
		}
		return FORMATTIME_DATETIME.format(sourceDate);
	}

	public static String getStringDateTime(Date sourceDate) {
		if (sourceDate == null) {
			return "";
		}
		return FORMATTIME_TIME.format(sourceDate);
	}

	/**
	 * 取得当前时间
	 * 
	 * @return Timestamp类型
	 */
	public static Timestamp getTimestamp() {
		return new Timestamp(new Date().getTime());
	}

	/**
	 * 取得当前时间
	 * 
	 * @return yyyy-MM-dd 格式
	 */
	public static String getCurrentDate() {
		return FORMATTIME_DATE.format(new java.util.Date());
	}

	/**
	 * 取得当前时间
	 * 
	 * @return yyMMdd格式
	 */
	public static String getCurrentSequnceDate() {
		String today = null;
		today = FORMATTIME_DATE.format(new java.util.Date());
		today = today.replace("-", "");
		today = today.substring(2, today.length());
		return today;
	}

	/**
	 * 取得当前时间
	 * 
	 * @return yyyy-MM-dd HH:mm:ss 格式
	 */
	public static String getCurrentTime() {
		return FORMATTIME_DATETIME.format(new java.util.Date());
	}

	/**
	 * 取得前一天日期
	 * 
	 * @return yyyy-MM-dd 格式
	 */
	public static Date getYesterDay() {

		ParsePosition pos = new ParsePosition(0);
		String dateTmp = FORMATTIME_DATE.format(new java.util.Date(System
				.currentTimeMillis() - 1000 * 3600 * 24));

		if (dateTmp.length() == 10) {
			return FORMATTIME_DATE.parse(dateTmp, pos);
		}
		return FORMATTIME_DATETIME.parse(dateTmp, pos);
	}

	/**
	 * 取得yyyy-MM-dd日期
	 * 
	 * @param day
	 *            (+时间增加day -时间后退day)
	 * @return yyyy-MM-dd 格式
	 */
	public static Date getDate(Long date, int day) {

		ParsePosition pos = new ParsePosition(0);

		String dateTmp = FORMATTIME_DATE.format(new java.util.Date(date + 1000
				* 3600 * 24 * day));
		return FORMATTIME_DATE.parse(dateTmp, pos);
	}

	/**
	 * 取得yyyy-MM-dd日期
	 * 
	 * @param day
	 * @return yyyy-MM-dd 格式
	 */
	public static Date getDate(String day) {

		ParsePosition pos = new ParsePosition(0);

		return FORMATTIME_DATE.parse(day, pos);
	}

	/**
	 * 取得yyyy-MM-dd日期
	 * 
	 * @param day
	 * @return yyyy-MM-dd 格式
	 */
	public static Date getDate(Date day, int hour, int min, int second) {

		ParsePosition pos = new ParsePosition(0);

		Date date = FORMATTIME_DATE.parse(FORMATTIME_DATE.format(day), pos);

		Long time = date.getTime() + hour * 60 * 60 * 1000 + min * 60 * 1000
				+ second * 1000;

		return new Date(time);
	}

	/**
	 * 取得yyyy-MM-dd日期
	 * 
	 * @param day
	 *            (+时间增加day -时间后退day)
	 * @return yyyy-MM-dd 格式
	 */
	public static String getDate(Long date) {
		return FORMATTIME_DATE.format(new java.util.Date(date));
	}

	/**
	 * 取得后一天日期
	 * 
	 * @return yyyy-MM-dd 格式
	 */
	public static Date getNextDay() {
		ParsePosition pos = new ParsePosition(0);
		String dateTmp = FORMATTIME_DATE.format(new java.util.Date(System
				.currentTimeMillis() + 1000 * 3600 * 24));
		if (dateTmp.length() == 10) {
			return FORMATTIME_DATE.parse(dateTmp, pos);
		}
		return FORMATTIME_DATETIME.parse(dateTmp, pos);
	}

	public static Date getDateNextNum(int n) {
		return new Date(System.currentTimeMillis() + 1000 * 3600 * 24 * n);
	}

	public static String getStringDateNextNum(int n) {
		return FORMATTIME_DATE.format(new Date(System.currentTimeMillis()
				+ 1000 * 3600 * 24 * n));
	}

	/**
	 * 取当前一天日期
	 * 
	 * @return yyyy-MM-dd 格式
	 */
	public static Date getCurrentDay() {
		ParsePosition pos = new ParsePosition(0);
		String dateTmp = FORMATTIME_DATE.format(new java.util.Date(System
				.currentTimeMillis()));
		if (dateTmp.length() == 10) {
			return FORMATTIME_DATE.parse(dateTmp, pos);
		}
		return FORMATTIME_DATETIME.parse(dateTmp, pos);
	}

	public static Date getDate() {
		ParsePosition pos = new ParsePosition(0);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = simpleDateFormat.format(new Date());
		return FORMATTIME_DATE.parse(date, pos);

	}

	/**
	 * 取得当前时间
	 * 
	 * @return yyyy-MM-dd HH-mm-ss 格式
	 */
	public static String getCompactTime() {
		SimpleDateFormat FORMATTIME = new SimpleDateFormat(
				"yyyy-MM-dd HH-mm-ss");
		return FORMATTIME.format(new java.util.Date());
	}

	/**
	 * 取得当前日期
	 * 
	 * @return yyyyMMdd 格式
	 */
	public static String getCompactDate() {
		SimpleDateFormat FORMATTIME = new SimpleDateFormat("yyyyMMdd");
		return FORMATTIME.format(new java.util.Date());
	}

	public static String getTimeInMillis() {
		Calendar cal = Calendar.getInstance();
		return Long.toString(cal.getTimeInMillis());
	}

	/**
	 * String转换成Date类型日期
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date getStringToDate(String strDate) {
		if (strDate == null) {
			return null;
		}
		ParsePosition pos = new ParsePosition(0);

		if (strDate.length() <= 8) {
			return FORMATTIME_ONLY_TIME.parse(strDate, pos);
		}

		if (strDate.length() == 10) {
			return FORMATTIME_DATE.parse(strDate, pos);
		}

		if (strDate.length() == 16) {
			return FORMATTIME_TIME.parse(strDate, pos);
		}
		return FORMATTIME_DATETIME.parse(strDate, pos);
	}

	public static Timestamp getStringToTimestamp(String strDate) {
		if (strDate == null) {
			return null;
		}

		if (strDate.length() >= 10) {
			// String year = "20"+strDate.substring(0,2);
			// String month = strDate.substring(3,5);
			// String day = strDate.substring(6,8);
			// strDate = year+"-"+month+"-"+day;
			return new Timestamp(getStringToDate(strDate).getTime());
		}
		return null;

	}

	/**
	 * 返回当前时间是一周中的第几天
	 * 
	 * @return 从周日到周六分别是1-7
	 */
	public static String getDayOfWeek() {
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		String dayofweek = String.valueOf(cal.get(Calendar.DAY_OF_WEEK));
		return dayofweek;
	}

	public static int getWeekNumber() {
		int w = -1;
		String week = getDayOfWeek();
		try {
			w = Integer.parseInt(week);
		} catch (Exception e) {
			return -1;
		}
		switch (w) {
		case 1:
			return 7;
		case 2:
			return 1;
		case 3:
			return 2;
		case 4:
			return 3;
		case 5:
			return 4;
		case 6:
			return 5;
		case 7:
			return 6;
		default:
			return -1;
		}
	}

	public static String getDayOfWeek(String week) {
		int w = -1;
		try {
			w = Integer.parseInt(week);
		} catch (Exception e) {
			return "";
		}
		switch (w) {
		case 1:
			return "星期日";
		case 2:
			return "星期一";
		case 3:
			return "星期二";
		case 4:
			return "星期三";
		case 5:
			return "星期四";
		case 6:
			return "星期五";
		case 7:
			return "星期六";
		default:
			return "";
		}
	}

	public static String backDayandWeek(Date date) {
		int n = 0;
		String[] weekDays = { "Sunday", "Monday", "Tuesday", "Wednesday",
				"Thursday", "Friday", "Saturday" };
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			n = c.get(Calendar.DAY_OF_WEEK) - 1;
			if (n < 0)
				n = 0;
		} catch (Exception e) {
			n = 0;
		}
		return weekDays[n];
	}

	public static String getYear(String datestr) {
		return datestr.substring(0, 4);
	}

	public static String getYear(Long time) {
		return getYear(FORMATTIME_DATE.format(new java.util.Date(time)));
	}

	/**
	 * 取得当前时间的年份
	 * 
	 * @return yyyy
	 */
	public static String getYear() {
		return getYear(getCurrentDate());
	}

	public static String getMonth(String datestr) {
		return datestr.substring(5, 7);
	}

	/**
	 * 取得当前时间的月份
	 * 
	 * @return
	 */
	public static Integer getMonth() {
		Calendar car = Calendar.getInstance();
		return car.get(Calendar.MONTH) + 1;
	}

	/**
	 * 取得当前时间的月份
	 * 
	 * @return
	 */
	public static Integer getNextMonth(Date date) {
		Calendar car = Calendar.getInstance();
		car.setTime(date);
		return car.get(Calendar.MONTH) + 1;
	}

	public static String getDay(String datestr) {
		return datestr.substring(8, 10);
	}

	/**
	 * 取得当前时间的日
	 * 
	 * @return
	 */
	public static Integer getDay() {
		Calendar car = Calendar.getInstance();
		return car.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 取得当前时间的日
	 * 
	 * @return
	 */
	public static Integer getNextDay(Date date) {
		Calendar car = Calendar.getInstance();
		car.setTime(date);
		return car.get(Calendar.DAY_OF_MONTH);
	}

	public static String getHour(String date) {
		return date.substring(11, 13);
	}

	public static String getHour() {
		return getHour(getCurrentTime());
	}

	/**
	 * 计算两个Date类型的时间差，单位转换成秒
	 * 
	 * @param starttime
	 * @param endtime
	 * @return 返回结果是endtime-starttime的时间差，单位是秒
	 */
	public static int getSecondDifference(Date starttime, Date endtime) {
		int diff = new Long((endtime.getTime() - starttime.getTime()) / 1000)
				.intValue();
		return diff;
	}

	public static int getSecondDifference(Timestamp starttime, Timestamp endtime) {
		int diff = new Long((endtime.getTime() - starttime.getTime()) / 1000)
				.intValue();
		return diff;
	}

	public static int getDaysInterval(String starttime, String endtime) {
		return getDaysInterval(getStringToDate(starttime),
				getStringToDate(endtime));
	}

	public static int getDaysInterval(Date starttime, Date endtime) {
		return new Long((endtime.getTime() - starttime.getTime()) / 86400000)
				.intValue();
	}

	public static int getMonths(Date date1, Date date2) {
		int iMonth = 0;
		int flag = 0;
		Calendar objCalendarDate1 = Calendar.getInstance();
		objCalendarDate1.setTime(date1);

		Calendar objCalendarDate2 = Calendar.getInstance();
		objCalendarDate2.setTime(date2);

		if (objCalendarDate2.equals(objCalendarDate1)) {
			return 0;
		}
		if (objCalendarDate1.after(objCalendarDate2)) {
			Calendar temp = objCalendarDate1;
			objCalendarDate1 = objCalendarDate2;
			objCalendarDate2 = temp;
		}
		if (objCalendarDate2.get(Calendar.DAY_OF_MONTH) < objCalendarDate1
				.get(Calendar.DAY_OF_MONTH)) {
			flag = 1;
		}
		if (objCalendarDate2.get(Calendar.YEAR) > objCalendarDate1
				.get(Calendar.YEAR)) {
			iMonth = ((objCalendarDate2.get(Calendar.YEAR) - objCalendarDate1
					.get(Calendar.YEAR))
					* 12
					+ objCalendarDate2.get(Calendar.MONTH) - flag)
					- objCalendarDate1.get(Calendar.MONTH);
		} else {
			iMonth = objCalendarDate2.get(Calendar.MONTH)
					- objCalendarDate1.get(Calendar.MONTH) - flag;
		}
		return iMonth;
	}

	public static boolean isDateBefore(String date1, String date2) {
		try {
			return FORMATTIME_DATETIME.parse(date1).before(
					FORMATTIME_DATETIME.parse(date2));
		} catch (Exception e) {
			return false;
		}
	}

	public static Timestamp getNotNullTimestampValue(String str) {
		Timestamp value;
		try {
			if (str == null || str.equals("")) {
				value = new Timestamp(System.currentTimeMillis());
			} else {
				value = new Timestamp(FORMATTIME_DATE.parse(str).getTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
			value = new Timestamp(System.currentTimeMillis());
		}
		return value;
	}

	public static Timestamp getNotNullTimestampValue(String str, String format) {
		Timestamp value;
		try {
			if (str == null || str.equals("")) {
				value = new Timestamp(System.currentTimeMillis());
			} else {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
				value = new Timestamp(simpleDateFormat.parse(str).getTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
			value = new Timestamp(System.currentTimeMillis());
		}
		return value;
	}

	public static boolean compareTotime(String time) {
		boolean bFlag = false;
		long l = 0;
		String currentTime = getCurrentTime();
		try {
			Date currentDate = FORMATTIME_DATE.parse(currentTime);
			Date date = FORMATTIME_DATE.parse(time);
			l = currentDate.getTime() - date.getTime();
		} catch (Exception e) {
			l = 0;
			e.printStackTrace();
		}
		if (l > 0)
			bFlag = true;
		return bFlag;
	}

	public static int compareTimstampStr(String sTime, String tTime,
			String format) {
		int nRslt = 10;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		long l = 10;
		try {
			Date sDate = simpleDateFormat.parse(sTime);
			Date tDate = simpleDateFormat.parse(tTime);
			l = sDate.getTime() - tDate.getTime();
		} catch (Exception e) {
			nRslt = 2147483647;
			e.printStackTrace();
		}
		if (l > 0)
			nRslt = 1;
		else if (l == 0)
			nRslt = 0;
		else if (l < 0)
			nRslt = -1;
		return nRslt;
	}

	public static String getCurrentDay(String s) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(s);
		return simpleDateFormat.format(new Date());
	}

	public static String getTimetoString(String seconds) {
		if (StringUtils.isEmpty(seconds)) {
			return "";
		}
		long l = 0;
		try {
			l = Long.parseLong(seconds);
		} catch (Exception e) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date d = new Date(l * 1000 - sdf.getTimeZone().getRawOffset());
		String str = sdf.format(d);
		return str;
	}

	public static Date getDateBeforeNum(int n) {
		return new Date(System.currentTimeMillis() - 1000 * 3600 * 24 * n);
	}

	public static int daysOfMonth(int year, int month) {
		int days = 0; // 该月的天数
		Calendar c = Calendar.getInstance();
		c.set(year, month - 1, 1); // java中Calendar类中的月从0-11，所以要month-1

		// 日历每天向后滚一天，如果月份相等，天数+1，月份不等，说明已经到下月第一天了，跳出循环
		while (true) {
			if (c.get(Calendar.MONTH) == (month - 1)) {
				days++;
				c.add(Calendar.DAY_OF_YEAR, 1);
			} else {
				break;
			}
		}
		return days;
	}

	public static boolean isBetween(String start, String end) {
		String currentTime = new SimpleDateFormat("HH:mm").format(Calendar
				.getInstance().getTime());
		long statr_time = getMimit(start);
		end = end.replaceFirst("00:", "24:");
		long end_time = getMimit(end);
		long cur_time = getMimit(currentTime);
		if (statr_time <= cur_time && cur_time <= end_time)
			return true;
		return false;
	}

	public static long getMimit(String time) {
		String string_hour = time.split(":")[0];
		String string_min = time.split(":")[1];

		int hour = Integer.valueOf(string_hour);
		int min = Integer.valueOf(string_min);

		return (60 * hour + min);
	}

	/**
	 * 在指定时间执行一次任务
	 * 
	 * @param date
	 *            日期
	 * @param time
	 *            时间
	 * @return 格式化后的表达式
	 */
	public static String parseCronExpression(String date, String time) {
		Calendar car = Calendar.getInstance();
		String dateTime = StringUtils.isEmpty(date) ? time : (StringUtils
				.isEmpty(time) ? date : (date + " " + time));
		car.setTime(getStringToDate(dateTime));
		StringBuffer result = new StringBuffer("");
		result.append(car.get(Calendar.SECOND)).append(" ");
		result.append(car.get(Calendar.MINUTE)).append(" ");
		result.append(car.get(Calendar.HOUR_OF_DAY)).append(" ");
		result.append(car.get(Calendar.DAY_OF_MONTH)).append(" ");
		result.append((car.get(Calendar.MONTH)) + 1).append(" ");
		result.append("?").append(" ");
		result.append(car.get(Calendar.YEAR));
		return result.toString();
	}

	public static Calendar parseDateTime(String dateTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getStringToDate(dateTime));

		return cal;
	}

	public static Calendar parseDateTime(Date dateTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateTime);

		return cal;
	}

	/** 计算年龄 */
	public static Integer getAge(Date birthDay) {
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthDay)) {
			throw new IllegalArgumentException(
					"The birthDay is before Now.It's unbelievable!");
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTime(birthDay);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				age--;
			}
		}

		return age;
	}

	public static String getDateTimeWithoutChar(Date sourceDate) {
		if (sourceDate == null) {
			return "";
		}
		return FORMATTIME.format(sourceDate);
	}

	public static void main(String[] args) {

		System.out.println(parseDateTime("2014-01-17 23:12:22").get(
				Calendar.HOUR_OF_DAY));
		System.out.println(parseDateTime("2014-01-17 23:12:22").get(
				Calendar.MINUTE));
		System.out.println(parseDateTime("2014-01-17 23:12:22").get(
				Calendar.SECOND));

		System.out.println(getDate(new Date(), 23, 12, 22));

		System.out.println(getStringDateNextNum(1));
	}
}
