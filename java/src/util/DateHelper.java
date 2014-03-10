package util;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateHelper {
    public static final String FORMAT = "MMMM d, yyyy hh:mm:ss";

    public static String convertToString(Date date){
        SimpleDateFormat format = new SimpleDateFormat(FORMAT);
        return format.format(date);
    }

    public static Date convertToDate(String dateString){
        Date date = null;
        try {
            date = new SimpleDateFormat(FORMAT).parse(dateString);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return date;
    }
}
