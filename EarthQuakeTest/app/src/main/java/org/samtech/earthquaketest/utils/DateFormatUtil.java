package org.samtech.earthquaketest.utils;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatUtil {
    @Nullable
    @SuppressLint("SimpleDateFormat")
    public static String formatDate(DateTime date, boolean showHours) {
        SimpleDateFormat sdf = showHours ?
                new SimpleDateFormat("yyyy-MM-dd HH:mm") : new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date.toDate());
    }

    @SuppressLint("SimpleDateFormat")
    public static boolean isCurrentDate(String paramToCompare) {
        try {
            SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
            Date strDate = dfDate.parse(paramToCompare);
            Date currentDate = dfDate.parse(dfDate.format(new Date()));

            if (strDate.equals(currentDate)) {
                return true;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @SuppressLint("SimpleDateFormat")
    public static boolean isAfterDate(String paramToCompare){

        try {
            SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
            Date strDate = dfDate.parse(paramToCompare);
            Date currentDate = dfDate.parse(dfDate.format(new Date()));

            if (strDate.after(currentDate)) {
                return true;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;

    }

}
