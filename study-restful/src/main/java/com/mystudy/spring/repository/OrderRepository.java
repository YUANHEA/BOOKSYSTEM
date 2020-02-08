package com.mystudy.spring.repository;

import com.mystudy.spring.domain.Order;
import com.mystudy.spring.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface OrderRepository extends JpaRepository<Order,Integer> {

    List<Order> findByUserId(Integer uid);

    Order findByOrderNo(Long orderNo);
}
