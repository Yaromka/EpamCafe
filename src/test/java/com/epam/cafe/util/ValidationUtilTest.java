package com.epam.cafe.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ValidationUtilTest {
    private final static String INVALID_PRICE = "1000";
    private List<String> invalidEmails = new ArrayList<>();
    private List<String> invalidPasswords = new ArrayList<>();
    private List<String> invalidPhones = new ArrayList<>();

    @Before
    public void init() {
        invalidEmails.add("tesuser@mail.ру");
        invalidEmails.add("tesuser@mail,ru");
        invalidEmails.add("tesuser@mail.ru1");

        invalidPasswords.add("Test12345");
        invalidPasswords.add("test123456");
        invalidPasswords.add("Testqwerty");
        invalidPasswords.add("678912345");

        invalidPhones.add("+375296660819");
        invalidPhones.add("+375(29)6660819");
        invalidPhones.add("+375(29)666-08-19");
        invalidPhones.add("+375-44-299-08-19");
    }

    @Test
    public void shouldReturnFalseInvalidPrice() {
        Assert.assertFalse(ValidationUtil.isPriceValid(INVALID_PRICE));
    }

    @Test
    public void shouldReturnFalseInvalidPassword() {
        for (String password:invalidPasswords) {
            Assert.assertFalse(ValidationUtil.isPassportValid(password));
        }
    }

    @Test
    public void shouldReturnFalseInvalidEmail() {
        for (String email:invalidEmails) {
            Assert.assertFalse(ValidationUtil.isEmailValid(email));
        }
    }

    @Test
    public void shouldReturnFalseInvalidPhone() {
        for (String phone:invalidPhones) {
            Assert.assertFalse(ValidationUtil.isPhoneValid(phone));
        }
    }
}