package com.mystudy.spring.domain;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "book_all_user")
public class ReleaseBook {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    private Integer uid;

    @NotNull
    private Integer bid;
}
