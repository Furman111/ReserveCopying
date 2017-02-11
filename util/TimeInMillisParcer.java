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

}
