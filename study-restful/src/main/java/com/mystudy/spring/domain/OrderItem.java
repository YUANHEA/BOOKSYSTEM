package com.mystudy.spring.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderItem {

    private Integer id;

    private Integer userId;

    private Long orderNo;

    private Integer productId;

    private String bookName;

    private String bookImage;

    private BigDecimal currentUnitPrice;

    private Integer quantity;

    private BigDecimal totalPrice;

    private Date createTime;

    private Date updateTime;
}
