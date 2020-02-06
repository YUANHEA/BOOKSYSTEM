package com.mystudy.spring.api;

import com.github.pagehelper.PageInfo;
import com.mystudy.spring.ApiConst.ApiConst;
import com.mystudy.spring.domain.OrderVo;
import com.mystudy.spring.domain.User;
import com.mystudy.spring.form.OrderCreateForm;
import com.mystudy.spring.service.OrderService;
import com.mystudy.spring.vo.ResponseVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation(value="创建订单", notes="创建订单")
    @PostMapping("/orders")
    public ResponseVo create(@Valid @RequestBody OrderCreateForm form,
                             HttpSession session) {
        User user = (User) session.getAttribute(ApiConst.USER_DATA);
        return orderService.create(user.getId(), form.getShippingId());
    }

    @ApiOperation(value="订单List", notes="订单状态:" +
            "0-已取消-10-未付款，20-已付款，40-已发货，50-交易成功，60-交易关闭")
    @GetMapping("/orders")
    public ResponseVo<PageInfo> list(){
        return null;
    }

    @ApiOperation(value="订单详情", notes="订单详情")
    @GetMapping("/orders/{orderNo}")
    public ResponseVo<OrderVo> detail(){
        return null;
    }

    @ApiOperation(value="取消订单", notes="取消订单")
    @PutMapping("/orders/{orderNo}")
    public ResponseVo cancel(){
        return null;
    }
}
