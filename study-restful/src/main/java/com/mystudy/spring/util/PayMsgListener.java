package com.mystudy.spring.util;

import com.google.gson.Gson;
import com.mystudy.spring.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "payNotify")
@Slf4j
public class PayMsgListener {
    @Autowired
    private OrderService orderService;

    @RabbitHandler
    public void process(String msg){
        log.info("【接收到消息】 => {}", msg);
        Map<String, String> payInfo = new Gson().fromJson(msg, Map.class);
        if(payInfo.get("status").equals("SUCCESS")){
            orderService.paid(Long.parseLong(payInfo.get("orderId")));
        }
    }
}
