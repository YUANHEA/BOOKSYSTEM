package com.mystudy.spring.repository;

import com.mystudy.spring.domain.Book;
import com.mystudy.spring.domain.CartBookVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


public interface CartRepository  extends JpaRepository<CartBookVo, Integer>{


    public CartBookVo findByBookId(Integer bookId);
}
