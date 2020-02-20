package com.mystudy.spring.repository;

import com.mystudy.spring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{
    public Object findByUsernameAndPassword(String username,String password);

    public Object findByUsername(String username);

    @Query(nativeQuery = true, value = "select * from book_user where id = (select uid from book_all_user where bid = ?1)")
    public User findUserByBookId(Integer bid);


}
