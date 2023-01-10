package com.example.b3tempocasalini;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class Tools {
    private static final String LOG_TAG = Tools.class.getSimpleName();
    private static AtomicInteger atomicInteger = null;
    private static final int INITIAL_GENERATOR_VALUE = 2023;

    // prevent object instanciation
    private Tools() {
    }
    /*
     * --- Helpers methods ---
     *
     */
    /**
     * getNowDate("yyyy") would return "2022" at the time this comment is written
     *
     * @param pattern : pattern to be used by the date formatter (see {@link SimpleDateFormat}
     * class for date pattern explanations)
     * @return the device locale date at the time of the call. The date is formatted using the
     * given date pattern
     */
    public static String getNowDate(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.FRANCE);
        Date now = new Date();
        return sdf.format(now);
    }

    /**
     * Format date sent by EDF API into something more pretty
     * @param apiDate : the tempo date string as sent by the EDF API
     * @return a pretty formatted date in the style of "Mon. 19 Dec. 2022"
     */
    public  static String formatTempoHistoryDate(String apiDate) {
        try {
            String[] ymdArray = apiDate.split("-");
            Date date = new Date(Integer.parseInt(ymdArray[0])-1900, Integer.parseInt(ymdArray[1])-1, Integer.parseInt(ymdArray[2]));
            SimpleDateFormat sdf = new SimpleDateFormat("E d MMM yyyy", Locale.FRANCE);
            return sdf.format(date);
        } catch (NumberFormatException e) {
            Log.w(LOG_TAG,e.getMessage());
            return "?";
        }
    }

    /**
     * Helper function used to initialize a spinner view
     * @param n : the number of previous years to get from the current year
     * @return an array of string holding the n-th latest year dates as from now
     * for example at the year this comment is written (2023), getLastNYearDate(3) will return
     * {"2023","2022","2021"}
     */
    public static String[] getLastNYearDate(int n) {
        String[] dateArray = new String[n];
        try {
            // get current year date
            int curYear = Integer.parseInt(getNowDate("yyyy"));
            // fill date array with the last n year date
            for (int i=0; i<n; i++) {
                dateArray[i] = String.valueOf(curYear-i);
            }
        } catch (NumberFormatException e) {
            Log.w(LOG_TAG,e.getMessage());
        }
        return dateArray;
    }

    public static int getNextNotifId() {
        if (atomicInteger == null) {
            atomicInteger = new AtomicInteger(INITIAL_GENERATOR_VALUE);
            return atomicInteger.get();
        } else {
            return atomicInteger.incrementAndGet();
        }
    }
}