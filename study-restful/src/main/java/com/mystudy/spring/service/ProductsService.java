package com.mystudy.spring.service;

import com.mystudy.spring.domain.Book;
import com.mystudy.spring.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductsService {
    @Autowired
    private ProductsRepository productsRepository;

    public Page<Book> getAllBooks(Integer categoryId, String keyword,
                                  Integer pageNum, Integer pageSize,
                                  String orderBy_field, String orderBy_rule){
        Pageable pageable = new PageRequest(pageNum-1, pageSize, Sort.Direction.fromString(orderBy_rule), orderBy_field);
        Specification<Book> specification = new Specification<Book>() {
            @Override
            public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();

                if (!StringUtils.isEmpty(keyword)) {
                    list.add(cb.like(root.get("name").as(String.class), "%" + keyword + "%"));
                }
                if (!StringUtils.isEmpty(categoryId)) {
                    list.add(cb.equal(root.get("categoryId").as(Integer.class), categoryId));
                }
                return cb.and(list.toArray(new Predicate[list.size()]));
            }
        };
        Page<Book> page = productsRepository.findAll(specification, pageable);
        return page;
    }

    public Book getBookById(Integer id){
        Book book = productsRepository.findOne(id);
        return book;
    }
}
