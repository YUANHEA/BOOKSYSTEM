package com.mystudy.spring.api;

import com.mystudy.spring.ApiConst.ApiConst;
import com.mystudy.spring.domain.User;
import com.mystudy.spring.form.ShippingForm;
import com.mystudy.spring.service.ShippingService;
import com.mystudy.spring.vo.ResponseVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class ShippingController {

    @Autowired
    private ShippingService shippingService;

    @ApiOperation(value="添加地址", notes="添加地址")
    @PostMapping("/shippings")
    public ResponseVo add(@Valid @RequestBody ShippingForm form,
                          HttpSession session){
        System.out.println("shippings_post:"+form);
        User user = (User) session.getAttribute(ApiConst.USER_DATA);
        return shippingService.add(user.getId(), form);
    }

    @ApiOperation(value="删除地址", notes="删除地址")
    @DeleteMapping("/shippings/{shippingId}")
    public ResponseVo delete(@PathVariable Integer shippingId,
                             HttpSession session){
        User user = (User) session.getAttribute(ApiConst.USER_DATA);
        return shippingService.delete(user.getId(), shippingId);
    }

    @ApiOperation(value="更新地址", notes="更新地址")
    @PutMapping("/shippings/{shippingId}")
    public ResponseVo update(@PathVariable Integer shippingId,
                             @Valid @RequestBody ShippingForm form,
                             HttpSession session){
        User user = (User) session.getAttribute(ApiConst.USER_DATA);
        return shippingService.update(user.getId(), shippingId, form);
    }

    @ApiOperation(value="选中查看具体的地址", notes="选中查看具体的地址")
    @GetMapping("/shippings/{shippingId}")
    public ResponseVo update(@PathVariable Integer shippingId,
                             HttpSession session){
        User user = (User) session.getAttribute(ApiConst.USER_DATA);
        return shippingService.getOneShipping(shippingId,user.getId());
    }

    @ApiOperation(value="地址列表", notes="地址列表")
    @GetMapping("/shippings")
    public ResponseVo list(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                           @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                           HttpSession session){
        User user = (User) session.getAttribute(ApiConst.USER_DATA);
        return shippingService.list(user.getId(), pageNum, pageSize);
    }
}
