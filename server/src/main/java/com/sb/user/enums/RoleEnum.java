package com.sb.user.enums;

public enum RoleEnum {
    ADMIN("ADMIN"),
    OWNER("OWNER"),
    MANAGER("MANAGER"),
    SELLER("SELLER"),
    USER("USER");
   

    private String role;

    RoleEnum(String role) {
        this.role = role;
    }

    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
