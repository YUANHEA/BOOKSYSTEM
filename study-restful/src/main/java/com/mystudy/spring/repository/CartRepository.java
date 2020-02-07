package com.mystudy.spring.repository;

import com.mystudy.spring.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


public interface CartRepository  extends JpaRepository<Book, Integer>{


    public Book findByBookId(Integer bookId);

    List<Book> findByBookIdIn(Set<Integer> bookIds) ;
}
