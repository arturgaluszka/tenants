package com.tenantsproject.flatmates.utils;

import org.joda.time.DateTime;

public class TimeUtils {
    public static String displayDate(long timeMS){
        DateTime time = new DateTime(timeMS);
        String displayedDate;
        if (timeMS==0) {
            displayedDate = "none";
        } else {
            displayedDate = time.toString("dd/MM/YYYY HH:mm:ss");
        }
        return displayedDate;
    }
}
