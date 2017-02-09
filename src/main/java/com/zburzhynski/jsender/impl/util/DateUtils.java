package com.zburzhynski.jsender.impl.util;

import static com.zburzhynski.jsender.api.domain.CommonConstant.DASH;
import static com.zburzhynski.jsender.api.domain.CommonConstant.RUSSIAN_DATE_FORMAT;
import static com.zburzhynski.jsender.api.domain.CommonConstant.SPACE;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Contains common methods for working with dates.
 * <p/>
 * Date: 12.10.13
 *
 * @author Vladimir Zburzhynski
 */
public final class DateUtils {

    private static final int MILLISECONDS_IN_DAY = 86400000;

    private static final long MILLISECONDS_IN_MONTH = 2592000000L;

    private static final String TIME_TEMPLATE = "HH:mm:ss";

    private static final String YEAR_TEMPLATE = "yyyy";

    private static final String DAY_TEMPLATE = "dd";

    private static final String MONTH_TEMPLATE = "MM";

    private static final String MONTH_AND_YEAR_TEMPLATE = "MMMMM yyyy";

    private static final String REPORT_DATE_TEMPLATE = "dd MMMMM yyyy";

    private static final String RUSSIAN_LANGUAGE = "ru";

    private static final String RUSSIAN_REGION = "RU";

    private static final int END_HOUR_OF_DAY = 23;

    private static final int END_MINUTE_OF_HOUR = 59;

    private static final int END_SECOND_OF_MINUTE = 59;

    private static final int END_MILLISECOND_OF_SECOND = 99;

    /**
     * Constructor.
     */
    private DateUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks is one date before or equals other date.
     *
     * @param checkDate check date
     * @param endDate   end date
     * @return true if one date before other, else false
     */
    public static boolean beforeOrEqual(Date checkDate, Date endDate) {
        return org.apache.commons.lang3.time.DateUtils.truncatedCompareTo(checkDate, endDate,
            Calendar.DAY_OF_MONTH) <= 0;
    }

    /**
     * Checks is one date after or equal other date.
     *
     * @param checkDate check date
     * @param startDate start date
     * @return true if one date after other, else false
     */
    public static boolean afterOrEqual(Date checkDate, Date startDate) {
        return org.apache.commons.lang3.time.DateUtils.truncatedCompareTo(checkDate, startDate,
            Calendar.DAY_OF_MONTH) >= 0;
    }

    /**
     * Checks is date between dates range including endpoints.
     *
     * @param checkDate check date
     * @param startDate start date
     * @param endDate   end date
     * @return true if date between dates range, else false
     */
    public static boolean between(Date checkDate, Date startDate, Date endDate) {
        return afterOrEqual(checkDate, startDate) && beforeOrEqual(checkDate, endDate);
    }

    /**
     * Checks if two date objects are on the same day ignoring time.
     *
     * @param date1 date1
     * @param date2 date2
     * @return true if two dates on the same day, else false
     */
    public static boolean isSameDay(Date date1, Date date2) {
        return org.apache.commons.lang3.time.DateUtils.isSameDay(date1, date2);
    }

