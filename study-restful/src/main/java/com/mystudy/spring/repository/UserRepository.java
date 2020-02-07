package com.mystudy.spring.repository;

import com.mystudy.spring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{
    public Object findByUsernameAndPassword(String username,String password);

    public Object findByUsername(String username);


}
