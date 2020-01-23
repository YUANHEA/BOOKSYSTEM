package com.mystudy.spring.exception;

import com.fengwenyi.javalib.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//异常处理
@ControllerAdvice
public class RuntimeExceptionHandler {

    @ExceptionHandler(UserLoginException.class)
    @ResponseBody
    public Result userLoginHandle(){
        return Result.error(10,"用户未登陆");
    }
}
