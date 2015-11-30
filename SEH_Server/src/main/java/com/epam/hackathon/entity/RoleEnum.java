package com.epam.hackathon.entity;

enum RoleEnum {

    USER(1), ADMIN(2), GUARD(3);

    public int getRoleId() {
        return roleId;
    }

    private int roleId;

    RoleEnum(int i) {
        roleId = i;
    }


}
