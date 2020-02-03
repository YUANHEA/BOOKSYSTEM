package com.mystudy.spring.domain;

import lombok.Data;

@Data
public class Cart {

    private Integer bookId;

    private Integer quantity;

    private Boolean bookSelected;

    public Cart(Integer bookId, Integer quantity, Boolean bookSelected) {
        this.bookId = bookId;
        this.quantity = quantity;
        this.bookSelected = bookSelected;
    }
}
