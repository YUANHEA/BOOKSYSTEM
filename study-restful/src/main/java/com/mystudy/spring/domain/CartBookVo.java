package com.mystudy.spring.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class CartBookVo {


    private int bookId;

    /**
     * 购买的数量
     */
    private Integer quantity;

    private String bookName;

    //    图片地址
    private String cover;

    private BigDecimal price;

    //    简介
    private String intro;

    private String auther;

    //    出版社
    private String press;

    //    发行时间
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date pubdate;

    //    总数量
    private Integer stock;

    //
    private Integer special;

    //
    private Integer news;

    //
    private Integer sale;

    //销售状态
    private Integer status;


    /**
     * 等于 quantity * productPrice
     */
    @Column(name = "bookTotalPrice")
    private BigDecimal bookTotalPrice;

    private Integer bookStock;

    /**
     * 商品是否选中
     */
    @Column(name = "bookSelected")
    private Boolean bookSelected;

    public CartBookVo() {
    }

    public CartBookVo(int bookId, Integer quantity, String bookName, String cover, BigDecimal price, String intro, String auther, String press, Date pubdate, Integer stock, Integer special, Integer news, Integer sale, Integer status, BigDecimal bookTotalPrice, Integer bookStock, Boolean bookSelected) {
        this.bookId = bookId;
        this.quantity = quantity;
        this.bookName = bookName;
        this.cover = cover;
        this.price = price;
        this.intro = intro;
        this.auther = auther;
        this.press = press;
        this.pubdate = pubdate;
        this.stock = stock;
        this.special = special;
        this.news = news;
        this.sale = sale;
        this.status = status;
        this.bookTotalPrice = bookTotalPrice;
        this.bookStock = bookStock;
        this.bookSelected = bookSelected;
    }
}
