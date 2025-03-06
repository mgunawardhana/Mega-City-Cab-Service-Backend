package com.megacity.backend.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),

    CUSTOMER_READ("customer:read"),
    CUSTOMER_UPDATE("customer:update"),
    CUSTOMER_CREATE("customer:create"),
    CUSTOMER_DELETE("customer:delete"),

    DRIVER_READ("driver:read"),
    DRIVER_UPDATE("driver:update"),
    DRIVER_CREATE("driver:create"),
    DRIVER_DELETE("driver:delete");

    private final String permission;
}
