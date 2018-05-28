package com.epam.cafe.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HashPasswordUtilTest {
    private final static String expected = "b1b3773a05c0ed0176787a4f1574ff0075f7521e";
    private final static String password = "qwerty";

    @Test
    public void sha1() {
        String actual = HashPasswordUtil.sha1(password);
        assertEquals(expected, actual);
    }
}