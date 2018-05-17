package com.epam.cafe.entity;

public enum OrderRating{
    ONE("1"),TWO("2"), THREE("3"), FOUR("4"), FIVE("5");

    private String value;

    OrderRating(String value) {
        this.value = value;
    }

    public static OrderRating stringToEnum(String string) {
        String numberToEnum = "";
        switch (string) {
            case "1" :
                numberToEnum = "ONE";
                break;
            case "2" :
                numberToEnum = "TWO";
                break;
            case "3" :
                numberToEnum = "THREE";
                break;
            case "4" :
                numberToEnum = "FOUR";
                break;
            case "5" :
                numberToEnum = "FIVE";
                break;
        }
        return OrderRating.valueOf(numberToEnum);
    }

    public String getValue() {
        return value;
    }
}
