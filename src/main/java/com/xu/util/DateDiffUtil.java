package com.xu.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 计算任意2个日期内的工作日，可扩展法假节假日
 * 
 * @author user
 */
public class DateDiffUtil {
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public static void main(String[] args) {
        System.out.println(DateUtil.date2String(getNextWorkDate(DateUtil.stringToDate("2016-04-15"))));

		// 2015-05-23周六，2015-05-24周日
		System.out.println(DateDiffUtil.getWorkDay("2015-05-22", "2015-05-23"));
		System.out.println(DateDiffUtil.getWorkDay("2015-05-23", "2015-05-23"));
		System.out.println(DateDiffUtil.getWorkDay("2015-05-23", "2015-05-24"));
		System.out.println(DateDiffUtil.getWorkDay("2015-05-23", "2015-05-26"));
		System.out.println(DateDiffUtil.getWorkDay("2015-05-22", "2015-05-26"));

		System.out.println(DateDiffUtil.getWorkDay(DateUtil.stringToDate("2015-05-26 09:00:00"),
				DateUtil.stringToDate("2015-05-26 09:00:00")));
		System.out.println(DateDiffUtil.getWorkDay(DateUtil.stringToDate("2015-05-26 09:00:00"),
				DateUtil.stringToDate("2015-05-26 10:00:00")));
		System.out.println(DateDiffUtil.getWorkDay(DateUtil.stringToDate("2015-05-24 09:00:00"),
				DateUtil.stringToDate("2015-05-25 10:00:00")));
		System.out.println(DateDiffUtil.getWorkDay(DateUtil.stringToDate("2015-05-25 11:00:00"),
				DateUtil.stringToDate("2015-05-26 10:00:00")));
		System.out.println(DateDiffUtil.getWorkDay(DateUtil.stringToDate("2015-05-25 09:00:00"),
				DateUtil.stringToDate("2015-05-26 10:00:00")));
		
		System.out.println(DateUtil.date2String(DateUtil.getNextWorkDay(DateUtil.stringToDate("2016-03-21")),"yyyy-MM-dd"));
		
		
		System.out.println(DateUtil.stringToDate("2015-05-24").compareTo(DateUtil.stringToDate("2015-05-24 09:00:00")) );
	}

	/**
	 * 计算工作日
	 * 
	 * @param beginDateStr
	 * @param endDateStr
	 */
	public static int getWorkDay(String beginDateStr, String endDateStr) {
		try {
			Date beginDate = sdf.parse(beginDateStr);
			Date endDate = sdf.parse(endDateStr);
			return getWorkDay(beginDate, endDate);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 计算工作日
	 * 
	 * @param beingDate
	 * @param endDate
	 * @return
	 */
	public static int getWorkDay(Date beingDate, Date endDate) {
		int workDay = 0;
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(beingDate);
		long time = endDate.getTime() - beingDate.getTime();
		long day = time % (3600000 / 24) == 0 ? time / 3600000 / 24 : time / 3600000 / 24 + 1;
		for (int i = 0; i < day; i++) {
			if (calendar.get(GregorianCalendar.DAY_OF_WEEK) != GregorianCalendar.SATURDAY
					&& calendar.get(GregorianCalendar.DAY_OF_WEEK) != GregorianCalendar.SUNDAY) {
				// 如果不是节假日
				if (!isHoliday(sdf.format(calendar.getTime()))) {
					workDay++;
				}
			}
			// 天数加1
			calendar.add(Calendar.DATE, 1);
		}
		return workDay;
	}

	// 春节放假三天，定义到2020年
	public static boolean isHoliday(String date) {
		List<String> holidays = new ArrayList<String>();
		holidays.add("2005-02-09");
		holidays.add("2005-02-10");
		holidays.add("2005-02-11");
		holidays.add("2006-01-29");
		holidays.add("2006-01-30");
		holidays.add("2006-01-31");
		holidays.add("2007-02-18");
		holidays.add("2007-02-19");
		holidays.add("2007-02-21");
		holidays.add("2008-02-07");
		holidays.add("2008-02-08");
		holidays.add("2008-02-09");
		holidays.add("2009-01-26");
		holidays.add("2009-01-27");
		holidays.add("2009-01-28");
		holidays.add("2010-02-14");
		holidays.add("2010-02-15");
		holidays.add("2010-02-16");
		holidays.add("2011-02-03");
		holidays.add("2011-02-04");
		holidays.add("2011-02-05");
		holidays.add("2012-01-23");
		holidays.add("2012-01-24");
		holidays.add("2012-01-25");
		holidays.add("2013-02-10");
		holidays.add("2013-02-11");
		holidays.add("2013-02-12");
		holidays.add("2014-01-31");
		holidays.add("2014-02-01");
		holidays.add("2014-02-02");
		holidays.add("2015-02-19");
		holidays.add("2015-02-20");
		holidays.add("2015-02-21");
        holidays.add("2016-02-08");
        holidays.add("2016-02-09");
        holidays.add("2016-02-10");
        holidays.add("2016-06-09");
        holidays.add("2016-06-10");
        holidays.add("2016-09-15");
        holidays.add("2016-09-16");
        holidays.add("2016-10-03");
        holidays.add("2016-10-04");
        holidays.add("2016-10-05");
        holidays.add("2016-10-06");
        holidays.add("2016-10-07");
		holidays.add("2017-01-28");
		holidays.add("2017-01-29");
		holidays.add("2017-01-30");
		holidays.add("2018-02-16");
		holidays.add("2018-02-17");
		holidays.add("2018-02-18");
		holidays.add("2019-02-05");
		holidays.add("2019-02-06");
		holidays.add("2019-02-07");
		holidays.add("2020-01-25");
		holidays.add("2020-01-26");
		holidays.add("2020-01-27");
		if (holidays.contains(date))
			return true;
		return false;
	}

    @SuppressWarnings("deprecation")
    public static Date getNextWorkDate(Date date) {
        while(true){
            date= DateUtil.addDay(date, 1);
            if (!isHoliday(DateUtil.date2String(date)) && date.getDay() != 6 && date.getDay() != 0) {
                return date;
            }
        }
    }
}
