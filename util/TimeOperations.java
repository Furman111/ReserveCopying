package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Furman on 08.02.2017.
 */
public class TimeOperations {

    public static String millisToDate(long timeInMillis) {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        return formatter.format(calendar.getTime());
    }

}
