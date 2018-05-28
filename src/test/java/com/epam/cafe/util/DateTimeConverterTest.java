package com.epam.cafe.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class DateTimeConverterTest {
    private static final String DATE_TIME = "2018-10-09 22:15";
    private Date dateTime;

    @Before
    public void initExpectedDate() {
        Calendar dateTime = Calendar.getInstance();
        dateTime.set(2018,Calendar.OCTOBER,9,22,15, 0);
        this.dateTime = dateTime.getTime();
    }

    @Test
    public void convertStringToDateTime() {
        Date convertedDate = DateTimeConverter.convertStringToDateTime(DATE_TIME);

        String expected = dateTime.toString();
        String actual = convertedDate.toString() ;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void convertDateTimeToString() {
        String convertedDateTimeString = DateTimeConverter.convertDateTimeToString(dateTime);
        Assert.assertEquals(DATE_TIME, convertedDateTimeString);
    }
}