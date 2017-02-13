package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Furman on 11.02.2017.
 */
public class TimeInMillisParcer {

    public static long parseToTimeInMillis(String str) {
        long res = 0;
        if (str.charAt(8) != '_') res += Character.getNumericValue(str.charAt(8)) * 10 * 604800000;
        if (str.charAt(9) != '_') res += Character.getNumericValue(str.charAt(9)) * 604800000;
        if (str.charAt(16) != '_') res += Character.getNumericValue(str.charAt(16)) * 10 * 86400000;
        if (str.charAt(17) != '_') res += Character.getNumericValue(str.charAt(17)) * 86400000;
        if (str.charAt(25) != '_') res += Character.getNumericValue(str.charAt(25)) * 10 * 3600000;
        if (str.charAt(26) != '_') res += Character.getNumericValue(str.charAt(26)) * 3600000;
        if (str.charAt(36) != '_') res += Character.getNumericValue(str.charAt(36)) * 10 * 60000;
        if (str.charAt(37) != '_') res += Character.getNumericValue(str.charAt(37)) * 60000;
        return res;
    }

    public static String millisToDate(long timeInMillis) {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        return formatter.format(calendar.getTime());
    }

    public static String timeToCopyInMillisToString(long timeToCopyInMillis){
        if (timeToCopyInMillis>0) {
            long temp = timeToCopyInMillis;
            long weeks = 0, days = 0, hours = 0, minutes = 0;
            weeks = temp / 604800000;
            temp = temp % 604800000;
            days = temp / 86400000;
            temp = temp % 86400000;
            hours = temp / 3600000;
            temp = temp % 3600000;
            minutes = temp / 60000;

            StringBuilder res = new StringBuilder();
            if (weeks > 0)
                res.append(weeks + " нед. ");
            if (days > 0)
                res.append(days + " дн. ");
            if (hours > 0)
                res.append(hours+" час. ");
            if (minutes > 0)
                res.append(minutes + " мин.");
            return res.toString();
        }
        else
            return "0";
    }

}
