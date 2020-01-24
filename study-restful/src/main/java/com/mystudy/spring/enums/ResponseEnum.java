package com.mystudy.spring.enums;

import lombok.Data;
import lombok.Getter;

@Getter
public enum  ResponseEnum {
    SUCCESS(0, "成功"),

            ;

    Integer code;

    String msg;

    ResponseEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
