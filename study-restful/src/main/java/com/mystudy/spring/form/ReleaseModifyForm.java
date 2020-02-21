package com.mystudy.spring.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ReleaseModifyForm {

    @NotNull
    private Integer bid;

    private Integer categoryId;

    private String name;

    //    图片地址
    private MultipartFile cover;

    private BigDecimal price;

    //    简介
    private String intro;

    private String auther;

    //    出版社
    private String press;

    //    发行时间
    private Date pubdate;

    //    数量
    private Integer stock;

    //销售状态
    private Integer status;

    //
    private Integer special;

    //
    private Integer news;

    //促销
    private Integer sale;
}