    /**
     * Gets current system time.
     *
     * @return current system time
     */
    public static Time getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat(TIME_TEMPLATE);
        return Time.valueOf(dateFormat.format(new Date()));
    }

    /**
     * Gets current month.
     *
     * @return current month
     */
    public static int getCurrentMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    /**
     * Gets current year.
     *
     * @return current year
     */
    public static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * Extracts day from date.
     *
     * @param date date
     * @return day from date
     */
    public static int extractDay(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(DAY_TEMPLATE);
        return Integer.parseInt(dateFormat.format(date));
    }

    /**
     * Extracts month from date.
     *
     * @param date date
     * @return day from date
     */
    public static int extractMonth(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(MONTH_TEMPLATE);
        return Integer.parseInt(dateFormat.format(date));
    }

    /**
     * Extracts year from date.
     *
     * @param date date
     * @return year from date
     */
    public static int extractYear(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(YEAR_TEMPLATE);
        return Integer.parseInt(dateFormat.format(date));
    }

    /**
     * Adds month to date.
     *
     * @param date  date to add
     * @param month month amount
     * @return new date
     */
    public static Date addMonthToDate(Date date, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + month);
        return calendar.getTime();
    }

    /**
     * Adds year to date.
     *
     * @param date date to add
     * @param year year amount
     * @return new date
     */
    public static Date addYearToDate(Date date, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + year);
        return calendar.getTime();
    }

    /**
     * Gets report date format format.
     *
     * @param date date
     * @return date in report format
     */
    public static String getReportDateFormat(Date date) {
        return formatDate(date, REPORT_DATE_TEMPLATE);
    }

    /**
     * Formats date by template.
     *
     * @param date     date
     * @param template template
     * @return formatted date
     */
    public static String formatDate(Date date, String template) {
        Locale locale = new Locale.Builder().setLanguage(RUSSIAN_LANGUAGE)
            .setRegion(RUSSIAN_REGION).build();
        DateFormat dateFormat = new SimpleDateFormat(template, locale);
        return dateFormat.format(date);
    }

    /**
     * Parse date from string.
     *
     * @param date     date to parse
     * @param template template
     * @return date
     */
    public static Date parseDate(String date, String template) {
        DateFormat dateFormat = new SimpleDateFormat(template);
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Gets age from two dates.
     *
     * @param birthdayDate birthday date
     * @param currentDate  current date
     * @return age
     */
    public static int getAge(Date birthdayDate, Date currentDate) {
        Calendar birthday = Calendar.getInstance();
        birthday.setTime(birthdayDate);
        Calendar current = Calendar.getInstance();
        current.setTime(currentDate);
        int age = current.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
        if (current.get(Calendar.MONTH) < birthday.get(Calendar.MONTH)) {
            age--;
        } else if (current.get(Calendar.MONTH) == birthday.get(Calendar.MONTH)
            && current.get(Calendar.DAY_OF_MONTH) < birthday.get(Calendar.DAY_OF_MONTH)) {
            age--;
        }
        return age;
    }

    /**
     * Counts days between two dates.
     *
     * @param start start date
     * @param end   end date
     * @return days
     */
    public static int daysBetween(Date start, Date end) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        return (int) ((end.getTime() - start.getTime()) / MILLISECONDS_IN_DAY);
    }

    /**
     * Counts month between two dates.
     *
     * @param start start date
     * @param end   end date
     * @return months
     */
    public static long monthsBetween(Date start, Date end) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        return (long) ((end.getTime() - start.getTime()) / MILLISECONDS_IN_MONTH);
    }

    /**
     * Gets start time of day.
     *
     * @param date date
     * @return start time of day
     */
    public static Date getStartTimeOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * Gets end time of day.
     *
     * @param date date
     * @return end time of day
     */
    public static Date getEndTimeOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, END_HOUR_OF_DAY);
        calendar.set(Calendar.MINUTE, END_MINUTE_OF_HOUR);
        calendar.set(Calendar.SECOND, END_SECOND_OF_MINUTE);
        calendar.set(Calendar.MILLISECOND, END_MILLISECOND_OF_SECOND);
        return calendar.getTime();
    }

    /**
     * Gets start date of the month.
     *
     * @param date date
     * @return start date of the month
     */
    public static Date getStartDateOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * Gets end date of month.
     *
     * @param date date
     * @return end date of month
     */
    public static Date getEndDateOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        return calendar.getTime();
    }

    /**
     * Gets start date of the year.
     *
     * @param date date
     * @return start date of the year
     */
    public static Date getStartDateOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
        return calendar.getTime();
    }

    /**
     * Gets end date of year.
     *
     * @param date date
     * @return end date of year
     */
    public static Date getEndDateOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        return calendar.getTime();
    }

    /**
     * Gets amount of days in month.
     *
     * @param month month
     * @param year  year
     * @return amount of days in month
     */
    public static int daysInMonth(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.YEAR, year);
        return calendar.getActualMaximum(Calendar.DATE);
    }

    /**
     * Gets dates range.
     *
     * @param startDate start date
     * @param endDate   end date
     * @return dates range
     */
    public static String getDateRange(Date startDate, Date endDate) {
        if (startDate == null && endDate == null) {
            return null;
        }
        if (startDate.equals(endDate)) {
            return formatDate(startDate, RUSSIAN_DATE_FORMAT);
        } else {
            int startDateMonth = extractMonth(startDate);
            int endDateMonth = extractMonth(endDate);
            if (startDate.equals(getStartDateOfMonth(startDate)) && endDate.equals(getEndDateOfMonth(endDate))
                && endDateMonth - startDateMonth == 0) {
                return formatDate(startDate, MONTH_AND_YEAR_TEMPLATE);
            } else {
                return formatDate(startDate, RUSSIAN_DATE_FORMAT) + SPACE + DASH + SPACE +
                    formatDate(endDate, RUSSIAN_DATE_FORMAT);
            }
        }
    }

}
