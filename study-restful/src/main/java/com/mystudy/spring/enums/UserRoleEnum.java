package com.mystudy.spring.enums;

import lombok.Getter;

@Getter
public enum UserRoleEnum {
    ORDINARY_USER(0),
    REAL_USER(1)
    ;

    Integer code;

    UserRoleEnum(Integer code) {
        this.code = code;
    }

}
