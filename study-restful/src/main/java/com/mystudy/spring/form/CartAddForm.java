package com.mystudy.spring.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CartAddForm {
    @NotNull
    private Integer bookId;

    private Boolean selected = true;
}
