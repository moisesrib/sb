package com.sb.infra.security.role;

import com.sb.user.enums.RoleEnum;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authentication {
    RoleEnum role();
}