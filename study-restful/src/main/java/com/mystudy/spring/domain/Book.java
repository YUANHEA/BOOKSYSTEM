package com.mystudy.spring.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "book_all")
public class Book {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer bookId;

    @Column(name = "category_id")
    private Integer categoryId;

    private String name;

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

    //    前台正常时间戳
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createtime")
    private Date createTime;

    //    前台正常时间戳
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updatetime")
    private Date updateTime;

}
