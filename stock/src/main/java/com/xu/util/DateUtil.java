package com.xu.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 单元功能 ： 日期处理所有的函数单元 编 写 ： canse 日 期 ： 20050801 修 改 ： 20050908
 */
public class DateUtil {

	private static Log log = LogFactory.getLog(DateUtil.class);
	// 注意格里历和儒略历交接时的日期差别
	/**
	 * Automatically generated variable: CONSTANT_MINUS3
	 */
	private static final int CONSTANT_MINUS3 = -3;

	/**
	 * Automatically generated variable: CONSTANT_5HUNDRED
	 */
	private static final int CONSTANT_5HUNDRED = 500;

	/**
	 * Automatically generated variable: CONSTANT_FOURTEEN
	 */
	private static final int CONSTANT_FOURTEEN = 14;

	/**
	 * Automatically generated variable: CONSTANT_FIFTEEN
	 */
	private static final int CONSTANT_FIFTEEN = 15;

	/**
	 * Automatically generated variable: CONSTANT_EIGHTEEN
	 */
	private static final int CONSTANT_EIGHTEEN = 18;

	/**
	 * Automatically generated variable: CONSTANT_4HUNDRED
	 */
	private static final int CONSTANT_4HUNDRED = 400;

	/**
	 * Automatically generated variable: CONSTANT_HUNDRED
	 */
	private static final int CONSTANT_HUNDRED = 100;

	/**
	 * Automatically generated variable: CONSTANT_TWELVE
	 */
	private static final int CONSTANT_TWELVE = 12;

	/**
	 * Automatically generated variable: CONSTANT_SIXTY
	 */
	private static final int CONSTANT_SIXTY = 60;

	/**
	 * Automatically generated variable: CONSTANT_TWENTYFOUR
	 */
	private static final int CONSTANT_TWENTYFOUR = 24;

	/**
	 * Automatically generated variable: CONSTANT_THOUSAND
	 */
	private static final int CONSTANT_THOUSAND = 1000;

	private static final transient int GREGORIANCUTOVERYEAR = 1582;

	// 闰年中每月天数
	private static final int[] DAYS_P_MONTH_LY = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	// 平年中每月天数
	private static final int[] DAYS_P_MONTH_CY = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	// 代表数组里的年、月、日
	private static final int Y = 0, M = 1, D = 2;

	// 参与运算用
	// private int[] ymd = null;

	public DateUtil() {
	}

	/*----------------------------------------------------------------------------
	 功能 : 获取服务器的日期
	 返回 : YYYY-MM-DD;
	 */
	public static String getSrvDate() {
		// Calendar cld = Calendar.getInstance();
		// int yInt = cld.get(Calendar.YEAR);
		// int mInt = (cld.get(Calendar.MONTH))+1;
		// int dInt = cld.get(Calendar.DATE);
		//
		// //获取服务器当前时间的的年月日
		// String mStr = "0" + Integer.toString(mInt);
		// String dStr = "0" + Integer.toString(dInt);
		//
		// //将当前日期的转化为字符串
		// mStr = mStr.substring(mStr.length()-2,mStr.length());
		// dStr = dStr.substring(dStr.length()-2,dStr.length());
		//
		// //返回结果
		// return yInt + "-" + mStr + "-" + dStr;
		return getDate(new Date(), "yyyy-MM-dd");
	}

	/**
	 * @param pattern 格式
	 * @return
	 */
	public static String getDate(Date date, String pattern) {
		// Calendar cld = Calendar.getInstance();
		// int yInt = cld.get(Calendar.YEAR);
		// int mInt = (cld.get(Calendar.MONTH))+1;
		// int dInt = cld.get(Calendar.DATE);
		//
		// //获取服务器当前时间的的年月日
		// String mStr = "0" + Integer.toString(mInt);
		// String dStr = "0" + Integer.toString(dInt);
		//
		// //将当前日期的转化为字符串
		// mStr = mStr.substring(mStr.length()-2,mStr.length());
		// dStr = dStr.substring(dStr.length()-2,dStr.length());

		if (null == date)
			return "";

		SimpleDateFormat sd = new SimpleDateFormat(pattern, Locale.getDefault());
		return sd.format(date);

		// 返回结果
		// return yInt + "-" + mStr + "-" + dStr;
	}

