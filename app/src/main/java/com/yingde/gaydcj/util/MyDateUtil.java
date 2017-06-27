package com.yingde.gaydcj.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class MyDateUtil {

	/**
	 * 判断是否是一位数 如果是 则在前面加个零
	 * 
	 * @param time
	 * @return
	 */
	public static String getDate(String time) {
		if (StringUtils.isBlank(time)) {
			return null;
		} else if (time.length() == 1) {
			return "0" + time;
		} else {
			return time;
		}
	}

	/**
	 * String 都转换为八位日期
	 * 
	 * @return
	 */
	public static String getMyDateb(String date) {
		if (StringUtils.isBlank(date)) {
			return "";
		} else if (7 < date.length() && date.length() < 12) {
			String returndate = "";
			try {
				SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
				Date datetest = format.parse(date);
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
				returndate = df.format(datetest);
				// System.out.println(returndate);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return returndate;
		} else if (date.length() >= 13) {
			String returndate = "";
			try {
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy年MM月dd日hh:mm:ss");
				Date datetest = format.parse(date);
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
				returndate = df.format(datetest);
				// System.out.println(returndate);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return returndate;
		} else {
			return "";
		}
	}

	public static String getMyDateNZG(String date) {
		if (StringUtils.isBlank(date)) {
			return "";
		} else if (7 < date.length() && date.length() < 12) {
			String returndate = "";
			try {
				SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
				Date datetest = format.parse(date);
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
				returndate = df.format(datetest);
				// System.out.println(returndate);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return returndate;
		} else if (date.length() >= 13) {
			String returndate = "";
			try {
				SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
				Date datetest = format.parse(date);
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
				returndate = df.format(datetest);
				// System.out.println(returndate);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return returndate;
		} else {
			return "";
		}
	}

	// 防范宣传
	public static String getMyDateFFXC(String date) {
		if (StringUtils.isBlank(date)) {
			return "";
		} else if (7 < date.length() && date.length() < 12) {
			String returndate = "";
			try {
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy年MM月dd日hh:mm");
				Date datetest = format.parse(date);
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
				returndate = df.format(datetest);
				// System.out.println(returndate);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return returndate;
		} else if (date.length() >= 13) {
			String returndate = "";
			try {
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy年MM月dd日hh:mm");
				Date datetest = format.parse(date);
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
				returndate = df.format(datetest);
				// System.out.println(returndate);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return returndate;
		} else {
			return "";
		}
	}

	/**
	 * String 转换为自己的时间格式
	 * 
	 * @return
	 */
	public static String getMyDate(String date) {
		if (StringUtils.isBlank(date)) {
			return "";
		} else if (7 < date.length() && date.length() < 12) {
			String returndate = "";
			try {
				SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
				Date datetest = format.parse(date);
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
				returndate = df.format(datetest);
				// System.out.println(returndate);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return returndate;
		} else if (date.length() >= 13) {
			String returndate = "";
			try {
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy年MM月dd日hh:mm:ss");
				Date datetest = format.parse(date);
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
				returndate = df.format(datetest);
				// System.out.println(returndate);
				return returndate;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return "";
			}

		} else {
			return "";
		}
	}

}
