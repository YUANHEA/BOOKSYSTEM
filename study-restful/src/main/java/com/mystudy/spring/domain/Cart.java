package com.mystudy.spring.domain;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class Cart implements Comparable<Cart> {

    private Integer bookId;

    private Integer quantity;

    private Boolean bookSelected;

    private String createTime;

    public Cart(Integer bookId, Integer quantity, Boolean bookSelected) {
        this.bookId = bookId;
        this.quantity = quantity;
        this.bookSelected = bookSelected;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.createTime = df.format(new Date());
    }



    public Cart() {
    }

    @Override
    public int compareTo(Cart o) {
        return this.createTime.compareTo(o.getCreateTime());
    }
}
