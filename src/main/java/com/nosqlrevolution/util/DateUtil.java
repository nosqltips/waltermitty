package com.nosqlrevolution.util;

import com.ocpsoft.pretty.time.PrettyTime;
import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 *
 * @author cbrown
 */
public class DateUtil {
    public static String formatRelativeTime(String time) {
        Date d = parseDate(time).toDate();
        if (d == null) { return null; }
        return new PrettyTime().format(d);
    }

    public static String formatRelativeTime(DateTime dt) {
        return new PrettyTime().format(dt.toDate());
    }
    
    public static String formatRelativeTime(Date d) {
        return new PrettyTime().format(d);
    }

    public static String formatRelativeTime(Long l) {
        return new PrettyTime().format(new Date(l));
    }
    
    public static DateTime parseDate(String time) {
        if (time == null) { return null; }
        DateTime dt = null;
    
        try {
            DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
            dt = fmt.parseDateTime(time);
        } catch (Throwable t) {}
     
        return dt;
    }
}
