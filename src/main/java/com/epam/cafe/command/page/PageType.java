package com.epam.cafe.command.page;

import com.epam.cafe.constants.LevelAccess;

public enum PageType {
    //GUEST pages
    LOGIN_JSP("LOGIN_JSP", LevelAccess.GUEST),
    REGISTRATION_JSP("REGISTRATION_JSP", LevelAccess.GUEST),

    //ONLY_AUTHORIZED(COMMON) pages
    NOT_FOUND_PAGE_JSP("NOT_FOUND_PAGE_JSP", LevelAccess.ONLY_AUTHORIZED),

    //USER pages
    MY_ORDERS_JSP("MY_ORDERS_JSP", LevelAccess.USER),
    CLIENT_ACCOUNT_JSP("CLIENT_ACCOUNT_JSP", LevelAccess.USER),
    BASKET_JSP("BASKET_JSP", LevelAccess.USER),
    CLIENT_CHANGE_PASS_JSP("CLIENT_CHANGE_PASS_JSP", LevelAccess.USER),
    ADD_REVIEW_JSP("ADD_REVIEW_JSP", LevelAccess.USER),
    CLIENT_CORT_JSP("CLIENT_CORT_JSP", LevelAccess.USER),

    //ADMIN pages
    ORDERS_JSP("ORDERS_JSP", LevelAccess.ADMIN),
    NEW_DISH_JSP("NEW_DISH_JSP", LevelAccess.ADMIN),
    ADMIN_CHANGE_PASS_JSP("ADMIN_CHANGE_PASS_JSP", LevelAccess.ADMIN),
    ADMIN_CORT_JSP("ADMIN_CORT_JSP", LevelAccess.ADMIN),
    ADMIN_ACCOUNT_JSP("ADMIN_ACCOUNT_JSP", LevelAccess.ADMIN),
    NEW_CATEGORY_JSP("NEW_CATEGORY_JSP", LevelAccess.ADMIN);

    private final String name;
    private final String levelAccess;

    PageType(String name, String levelAccess) {
        this.name = name;
        this.levelAccess = levelAccess;
    }

    public String getLevelAccess() {
        return this.levelAccess;
    }

    @Override
    public String toString() {
        return name;
    }

    public static PageType fromString(String text) {
        for (PageType value : PageType.values()) {
            if (value.name.equalsIgnoreCase(text)) {
                return value;
            }
        }
        return null;
    }
}

