package com.mystudy.spring.repository;

import com.mystudy.spring.domain.ReleaseBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ReleaseBookRepository extends JpaRepository<ReleaseBook,Integer> {

    ReleaseBook findByUidAndBid(Integer uid, Integer bid);

    List<ReleaseBook> findByUid(Integer uids);
}
