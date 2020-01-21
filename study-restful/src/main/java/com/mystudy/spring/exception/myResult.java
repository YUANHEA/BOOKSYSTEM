package com.mystudy.spring.exception;

import java.io.Serializable;

public class myResult <T> implements Serializable {
    /**
     * 错误内容
     */
    private String error;

    /**
     * 自定义错误码
     */
    private int code;

    private String msg;

    public myResult(int code ,String msg){
        this.code=code;
        this.msg=msg;
    }

    public myResult(String error, int code)
    {
        this.error = error;
        this.code = code;
    }

//    自定义返回success
    public static <T> myResult success(int code ,String msg){
        return new myResult(code,msg);
    }

    public String getError()
    {
        return error;
    }

    public void setError(String error)
    {
        this.error = error;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMsg(){
        return msg;
    }

    public void setMsg(String msg){
        this.msg=msg;
    }

    public myResult(String msg) {
        this.msg = msg;
    }



    public enum ErrorCode{
        /**
         * 用户不存在
         */
        USER_NOT_FOUND(40401),

        /**
         * 用户已存在
         */
        USER_ALREADY_EXIST(40001),
        ;

        private int code;

        public int getCode()
        {
            return code;
        }

        ErrorCode(int code)
        {
            this.code = code;
        }
    }
}
