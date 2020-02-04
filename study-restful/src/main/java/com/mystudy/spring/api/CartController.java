package com.mystudy.spring.api;

import com.mystudy.spring.ApiConst.ApiConst;
import com.mystudy.spring.domain.User;
import com.mystudy.spring.enums.ResponseEnum;
import com.mystudy.spring.form.CartAddForm;
import com.mystudy.spring.form.CartUpdateForm;
import com.mystudy.spring.service.CartService;
import com.mystudy.spring.vo.ResponseVo;
import io.swagger.annotations.Api;
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

        return cartService.list(user.getId());
//        return null;
    }

    @ApiOperation(value = "购物车添加商品",notes = "购物车添加商品")
    @PostMapping("/carts")
    public ResponseVo postCarts(@Valid @RequestBody CartAddForm cartAddForm,
                                HttpSession session){
        User user = (User) session.getAttribute(ApiConst.USER_DATA);
        System.out.println(cartAddForm);
        return cartService.add(user.getId(),cartAddForm);
//        return null;
    }

    @ApiOperation(value = "更新购物车某个产品数量",notes = "更新购物车某个产品数量")
    @PutMapping("/carts/{bookId}")
    public ResponseVo updata_quantity(@PathVariable("bookId") int bookId,
                                      @Valid @RequestBody CartUpdateForm form,
                                      HttpSession session){
        User user = (User) session.getAttribute(ApiConst.USER_DATA);
        return cartService.update(user.getId(), bookId, form);
    }
    @ApiOperation(value = "移除购物车某个产品",notes = "移除购物车某个产品")
    @DeleteMapping("/carts/{bookId}")
    public ResponseVo deleteBook(@PathVariable("bookId") int bookId,
                                 HttpSession session){
        User user = (User) session.getAttribute(ApiConst.USER_DATA);
        return cartService.delete(user.getId(), bookId);
    }

    @ApiOperation(value = "全选中",notes = "无参数,需要登录状态")
    @PutMapping("/carts/selectAll")
    public ResponseVo selectAll_books(HttpSession session){
        User user = (User) session.getAttribute(ApiConst.USER_DATA);
        return cartService.selectAll(user.getId());
    }


    @ApiOperation(value = "全不选中",notes = "无参数,需要登录状态")
    @PutMapping("/carts/unSelectAll")
    public ResponseVo unSelectAll_books(HttpSession session){
        User user = (User) session.getAttribute(ApiConst.USER_DATA);
        return cartService.unSelectAll(user.getId());
    }


    @ApiOperation(value = "获取购物中所有商品数量总和",notes = "无参数,需要登录状态")
    @GetMapping("/carts/books/sum")
    public ResponseVo getCarts_sun(HttpSession session){
        User user = (User) session.getAttribute(ApiConst.USER_DATA);
        return cartService.sum(user.getId());
    }


}
