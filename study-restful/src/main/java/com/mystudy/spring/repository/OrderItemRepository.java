package com.mystudy.spring.repository;

import com.mystudy.spring.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface OrderItemRepository extends JpaRepository<OrderItem,Integer> {
    List<OrderItem> findByOrderNoIn(Set<Long> orderNoset);
}
