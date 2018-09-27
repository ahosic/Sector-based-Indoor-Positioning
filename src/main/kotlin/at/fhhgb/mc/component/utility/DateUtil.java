package at.fhhgb.mc.component.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Neidschel on 10/03/16.
 */
public class DateUtil {
    public static String getDate(long timeStamp){
        long scanResultTimestampInMillisSinceEpoch = System.currentTimeMillis() - System.currentTimeMillis() + (timeStamp / 1000);

        try{
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
            Date netDate = (new Date(scanResultTimestampInMillisSinceEpoch));
            return dateFormat.format(netDate);
        }
        catch(Exception ex){
            return "timestamp error";
        }
    }

}
