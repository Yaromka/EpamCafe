package com.epam.cafe.util;

import java.util.Date;

public class ValidationUtil {

    private static final String EMAIL_REGEX = "[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";
    private static final String PASSWORD_REGEX = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*[\\W|_]).{8,20}";
    private static final String PHONE_REGEX = "(^[+]{1}[\\d]{3}[\\ ][(]{1}[\\d]{2}[)]{1}[\\ ][\\d]{3}[-]{1}[\\d]{2}[-]{1}[\\d]{2}$)";
    private static final String PASSPORT_REGEX = "[A-Z]{2}\\d{7}";
    private static final String NAME_REGEX = "^[А-ЯA-Z][a-яa-z]{2,24}";
    private static final String SURNAME_REGEX = "^[А-ЯA-Z][a-яa-z]{2,24}(-[А-ЯA-Z][a-яa-z]{2,12})?";
    private static final String DATE_REGEX = "(^\\d{4}-\\d{2}-\\d{2}$)";
    private static final String TIME_REGEX = "^\\d{2}:\\d{2}$";
    private static final String DESCRIPTION_REGEX = "[А-Яа-я\\w\\s.,?!-+#%_()]{2,250}";
    private static final String PRICE_REGEX = "[\\d]{0,3}\\.[\\d]{0,2}";
    private static final String AMOUNT_REGEX = "\\d{1,3}";
    private static final String MARK_REGEX ="[12345]";

    public static boolean isDescriptionValid(String description) {
        return (description != null) && description.matches(DESCRIPTION_REGEX);
    }

    public static boolean isAmountValid(String amount) {
        return (amount != null) && amount.matches(AMOUNT_REGEX);
    }

    public static boolean isPriceValid(String price) {
        return (price != null) && price.matches(PRICE_REGEX);
    }

    public static boolean isPasswordValid(String password) {
        return (password != null) && password.matches(PASSWORD_REGEX);
    }

    public static boolean isEmailValid(String eMail) {
        return (eMail != null) && eMail.matches(EMAIL_REGEX);
    }


    public static boolean isSurnameValid(String surname) {
        return (surname != null) && surname.matches(SURNAME_REGEX);
    }

    public static boolean isPhoneValid(String phone) {
        return (phone != null) && phone.matches(PHONE_REGEX);
    }

    public static boolean isPassportValid(String passport) {
        return (passport != null) && passport.matches(PASSPORT_REGEX);
    }

    public static boolean isMarkValid(String mark) {
        return (mark != null) && mark.matches(MARK_REGEX);
    }

    public static boolean isNameValid(String name) {
        return (name != null) && name.matches(NAME_REGEX);
    }

    public static boolean isPasswordRepeatValid(String password, String passwordRepeat) {
        boolean resp = false;
        if (passwordRepeat != null && password != null) {
            resp = password.equals(passwordRepeat);
        }
        return resp;
    }

    public static boolean isDateValid(String dateParameter) {
        return dateParameter.matches(DATE_REGEX);
    }

    public static boolean isTimeValid(String timeParameter) {
        return timeParameter.matches(TIME_REGEX);
    }

    public static boolean isDateAfterNow(Date orderDate) {
        Date now = new Date();
        return orderDate.after(now);
    }

    public static boolean isTimePeriodValid(Date startDate, Date endDate) {
        int result = startDate.compareTo(endDate);
        return result <= 0;
    }
}
