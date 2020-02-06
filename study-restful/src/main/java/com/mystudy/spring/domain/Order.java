package com.mystudy.spring.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "book_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_no")
    private Long orderNo;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "shipping_id")
    private Integer shippingId;

    /*
    * 实际付款金额
    * */
    @Column(name = "payment")
    private BigDecimal payment;

    /*
     * 支付类型
     * */
    @Column(name = "payment_type")
    private Integer paymentType;

    /*
     * 运费
     * */
    @Column(name = "postage")
    private Integer postage;

    /*
    * 订单状态:
    * 0-已取消-10-未付款，20-已付款，40-已发货，
    * 50-交易成功，60-交易关闭
    * */
    @Column(name = "status")
    private Integer status;

    /*
    * 支付时间
    * */
    @Column(name = "payment_time")
    private Date paymentTime;

    /*
    * 发货时间
    * */
    @Column(name = "send_time")
    private Date sendTime;

    /*
     * 交易完成时间
     * */
    @Column(name = "end_time")
    private Date endTime;

    /*
    * 交易关闭时间  
    * */
    @Column(name = "close_time")
    private Date closeTime;

    //    前台正常时间戳
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time")
    private java.util.Date updateTime;

    //    前台正常时间戳
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private java.util.Date createTime;
}
