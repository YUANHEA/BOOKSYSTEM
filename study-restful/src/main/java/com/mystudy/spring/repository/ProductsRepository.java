package com.mystudy.spring.repository;

import com.mystudy.spring.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Book, Integer>{

    Page<Book> findAll(Specification<Book> specification, Pageable pageable);

}