	/*----------------------------------------------------------------------------
	 功能 : 返回服务器时间
	 返回 : HHMM
	 */
	public static String getSrvTime() {
		Calendar cld = Calendar.getInstance();
		int hInt = cld.get(Calendar.HOUR_OF_DAY);
		int mInt = cld.get(Calendar.MINUTE);

		String hStr = "0" + Integer.toString(hInt);
		String mStr = "0" + Integer.toString(mInt);

		hStr = hStr.substring(hStr.length() - 2, hStr.length());
		mStr = mStr.substring(mStr.length() - 2, mStr.length());

		return hStr + mStr;
	}

	/*----------------------------------------------------------------------------
	 功能 : 返回服务器时间
	 返回 : HHMMSS
	 */
	public static String getSrvTimeHHMMSS() {
		Calendar cld = Calendar.getInstance();
		int hInt = cld.get(Calendar.HOUR_OF_DAY);
		int mInt = cld.get(Calendar.MINUTE);
		int mSec = cld.get(Calendar.SECOND);

		String hStr = "0" + Integer.toString(hInt);
		String mStr = "0" + Integer.toString(mInt);
		String sStr = "0" + Integer.toString(mSec);

		hStr = hStr.substring(hStr.length() - 2, hStr.length());
		mStr = mStr.substring(mStr.length() - 2, mStr.length());
		sStr = sStr.substring(sStr.length() - 2, sStr.length());

		return hStr + mStr + sStr;
	}

	/*----------------------------------------------------------------------------
	 功能 : 返回服务器时间
	 返回 : HH:MM:SS
	 */
	public static String getSrvTimeHH_MM_SS() {
		Calendar cld = Calendar.getInstance();
		int hInt = cld.get(Calendar.HOUR_OF_DAY);
		int mInt = cld.get(Calendar.MINUTE);
		int mSec = cld.get(Calendar.SECOND);

		String hStr = "0" + Integer.toString(hInt);
		String mStr = "0" + Integer.toString(mInt);
		String sStr = "0" + Integer.toString(mSec);

		hStr = hStr.substring(hStr.length() - 2, hStr.length());
		mStr = mStr.substring(mStr.length() - 2, mStr.length());
		sStr = sStr.substring(sStr.length() - 2, sStr.length());

		return hStr + ":" + mStr + ":" + sStr;
	}

	/*---------------------------------------------------------------------------
	 功能 : 计算两个日期之间的差
	 参数 : 有效的YYYY-MM-DD日期字符串
	 参数 : (frmDate : 开始日期字符串)
	 参数 : (endDate : 结束日期字符串)
	 返回 : endDate - frmDate 的天数;
	 */
	public static long dateDiff(String frmDate, String endDate) {

		// Calendar cld = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		// int Days=0;

		try {
			Date fDate = sdf.parse(frmDate);
			Date eDate = sdf.parse(endDate);
			long l = eDate.getTime() - fDate.getTime();
			long d = l / (CONSTANT_TWENTYFOUR * CONSTANT_SIXTY * CONSTANT_SIXTY * CONSTANT_THOUSAND);
			return d;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return -1;
		}
	}

	/*---------------------------------------------------------------------------
	 功能 : 计算当前日期加上一定天数的后的日期
	 输入 ：有效的整型字符串
	 返回 ：返回yyyy-mm-dd
	 */
	public static String dateAdd(String Days) {
		int strTo;
		try {
			strTo = Integer.parseInt(Days);
		} catch (Exception e) {
			log.error("日期标识转换出错! : \n:::" + Days + "不能转为数字型");
			log.error(e.getMessage(), e);
			strTo = 0;
		}
		Calendar strDate = Calendar.getInstance(); // java.util包
		strDate.add(Calendar.DATE, strTo); // 日期减 如果不够减会将月变动

		// 生成 (YYYY-MM-DD) 字符串
		String strMoth = String.valueOf(strDate.get(Calendar.MONTH) + 1);
		String strDay = "0" + strDate.get(Calendar.DATE);
		strMoth = "0" + strMoth;
		strMoth = strMoth.substring(strMoth.length() - 2, strMoth.length());
		strDay = strDay.substring(strDay.length() - 2, strDay.length());

		String meStrDate = strDate.get(Calendar.YEAR) + "-" + strMoth + "-" + strDay;

		return meStrDate;
	}

