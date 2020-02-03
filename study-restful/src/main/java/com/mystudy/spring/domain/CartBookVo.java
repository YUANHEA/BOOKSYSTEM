package com.mystudy.spring.domain;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "book_volist")
public class CartBookVo {


    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;

    /**
     * 购买的数量
     */
    private Integer quantity;

    private String bookName;

    private String bookSubtitle;

    private String bookMainImage;

    private BigDecimal bookPrice;

    private Integer bookStatus;

    /**
     * 等于 quantity * productPrice
     */
    private BigDecimal bookTotalPrice;

    private Integer bookStock;

    /**
     * 商品是否选中
     */
    private Boolean bookSelected;

    public CartBookVo(Integer bookId, Integer quantity, String bookName, String bookSubtitle, String bookMainImage, BigDecimal bookPrice, Integer bookStatus, BigDecimal bookTotalPrice, Integer bookStock, Boolean bookSelected) {
        this.bookId = bookId;
        this.quantity = quantity;
        this.bookName = bookName;
        this.bookSubtitle = bookSubtitle;
        this.bookMainImage = bookMainImage;
        this.bookPrice = bookPrice;
        this.bookStatus = bookStatus;
        this.bookTotalPrice = bookTotalPrice;
        this.bookStock = bookStock;
        this.bookSelected = bookSelected;
    }
}
