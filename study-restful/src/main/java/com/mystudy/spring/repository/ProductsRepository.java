package com.mystudy.spring.repository;

import com.mystudy.spring.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ProductsRepository extends JpaRepository<Book, Integer>{

    Page<Book> findAll(Specification<Book> specification, Pageable pageable);

    Page<Book> findByBookIdInOrderByCreateTimeDesc(Set<Integer> bidSet ,Pageable pageable);
}
