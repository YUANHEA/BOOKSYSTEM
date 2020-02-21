package com.mystudy.spring.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderItemVo {

    private Long orderNo;

    private Integer bookId;

    private String bookName;

    private String bookImage;

    private BigDecimal currentUnitPrice;

    private Integer quantity;

    private BigDecimal totalPrice;

    private Date createTime;


    private Integer sellerId;

    private String sellerName;
}
