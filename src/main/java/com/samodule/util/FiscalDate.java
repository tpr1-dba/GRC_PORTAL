package com.samodule.util;


import java.util.Calendar;
import java.util.Date;


public class FiscalDate {

    private static final int    FIRST_FISCAL_MONTH  = Calendar.MARCH;

    private static Calendar calendarDate=           Calendar.getInstance();
   // private Calendar calendar = Calendar.getInstance();
    
//    public FiscalDate(Calendar calendarDate) {
//        this.calendarDate = calendarDate;
//    }
//    
    
//    public FiscalDate(int year, int month, int day){    	
//    	calendarDate.set(Calendar.YEAR, year);
//    	calendarDate.set(Calendar.MONTH, month);
//    	calendarDate.set(Calendar.DAY_OF_MONTH, day);
//    	calendarDate.set(Calendar.HOUR_OF_DAY, 0);
//    	calendarDate.set(Calendar.MINUTE, 0);
//    	calendarDate.set(Calendar.SECOND, 0);
//       // calendarDate=calendar;
//    }
//    
//    public FiscalDate(Date date) {
//        this.calendarDate = Calendar.getInstance();
//        this.calendarDate.setTime(date);
//    }

    public static void setCalenderDate(int year, int month, int day){    	
    	calendarDate.set(Calendar.YEAR, year);
    	calendarDate.set(Calendar.MONTH, month-1);
    	calendarDate.set(Calendar.DAY_OF_MONTH, day);
    	calendarDate.set(Calendar.HOUR_OF_DAY, 0);
    	calendarDate.set(Calendar.MINUTE, 0);
    	calendarDate.set(Calendar.SECOND, 0);
    }
    
    public static void setDate(Date date){
        calendarDate.setTime(date);
    }
    
    
    public static int getFiscalMonth() {
        int month = calendarDate.get(Calendar.MONTH);
        int result = ((month - FIRST_FISCAL_MONTH - 1) % 12) + 1;
        if (result < 0) {
            result += 12;
        }
        return result;
    }

    public static int getFiscalYear(int y, int m, int d) {
    	setCalenderDate(y,m,d);
        int month = calendarDate.get(Calendar.MONTH);
        int year = calendarDate.get(Calendar.YEAR);
        return (month >= FIRST_FISCAL_MONTH) ? year : year - 1;
    }

    public static int getCalendarMonth() {
        return calendarDate.get(Calendar.MONTH);
    }

    public static int getCalendarYear() {
        return calendarDate.get(Calendar.YEAR);
    }
    
    public static String  getFinancialYear(int y, int m, int d) {        
        int year = getFiscalYear(y,m,d);
      //  System.out.println("Current Date : " + calendar.getTime().toString());
        System.out.println("Fiscal Years : " + year + "-" + (year + 1));
       // System.out.println("Fiscal Month : " + fiscalDate.getFiscalMonth());
        System.out.println(" ");
        return year + "-" + (year + 1);
    }
//    public static void main(String[] args) {
//        // displayFinancialDate(Calendar.getInstance());
//    	FiscalDate fiscalDate=new FiscalDate(new Date());
//    	System.out.println(fiscalDate.getFinancialYear());
//    	System.out.println(fiscalDate.getFiscalYear());
//    
//     }
}
