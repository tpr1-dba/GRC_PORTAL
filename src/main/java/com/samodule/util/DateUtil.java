package com.samodule.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

	public static String getStringDate(Date date) throws ParseException {
		SimpleDateFormat strformatter = new SimpleDateFormat("yyyy-mm-dd");
		DateFormat dateformatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
		Date datec = (Date) dateformatter.parse(date.toString());
		System.out.println(datec);

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		String formatedDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-"
				+ cal.get(Calendar.DATE);
		System.out.println("formatedDate : " + formatedDate);
		return formatedDate;
	}

	public static Date getDateDDMMYYYY(String date) {
		SimpleDateFormat strformatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
		Date datec = null;
		try {
			System.out.println("Date 1 is: " + date);
			datec = (Date) strformatter.parse(date);
			System.out.println("Date 2 is: " + datec);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("getDDMMYYYY  ", e);
		}

		return datec;
	}

	public static Date getDDMMYYYYHHMMMSS(String date) {
		SimpleDateFormat strformatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Date datec = null;
		try {
			// System.out.println("Date 1 is: "+date);
			datec = (Date) strformatter.parse(date);
			// System.out.println("Date 2 is: "+datec);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return datec;
	}

	public static Date getDDMMYYYY(String date) {
		SimpleDateFormat strformatter = new SimpleDateFormat("dd-MM-yyyy");
		Date datec = null;
		try {
			// System.out.println("Date 1 is: "+date);
			datec = (Date) strformatter.parse(date);
			// System.out.println("Date 2 is: "+datec);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return datec;
	}

	public static String getStringDateDDMMYYYY(String strDate) throws ParseException {
		SimpleDateFormat strformatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat dateformatter = new SimpleDateFormat("dd/MM/yyyy");
		Date datec = (Date) strformatter.parse(strDate);
		System.out.println(datec);

		Calendar cal = Calendar.getInstance();
//			cal.setTime(datec);
//			String formatedDate =  +     cal.get(Calendar.DATE)+ "/" + (cal.get(Calendar.MONTH) + 1) + "/" +cal.get(Calendar.YEAR)   ;
//			System.out.println("formatedDate : " + formatedDate); 

		return dateformatter.format(datec);
	}

	public static String getStringDateDDMMYYYY(Date strDate) throws ParseException {

		DateFormat dateformatter = new SimpleDateFormat("dd/MM/yyyy");

		return dateformatter.format(strDate);
	}

	public static String getDateDDMMYYYY() throws ParseException {

		// DateFormat dateformatter = new SimpleDateFormat("dd/MM/yyyy");

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		String formatedDate = +cal.get(Calendar.DATE) + "" + (cal.get(Calendar.MONTH) + 1) + ""
				+ cal.get(Calendar.YEAR);
		System.out.println("formatedDate : " + formatedDate);
		return formatedDate;
	}

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMMM-yyyy");

	public static String getFormateDate(Date date) {
		return dateFormat.format(date);
	}
	
	
	public static String reportDateConvert(String originalDateString) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yy");
        String newDateString = null;
        try {
            Date date = originalFormat.parse(originalDateString);
            newDateString = newFormat.format(date);
            System.out.println(newDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
		return newDateString;
    }


}
