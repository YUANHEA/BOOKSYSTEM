package com.mystudy.spring.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.sql.Date;

@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "book_user")
public class User {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    private String password;


    @Column(length = 255)
    private String email;


    @Column(length = 255)
    private String phone;

    private int role;

    /**
     * 更新时间
     */
//    @LastModifiedDate
//    @Column(name = "updateTime", nullable = false)
//    private Long updateTime;
//
//    /**
//     * 创建时间
//     */
//    @CreatedDate
////    @Column(name="createTime",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",insertable = false,updatable = false)
////    @Generated(GenerationTime.INSERT)
//        @Column(name="createTime",insertable = false,updatable = false)
//    private Long createTime;


    //    前台正常时间戳
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updatetime")
    private java.util.Date updateTime;

    //    前台正常时间戳
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createtime")
    private java.util.Date createTime;

}