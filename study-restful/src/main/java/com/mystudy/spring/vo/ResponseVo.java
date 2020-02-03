package com.mystudy.spring.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mystudy.spring.enums.ResponseEnum;
import lombok.Data;

/**
 * @Data lombok
 * @JsonInclude 过滤掉null字段，data里面的每个对象都需要加
 *
 * **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseVo<T> {

    private Integer status;

    private String msg;

    private T data;

    private ResponseVo(Integer code, String msg){
        this.status=code;
        this.msg=msg;
    }

    public ResponseVo(Integer status, T data) {
        this.status = status;
        this.data = data;
    }

    public static <T> ResponseVo<T> success(Integer code, String msg){
        return new ResponseVo<>(code,msg);
    }

    public static <T> ResponseVo<T> success(){
        return new ResponseVo<>(ResponseEnum.SUCCESS.getCode(),ResponseEnum.SUCCESS.getMsg());
    }

    public static <T> ResponseVo<T> success(ResponseEnum responseEnum){
        return new ResponseVo<>(responseEnum.getCode(),responseEnum.getMsg());
    }

    public static <T> ResponseVo<T> success(T data) {
        return new ResponseVo<>(ResponseEnum.SUCCESS.getCode(), data);
    }

    public static <T> ResponseVo<T> error(ResponseEnum responseEnum){
        return new ResponseVo<>(responseEnum.getCode(),responseEnum.getMsg());
    }

}
