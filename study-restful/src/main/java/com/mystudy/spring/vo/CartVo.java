package com.mystudy.spring.vo;

import com.mystudy.spring.domain.CartBookVo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartVo {

    private List<CartBookVo> cartBookVoList;

    private Boolean selectedAll;

    private BigDecimal cartTotalPrice;

    private Integer cartTotalQuantity;
}