	/*-------------------------------------------------------------------------
	 *功能 ： 检查传入的参数是否合法的日期
	 *参数 ： YYYY-MM-DD字符串
	 */
	public static boolean validate(String date) {

		boolean isSucceed = true;
		try {
			int[] ymd = splitYMD(date);

			if (0 == ymd[M] || CONSTANT_TWELVE < ymd[M]) {
				isSucceed = false;
				throw new IllegalArgumentException("月份数值错误");

			}

			if (isLeapYear(ymd[0])) {
				if (0 == ymd[D] || ymd[D] > DAYS_P_MONTH_LY[ymd[M] - 1]) {
					isSucceed = false;
					throw new IllegalArgumentException("日期数值错误");
				}
			} else {
				if (0 == ymd[D] || ymd[D] > DAYS_P_MONTH_CY[ymd[M] - 1]) {
					isSucceed = false;
					throw new IllegalArgumentException("日期数值错误");
				}
			}
		} catch (Exception e) {
			isSucceed = false;
			log.error(e.getMessage(), e);
		}

		return isSucceed;
	}

	/*---------------------------------------------------------------------------
	 功能 : 计算当前日期加上一定天数的后的日期
	 输入 ：有效的整型字符串
	 返回 ：返回yyyy-mm-dd
	 */
	public static Date addDay(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + days);
		return calendar.getTime();
	}

	/*---------------------------------------------------------------------------
	 功能 : 字符串转Date
	 输入 ：有效的整型字符串
	 返回 ：返回date
	 */
	public static Date Mydate(String MyString) {
		Date Mydate = null;
		try {
			String parrten = "yyyy-MM-dd";
			SimpleDateFormat formatter = new SimpleDateFormat(parrten, Locale.getDefault());
			ParsePosition pos = new ParsePosition(0);
			Mydate = formatter.parse(MyString, pos);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if (null == Mydate) {
			return new Date();
		}
		return Mydate;
	}

	public static Date mydate4LongFormat(String MyString) {
		Date Mydate = null;
		try {
			String parrten = "yyyy-MM-dd HH:mm:ss";
			SimpleDateFormat formatter = new SimpleDateFormat(parrten, Locale.getDefault());
			ParsePosition pos = new ParsePosition(0);
			Mydate = formatter.parse(MyString, pos);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if (null == Mydate) {
			return new Date();
		}
		return Mydate;
	}

	/**
	 * 
	 * @param MyString
	 * @return
	 */
	public static Date MydateWithoutDefault(String s) {
		Date ret = null;
		try {
			String parrten = "yyyy-MM-dd";
			SimpleDateFormat formatter = new SimpleDateFormat(parrten, Locale.getDefault());
			ParsePosition pos = new ParsePosition(0);
			ret = formatter.parse(s, pos);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ret;
	}

	public static Date Mydate4PsgBirthday(String MyString) {
		Date Mydate = null;
		try {
			String parrten = "yyyyMMdd";
			SimpleDateFormat formatter = new SimpleDateFormat(parrten, Locale.getDefault());
			ParsePosition pos = new ParsePosition(0);
			Mydate = formatter.parse(MyString, pos);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if (null == Mydate) {
			return new Date();
		}
		return Mydate;
	}

	/*---------------------------------------------------------------------------
	 功能 : Date转字符串
	 输入 ：Date
	 返回 ：YYYY-MM-DD
	 */
	public static String MyString(Date MyDate) {
		String ret = "1900-01-01";
		try {
			String parrten = "yyyy-MM-dd";
			SimpleDateFormat formatter = new SimpleDateFormat(parrten, Locale.getDefault());
			ret = formatter.format(MyDate);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ret;
	}

	/*---------------------------------------------------------------------------
	 功能 : Date转字符串
	 输入 ：Date
	 返回 ：YYYY-MM-DD
	 */
	public static String MyChineseDate(Date MyDate) {
		String ret = "1900年1月1日";
		try {
			String parrten = "yyyy年M月d日";
			SimpleDateFormat formatter = new SimpleDateFormat(parrten, Locale.getDefault());
			ret = formatter.format(MyDate);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ret;
	}

	// wangzhe
	public static String MyEnglishDate(Date MyDate) {
		String ret = "";
		String[] month = { "Jan", " Feb", " Mar", " Apr", " May", " Jun", " Jul", " Aug", " Sep", " Oct", " Nov",
				" Dec" };
		try {
			String str1 = DateUtil.MyString(MyDate);
			String[] str2 = str1.split("-");
			int k = Integer.parseInt(str2[1]);
			for (int i = 1; CONSTANT_TWELVE >= i; i++) {
				if (k == i) {
					str2[1] = month[i - 1];
					break;
				}
			}
			ret = str2[2] + "-" + str2[1].trim();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ret;
	}

	public static String date2String(Date myDate, String parrten) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(parrten, Locale.getDefault());
			return formatter.format(myDate);
		} catch (Exception e) {
			return "";
		}
	}

	public static String MyChineseTime(String MyTime) {

		if (4 != MyTime.length()) {
			return "";
		}

		StringBuffer sb = new StringBuffer(MyTime);

		sb.insert(2, ":");

		return sb.toString();
	}

	public static String MyString4PsgBirthday(Date MyDate) {
		String ret = "";
		try {
			String parrten = "yyyyMMdd";
			SimpleDateFormat formatter = new SimpleDateFormat(parrten, Locale.getDefault());
			ret = formatter.format(MyDate);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ret;
	}

	/*-------------------------------------------------------------------------
	 *功能 ： 检查传入的参数代表的年份是否为闰年
	 *参数 ： 年份
	 *返回 ： 真 假
	 */
	public static boolean isLeapYear(int year) {
		return year >= GREGORIANCUTOVERYEAR ? ((0 == year % 4) && ((0 != year % CONSTANT_HUNDRED) || (0 == year
				% CONSTANT_4HUNDRED))) : (0 == year % 4);
	}

	/**
	 * 功能 ： 将代表日期的字符串分割为代表年月日的整形数组 参数 ： YYYY-MM-DD 输出 ： 年月日数组
	 */
	public static int[] splitYMD(String date) {
		int[] ymd = { 0, 0, 0 };
		ymd[Y] = Integer.parseInt(date.substring(0, 4));
		ymd[M] = Integer.parseInt(date.substring(5, 7));
		ymd[D] = Integer.parseInt(date.substring(8, 10));
		return ymd;
	}

	/*-------------------------------------------------------------------------
	 * 将不足两位的月份或日期补足为两位
	 * 参数 ： 要补位的数字
	 * 返回 ： 补位后的字符串（1：返回01）
	 */
	public static String formatMonthDay(int decimal) {
		DecimalFormat df = new DecimalFormat("00");
		return df.format(decimal);
	}

	/*-------------------------------------------------------------------------
	 * 功能 ： 将不足四位的年份补足为四位
	 * 参数 ： 要补位的数字
	 * 返回 ： 补位后的字符串（05：返回0005
	 */
	public static String formatYear(int decimal) {
		DecimalFormat df = new DecimalFormat("0000");
		return df.format(decimal);
	}

	/*-------------------------------------------------------------------------
	 * 功能 ： 计算给定日期加上一定天数后的日期
	 * 参数 ： 给定的日期 运算天数
	 * 返回 ： 返回有效的YYYY-MM-DD
	 liming.hu update in 2006/12/20
	 */
	public static String addDays(String date, int days) {
		/*
		 * validate(date); ymd = splitYMD(date);
		 * 
		 * if( isLeapYear( ymd[Y] ) ){ ymd[D] += days; if( ymd[D] > DAYS_P_MONTH_LY[ymd[M] -1 ] ){ ymd[M] ++; ymd[D] =
		 * ymd[D] - DAYS_P_MONTH_LY[ymd[M] -1-1 ]; if(ymd[M] > 12){ ymd[M] -= 12; ymd[Y]++; } if( ymd[D] >
		 * DAYS_P_MONTH_LY[ymd[M] -1 ] ){ addDays(formatYear(ymd[Y])+ formatMonthDay(ymd[M])+
		 * formatMonthDay(DAYS_P_MONTH_LY[ymd[M] -1 ]), ymd[D] - DAYS_P_MONTH_LY[ymd[M] -1 ]); } } }else{ ymd[D] +=
		 * days; if( ymd[D] > DAYS_P_MONTH_CY[ymd[M] -1 ] ){ ymd[M] ++; ymd[D] = ymd[D] - DAYS_P_MONTH_CY[ymd[M] -1-1 ];
		 * if(ymd[M] > 12){ ymd[M] -= 12; ymd[Y]++; } if( ymd[D] > DAYS_P_MONTH_CY[ymd[M] -1 ] ){
		 * addDays(formatYear(ymd[Y])+ formatMonthDay(ymd[M])+ formatMonthDay(DAYS_P_MONTH_CY[ymd[M] -1 ]), ymd[D] -
		 * DAYS_P_MONTH_CY[ymd[M] -1 ]); } } } String returnString = ""; return formatYear(ymd[Y])+ "-"+
		 * formatMonthDay(ymd[M])+ "-"+ formatMonthDay(ymd[D]);
		 */
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(Mydate(date));
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + days);
		return MyString(calendar.getTime());

	}

	/*-------------------------------------------------------------------------
	 * @auth liliang
	 * 功能 ： 计算给定日期加上一定天数后的日期
	 * 参数 ： 给定的日期 运算天数
	 * 返回 ： 返回有效的YYYY-MM-DD
	 */
	public static String addDays_li(String date, int days) {
		Calendar c = null;
		String s = "";

		try {
			c = Calendar.getInstance();
			c.setTime(Mydate(date));
			c.add(Calendar.DAY_OF_MONTH, days);
			s = MyString(c.getTime());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return s;
	}

	// public static void main(String[] str) {
	//  
	// // String da = "2006-05-12 0840";
	//  
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddHHmm");
	//  
	// System.out.println(sdf.format(new Date()));
	//  
	// Calendar cal = Calendar.getInstance();
	//  
	// cal.add(Calendar.DATE, 1);
	//  
	// System.out.println(cal.get(Calendar.DATE));
	//  
	// }

	/*
	 * public static String addDay(String date, int days) { String s = ""; Date dates = null; SimpleDateFormat sdf = new
	 * SimpleDateFormat("yyyy-MM-dd"); try { dates = sdf.parse(date); long d = dates.getTime(); if(days==1) { dates =
	 * new Date(d+1000*60*60*24); } if(days==-1) { dates = new Date(d-1000*60*60*24); } s =
	 * dates.getYear()+"-"+dates.getMonth()+1+"-"+dates.getDay(); } catch (ParseException e) { // Auto-generated catch
	 * block e.printStackTrace(); } return s; }
	 */

	/*-------------------------------------------------------------------------
	 * 功能 ： 日期获取函数
	 * 参数 ： 无参数
	 * 返回 ： 返回"yyyymmddhhmmss"
	 */
	public static String getDateTimeNo() {
		Calendar cld = Calendar.getInstance();
		int yInt = cld.get(Calendar.YEAR);
		int mInt = cld.get(Calendar.MONTH) + 1;
		int dInt = cld.get(Calendar.DATE);
		int hInt = cld.get(Calendar.HOUR_OF_DAY);
		int mmInt = cld.get(Calendar.MINUTE);
		int sInt = cld.get(Calendar.SECOND);

		String sYear = "00" + yInt;
		sYear = sYear.substring(sYear.length() - 4, sYear.length());

		String sMonth = "00" + mInt;
		sMonth = sMonth.substring(sMonth.length() - 2, sMonth.length());

		String sDay = "00" + dInt;
		sDay = sDay.substring(sDay.length() - 2, sDay.length());

		String sHour = "00" + hInt;
		sHour = sHour.substring(sHour.length() - 2, sHour.length());

		String sminute = "00" + mmInt;
		sminute = sminute.substring(sminute.length() - 2, sminute.length());

		String sSecond = "00" + sInt;
		sSecond = sSecond.substring(sSecond.length() - 2, sSecond.length());

		return sYear + "-" + sMonth + "-" + sDay + " " + sHour + ":" + sminute + ":" + sSecond;
	}

	/**
	 * 字符串转日期 示例:stringToDate("2005-5-2 0:0:10");
	 * 
	 * @param str String 输入字符串日期可用三种格式 yyyy-MM-dd HH:mm:ss完整式 yyyy-MM-dd HH:mm不含秒 yyyy-MM-dd只有日期不含时间
	 * @return Date
	 * @throws Exception
	 */
	public static Date stringToDate(String str) {
		Date return_date = null;
		String format = "";
		if (null == str) {
			return null;
		}
		if (CONSTANT_EIGHTEEN < str.length()) {
			format = "yyyy-MM-dd HH:mm:ss";
		} else if (CONSTANT_FIFTEEN < str.length()) {
			format = "yyyy-MM-dd HH:mm";
		} else if (CONSTANT_FOURTEEN < str.length()) {
			format = "yyyy-MM-dd HHmm";
		} else {
			format = "yyyy-MM-dd";
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
			return_date = sdf.parse(str);
		} catch (ParseException e) {
			// throw new Exception(
			// "输入字符串的格式出错(格式为yyyy-MM-dd/yyyy-MM-dd HH:mm/yyyy-MM-dd HH:mm:ss)：" +
			e.getMessage();
		}
		return return_date;
	}

	/**
	 * 生成日期的超链接供快速查询机票
	 * 
	 * @param myDate
	 * @param dateNum rule 如早于当前日期最多显示三天
	 * @return 包含日期超链接的字符串供页面上显示
	 * @throws Exception
	 */
	public static String getOtherDateByDate(String myDate, int dateNum, String rDate, String flag) throws Exception {

		long d = 0;
		long t = 0;
		int start = 0;
		String nowDate = getSrvDate();
		try {
			d = dateDiff(nowDate, myDate);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

		StringBuffer sb = new StringBuffer(CONSTANT_5HUNDRED);
		sb.append("<td width=\"8%\" align=\"center\">");
		sb.append("更多日期查询");
		sb.append("</td>");
		t = DateUtil.dateDiff(rDate, nowDate);
		if (3 < d) {
			sb.append("<td width=\"11%\" align=\"center\" bgcolor=\"#FFFFFF\">");
			if (0 > t) {
				sb.append("<span class=\"style7\">今天<BR>" + getWeekStr(myFormatter.parse(nowDate)) + "</span>");
			} else {
				sb.append("<a href=\"javascript:nextQuery('" + nowDate + "','" + flag + "')\">今天<BR>"
						+ getWeekStr(myFormatter.parse(nowDate)) + "</a>");
			}
			sb.append("</td>");
			start = CONSTANT_MINUS3;
		} else {
			start = 0 - (int) d;
		}

		for (int i = start; i < start + 7; i++) {
			sb.append("<td width=\"11%\" align=\"center\" bgcolor=\"#FFFFFF\">");
			t = DateUtil.dateDiff(rDate, DateUtil.addDays(myDate, i));
			if (DateUtil.addDays(myDate, i).equals(myDate) || 0 > t) {
				sb.append("<span class=\"style7\">" + DateUtil.addDays(myDate, i) + "<BR>"
						+ getWeekStr(myFormatter.parse(DateUtil.addDays(myDate, i))) + "</span>");
			} else {
				sb.append("<a href=\"javascript:nextQuery('" + DateUtil.addDays(myDate, i) + "','" + flag + "')\">"
						+ DateUtil.addDays(myDate, i) + "<BR>"
						+ getWeekStr(myFormatter.parse(DateUtil.addDays(myDate, i))) + "</a>");
			}
			sb.append("</td>");
		}

		return sb.toString();
	}

	// 根据日期获取是星期几

	public static String getWeekStr(Date Str) {

		// SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		// java.util.Date mydate=new java.util.Date();
		SimpleDateFormat formatter4 = new SimpleDateFormat("E", Locale.CHINA);
		String WeekStr = formatter4.format(Str);
		return WeekStr;

	}

	public static String getWeek(Date str) {
		StringBuffer sb = new StringBuffer(getWeekStr(str));
		String week = "星期";
		String week1 = "周";
		if (0 == sb.indexOf(week)) {
			sb.replace(sb.indexOf(week), sb.indexOf(week) + week.length(), week1);
		}
		return sb.toString();
	}

	// 时间间隔
	public static String get2DateContrast(Date date1, Date date2) {

		// long count = date2.getTime()-date1.getTime();

		long count = get2DateTime(date1, date2);

		long hoursTime = CONSTANT_SIXTY * CONSTANT_SIXTY * CONSTANT_THOUSAND;

		long minutesTime = CONSTANT_SIXTY * CONSTANT_THOUSAND;

		long dayTime = CONSTANT_TWENTYFOUR * CONSTANT_SIXTY * CONSTANT_SIXTY * CONSTANT_THOUSAND;

		long day = count / dayTime;

		long hour = count % dayTime / hoursTime;

		long minutes = count % hoursTime / minutesTime;

		StringBuffer sb = new StringBuffer();

		sb.append(0 >= day ? "" : String.valueOf(day + "天"));
		sb.append(0 >= hour ? "" : String.valueOf(hour + "小时"));
		sb.append(0 >= minutes ? "" : String.valueOf(minutes + "分钟"));
		return sb.toString();
	}

	public static long get2DateTime(Date date1, Date date2) {

		long count = Math.abs(date2.getTime() - date1.getTime());

		return count;
	}

	public static String getStyle(Date date1, Date date2) {

		long count = get2DateTime(date1, date2);

		long hoursTime = CONSTANT_SIXTY * CONSTANT_SIXTY * CONSTANT_THOUSAND;

		if (count > 4 * hoursTime && 0 < count) {

			return "class='style3'";
		} else
			return "";
	}

	public static String getStyle1(Date date1, Date date2) {

		long count = get2DateTime(date1, date2);

		long hoursTime = CONSTANT_SIXTY * CONSTANT_SIXTY * CONSTANT_THOUSAND;

		if (count < 4 * hoursTime && 0 < count) {

			return "class='style3'";
		} else
			return "";
	}

	/**
	 * 时期加上小时数
	 * 
	 * @param date
	 * @param days 2007-07-27 0750
	 * @return
	 */
	public static String addHours_li(String date, int hours) {
		Calendar c = null;
		String s = "";

		try {
			c = Calendar.getInstance();
			c.setTime(stringToDate(date));
			c.add(Calendar.HOUR_OF_DAY, hours);

			int month = 1 + c.get(Calendar.MONTH);
			int day = c.get(Calendar.DATE);

			int hour = c.get(Calendar.HOUR_OF_DAY);

			int minute = c.get(Calendar.MINUTE);

			s += String.valueOf(10 > month ? "0" + month : String.valueOf(month)) + "月";

			s += String.valueOf(10 > day ? "0" + day : String.valueOf(day)) + "日 ";

			s += String.valueOf(10 > hour ? "0" + hour : String.valueOf(hour)) + ":";

			s += String.valueOf(10 > minute ? "0" + minute : String.valueOf(minute));

			// s = month<10?"0"+month:String.valueOf(month)+
			// "月"+(day<10?"0"+day:String.valueOf(day))+" "
			// +(hour<10?"0"+hour:String.valueOf(hour)) + ":"
			// + (minute<10?"0"+minute:String.valueOf(minute));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return s;
	}

	/**
	 * 计算当前时间加上一定分钟数的后的时间
	 * 
	 * @param date
	 * @param minutes
	 * @return
	 */
	public static Date addMinutes(Date date, int minutes) {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + minutes);
			return calendar.getTime();
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * 判断当前时间是否在时间date之前
	 * 
	 * @param date 要比较的时间
	 * @return
	 */
	public static boolean isDateBefore(Date date) {
		Date currentDate = new Date();
		return currentDate.before(date);
	}

	/**
	 * <code>dateA</code>是否大于等于<code>dateB</code> <li>Greater-than-or-equal (>= or ge)</li>
	 * 
	 * @param date
	 * @return
	 */
	public static boolean ge(Date dateA, Date dateB) {
		// return dateA.after(dateB) || dateA.equals(dateB);
		return dateA.getTime() >= dateB.getTime();
	}

	/**
	 * 计算两个日期之间所差的天数
	 * 
	 * @param dateA
	 * @param dateB
	 * @return
	 */
	public static int difference(Date dateA, Date dateB) {

		long difference = dateA.getTime() - dateB.getTime();
		difference /= CONSTANT_THOUSAND / CONSTANT_SIXTY / CONSTANT_SIXTY / CONSTANT_TWENTYFOUR;

		return Integer.parseInt(String.valueOf(difference));
	}

	/**
	 * 计算两个日期的时间间隔(毫秒数)
	 * 
	 * @param startDate开始时间
	 * @param endDate结束时间
	 * @return 毫秒数
	 */
	public static long getTimeInterval(Date startDate, Date endDate) {

		try {
			return endDate.getTime() - startDate.getTime();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return -1;
		}
	}

	/**
	 * 产生系统时间字符串 YYYYMMDDhhmmss
	 * 
	 * @return 系统时间字符串 YYYYMMDDhhmmss
	 */
	public static String getTimeStamp() {

		Date now = Calendar.getInstance().getTime();
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
		return format.format(now);
	}

	/**
	 * yyyyMMddHHmmss
	 * 
	 * @param dateString
	 * @return
	 */
	public static Date getTimeStamp(String dateString) {
		Date Mydate = null;
		try {
			String parrten = "yyyyMMddHHmmss";
			SimpleDateFormat formatter = new SimpleDateFormat(parrten, Locale.getDefault());
			ParsePosition pos = new ParsePosition(0);
			if (StringUtils.isNotEmpty(dateString) && 8 == dateString.length()) {
				dateString += "000000";
			}
			Mydate = formatter.parse(dateString, pos);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return Mydate;
	}

	/**
	 * 得到当前日期前一天的日期字符串 yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getPrevDayStr() {

		return DateUtil.date2String(DateUtil.addDay(new Date(), -1), "yyyy-MM-dd");
	}

	/**
	 * 
	 * 日期转换，比如把2006-09-20转换成20SEP06
	 * 
	 * @param queryDate 查询航班的日期
	 * @param yearNeed 是否需要年份信息
	 * @return dateStr 处理后的日期格式
	 */
	public static String formatQueryDate(String queryDate, boolean yearNeed) {

		queryDate = queryDate.replaceAll("-", "");
		String dateStr = "";
		String month_name = "JAN,FEB,MAR,APR,MAY,JUN,JUL,AUG,SEP,OCT,NOV,DEC";
		String monthEn = "";
		String[] month_name_Str;
		try {
			String year = queryDate.substring(2, 4);
			String month = queryDate.substring(4, 6);
			String day = queryDate.substring(6, 8);
			month_name_Str = month_name.split(",");
			int monthInt = Integer.parseInt(month) - 1;
			for (int i = 0; i <= month_name_Str.length; i++) {
				if (monthInt == i) {
					monthEn = month_name_Str[i];
				}
			}
			if (yearNeed) {
				dateStr = day + monthEn + year;
			} else {
				dateStr = day + monthEn;
			}
		} catch (Exception e) {
			dateStr = "20NOV08";
			log.error("ERROR:date" + e.toString());
			log.error(e.getMessage(), e);
		}
		return dateStr;
	}

	/**
	 * 
	 * 日期转换 比如把20SEP转换成2009-09-20
	 * 
	 * @param queryDate 查询航班的日期
	 * @return dateStr 处理后的日期格式
	 */
	public static String formatQueryDate(String Str) {
		Calendar cld = Calendar.getInstance();
		int yInt = cld.get(Calendar.YEAR);
		String dStr = Str.substring(0, 2);
		String mStr = Str.substring(2, 5);
		int mInt = 0;
		String DateStr = "";
		String Month_name = "JAN,FEB,MAR,APR,MAY,JUN,JUL,AUG,SEP,OCT,NOV,DEC";
		String[] Month_name_Str;
		try {
			Month_name_Str = Month_name.split(",");
			for (int i = 0; i <= Month_name_Str.length - 1; i++) {
				if (Month_name_Str[i].equals(mStr)) {
					mInt = i + 1;
					mStr = String.valueOf(mInt);
					mStr = "0" + Integer.toString(mInt);
					mStr = mStr.substring(mStr.length() - 2, mStr.length());
				}
			}
			Date now = new Date();
			String nowString = dateToString(now, "MM-dd");
			if (0 > (stringToDate(mStr + "-" + dStr, "MM-dd").compareTo(stringToDate(nowString, "MM-dd")))) {
				yInt += 1;
			}

			DateStr = yInt + "-" + mStr + "-" + dStr;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return DateStr;

	}

	public static java.util.Date stringToDate(String pstrValue, String pstrDateFormat) {
		if ((null == pstrValue) || (pstrValue.equals(""))) {
			return null;
		}
		java.util.Date dttDate = null;
		try {
			SimpleDateFormat oFormatter = new SimpleDateFormat(pstrDateFormat, Locale.getDefault());
			dttDate = oFormatter.parse(pstrValue);
			oFormatter = null;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return dttDate;
	}

	/**
	 * Date convert to String.
	 * 
	 * @return String representation of the given Date and DateFormat.
	 */
	public static String dateToString(Date pdttValue, String pstrDateFormat) {
		String pstrDate = null; // return value
		if (null == pdttValue) {
			return null;
		}
		if ((null == pstrDateFormat) || (pstrDateFormat.equals(""))) {
			pstrDateFormat = "yyyy-MM-dd";
		}
		SimpleDateFormat oFormatter = new SimpleDateFormat(pstrDateFormat, Locale.getDefault());
		pstrDate = oFormatter.format(pdttValue);
		return pstrDate;
	}

	/**
	 * 
	 * 日期转换 比如把20SEP09转换成2009-09-20
	 * 
	 * @param queryDate 查询航班的日期
	 * @return dateStr 处理后的日期格式
	 */
	public static String formatQueryDate2(String Str) {

		String yInt = Str.substring(5, 7);
		String dStr = Str.substring(0, 2);
		String mStr = Str.substring(2, 5);
		int mInt = 0;
		String DateStr = "";
		String Month_name = "JAN,FEB,MAR,APR,MAY,JUN,JUL,AUG,SEP,OCT,NOV,DEC";
		String[] Month_name_Str;
		try {
			Month_name_Str = Month_name.split(",");
			for (int i = 0; i <= Month_name_Str.length - 1; i++) {
				if (Month_name_Str[i].equals(mStr)) {
					mInt = i + 1;
					mStr = String.valueOf(mInt);
					mStr = "0" + Integer.toString(mInt);
					mStr = mStr.substring(mStr.length() - 2, mStr.length());
				}
			}
			DateStr = "20" + yInt + "-" + mStr + "-" + dStr;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return DateStr;

	}

	public static Timestamp getSysTime() {

		Timestamp sys_time = new Timestamp(Calendar.getInstance().getTimeInMillis());

		return sys_time;
	}
	
	
	@SuppressWarnings("deprecation")
	public static int getYear(Date date) {
		return date.getYear() + 1900;
	}

	public static int getSeason(Date date) {
		@SuppressWarnings("deprecation")
		int m = date.getMonth() + 1;
		return m % 3 == 0 ? m / 3 : m / 3 + 1;
	}

}
