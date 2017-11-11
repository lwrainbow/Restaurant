package tapandtake.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * helper functions for handling dates
 * @author lxy
 */
public class DateUtil {
    // The DATE_PATTERN and DATE_ID that are used for conversion.
    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String ORDER_NUMBER = "yyyyMMddHHmmss";
    
    //The date formatter.
    private static final SimpleDateFormat DATE_PATTERN_FORMATTER = new SimpleDateFormat(DATE_PATTERN);
    private static final SimpleDateFormat ORDER_NUMBER_FORMATTER = new SimpleDateFormat(ORDER_NUMBER);
    
    /**
     * return the given date as a well formatted String.
     * @param date the date to be returned as a String
     * @return formatted string
     */
    public static String formatDate(Date date){
        if (date == null){
            return null;
        }
        return DATE_PATTERN_FORMATTER.format(date);
    }
    
    public static String formatOrderNumber(Date date){
        if (date == null){
            return null;
        }
        return ORDER_NUMBER_FORMATTER.format(date);
    }
}
