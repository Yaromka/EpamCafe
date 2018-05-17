package com.epam.cafe.entity;

public enum Role {
    ADMIN, CLIENT;

    public String getDbId(){
        return ADMIN.equals(this)?"1":"2";
    }

    public static Role getRole(String id) {
        String adminRoleId = ADMIN.getDbId();
        return adminRoleId.equals(id)? ADMIN : CLIENT;
    }
}

