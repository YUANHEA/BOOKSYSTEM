package com.mystudy.spring.enums;

import lombok.Getter;

@Getter
public enum BookStatusEnum {

    ON_SALE(1),

    OFF_SALE(2),

    DELETE(3),



    ;

    Integer code;

    BookStatusEnum(Integer code) {
        this.code = code;
    }
}
