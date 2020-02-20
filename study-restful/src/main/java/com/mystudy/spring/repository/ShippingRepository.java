package com.mystudy.spring.repository;

import com.mystudy.spring.domain.Shipping;
import jdk.nashorn.internal.runtime.SharedPropertyMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Transactional
public interface ShippingRepository extends JpaRepository<Shipping,Integer> {
    int deleteByIdAndUserId(Integer id,Integer uid);

    Shipping findByIdAndUserId(Integer id,Integer uid);

//    @Modifying
//    @Query(value = "update user set name = :name where id = :id",nativeQuery = true)
//    void updateNameById(@Param("id") Long id, @Param("name") String name);

    public List<Shipping> findByUserId(Integer uid);

    List<Shipping> findByIdIn(Set<Integer> shippingIdSet);
}
