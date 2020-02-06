package com.mystudy.spring.form;

import com.mystudy.spring.vo.ResponseVo;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@Data
public class OrderCreateForm {

    @NotNull
    private Integer shippingId;
}

