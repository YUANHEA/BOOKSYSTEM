package com.mystudy.spring.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ReleaseAddForm {

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
