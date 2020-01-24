package com.mystudy.spring.api;

import com.mystudy.spring.enums.ResponseEnum;
import com.mystudy.spring.vo.ResponseVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {


    @GetMapping("/carts")
    public ResponseVo getCarts(){
        String s="11";
        return ResponseVo.success(ResponseEnum.SUCCESS);
    }


}
