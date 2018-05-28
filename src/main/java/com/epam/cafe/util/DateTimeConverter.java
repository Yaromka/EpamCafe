package com.epam.cafe.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeConverter {
    private static final Logger LOGGER = LogManager.getLogger(DateTimeConverter.class.getName());

    private static final SimpleDateFormat SDF_DATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final SimpleDateFormat SDF_DATE = new SimpleDateFormat("yyyy-MM-dd");

    public static Date convertStringToDateTime(String dateTime) {
        Date orderDate;
        try{
            orderDate = SDF_DATETIME.parse(dateTime);
        }
        catch(ParseException e) {
            LOGGER.log(Level.FATAL, "An exception occurred during attempt to parse input parameter." );
            throw new IllegalArgumentException("An exception occurred during attempt to parse input parameter: ");
        }

        return orderDate;
    }

    public static Date convertStringToDate(String date) {
        Date orderDate;
        try{
            orderDate = SDF_DATE.parse(date);
        }
        catch(ParseException e){
            LOGGER.log(Level.FATAL, "An exception occurred during attempt to parse input date parameter." );
            throw new IllegalArgumentException("An exception occurred during attempt to parse input date parameter: ");
        }

        return orderDate;
    }

    public static String convertDateTimeToString(Date date) {
        return SDF_DATETIME.format(date);
    }
}
