package com.epam.cafe.command;

import com.epam.cafe.constants.LevelAccess;

public enum CommandType {
    /**
     * Commands for guests.
     */
    LOGIN("LOGIN", LevelAccess.GUEST),
    SIGN_UP("SIGN_UP", LevelAccess.GUEST),

    /**
     * Common commands for the only authorized.
     */
    LOGOUT("LOGOUT", LevelAccess.ONLY_AUTHORIZED),
    CHANGE_PASS("CHANGE_PASS", LevelAccess.ONLY_AUTHORIZED),
    EDIT_USER("EDIT_USER", LevelAccess.ONLY_AUTHORIZED),

    /**
     * Commands for users.
     */
    GET_MENU("GET_MENU", LevelAccess.USER),
    GET_MY_ORDERS("GET_MY_ORDERS", LevelAccess.USER),
    ADD_TO_BASKET("ADD_TO_BASKET", LevelAccess.USER),
    REMOVE_FROM_BASKET("REMOVE_FROM_BASKET", LevelAccess.USER),
    MAKE_ORDER("MAKE_ORDER", LevelAccess.USER),
    ADD_REVIEW("ADD_REVIEW", LevelAccess.USER),

    /**
     * Commands for admin.
     */
    GET_USER_BY_SURNAME("GET_USER_BY_SURNAME", LevelAccess.ADMIN),
    GET_ORDERS_BY_USER("GET_ORDERS_BY_USER", LevelAccess.ADMIN),
    GET_DISHES("GET_DISHES", LevelAccess.ADMIN),
    ADD_DISH("ADD_DISH", LevelAccess.ADMIN),
    GET_CATEGORIES("GET_CATEGORIES", LevelAccess.ADMIN),
    ADD_CATEGORY("ADD_CATEGORY", LevelAccess.ADMIN),
    GET_ALL_USERS("GET_ALL_USERS", LevelAccess.ADMIN),
    CHANGE_PAY_STATUS("CHANGE_PAY_STATUS", LevelAccess.ADMIN),
    GET_ORDERS_BY_PARAMETERS("GET_ORDERS_BY_PARAMETERS", LevelAccess.ADMIN),
    UPDATE_ENABLE_STATUS("UPDATE_ENABLE_STATUS", LevelAccess.ADMIN),
    UPDATE_LOYALTY_POINTS("UPDATE_LOYALTY_POINTS", LevelAccess.ADMIN),

    /**
     * Commands which can use anyone.
     */
    LOCALE("LOCALE", LevelAccess.ANY),
    GET_PAGE("GET_PAGE", LevelAccess.ANY);

    private final String name;
    private final String levelAccess;

    CommandType(String name, String levelAccess) {
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

    public static CommandType fromString(String text) {
        for (CommandType value : CommandType.values()) {
            if (value.name.equalsIgnoreCase(text)) {
                return value;
            }
        }
        return null;
    }
}