package util;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateHelper {
    public static final String FORMAT_JAVASCRIPT = "MMMM d, yyyy HH:mm:ss";
    public static final String FORMAT_GUI = "dd-MM-yyyy, HH:mm";
    public static final String FORMAT_DB = "yyyy-MM-dd HH:mm:ss";

    public static String convertToString(Date date, String FORMAT){
        return new SimpleDateFormat(FORMAT).format(date);
    }

    public static Date convertToDate(String dateString, String format){
        Date date = null;
        try {
            date = new SimpleDateFormat(format).parse(dateString);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return date;
    }
}
