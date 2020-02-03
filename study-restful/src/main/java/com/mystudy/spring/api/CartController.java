package com.mystudy.spring.api;

import com.mystudy.spring.ApiConst.ApiConst;
import com.mystudy.spring.domain.User;
import com.mystudy.spring.enums.ResponseEnum;
import com.mystudy.spring.form.CartAddForm;
import com.mystudy.spring.service.CartService;
import com.mystudy.spring.vo.ResponseVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @ApiOperation(value="购物车列表", notes="购物车列表")
    @GetMapping("/carts")
    public ResponseVo getCarts_list(HttpSession session){
        User user = (User) session.getAttribute(ApiConst.USER_DATA);

//        return cartService.list(user.getId());
        return null;
    }

    @ApiOperation(value = "购物车添加商品",notes = "购物车添加商品")
    @PostMapping("/carts")
    public ResponseVo postCarts(@Valid @RequestBody CartAddForm cartAddForm,
                                HttpSession session){
        User user = (User) session.getAttribute(ApiConst.USER_DATA);
//        return cartService.add(user.getId(),cartAddForm);
        return null;
    }

    @ApiOperation(value = "更新购物车某个产品数量",notes = "更新购物车某个产品数量")
    @PutMapping("/carts/{bookId}")
    public ResponseVo updata_quantity(@PathVariable("bookId") int bookId){
        return ResponseVo.success();
    }
    @ApiOperation(value = "移除购物车某个产品",notes = "移除购物车某个产品")
    @DeleteMapping("/carts/{productId}")
    public ResponseVo deleteProduct(@PathVariable("bookId") int bookId){
        return ResponseVo.success();
    }

    @ApiOperation(value = "全选中",notes = "无参数,需要登录状态")
    @PutMapping("/carts/selectAll")
    public ResponseVo selectAll_books(){
        return ResponseVo.success();
    }


    @ApiOperation(value = "全不选中",notes = "无参数,需要登录状态")
    @PutMapping("/carts/unSelectAll")
    public ResponseVo unSelectAll_books(){
        return ResponseVo.success();
    }


    @ApiOperation(value = "获取购物中所有商品数量总和",notes = "无参数,需要登录状态")
    @GetMapping("/carts/products/sum")
    public ResponseVo getCarts_sun(){
        return ResponseVo.success();
    }


}
