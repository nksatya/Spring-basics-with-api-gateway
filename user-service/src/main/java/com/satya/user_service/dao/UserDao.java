package com.satya.user_service.dao;

import com.satya.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User,Integer> {

    @Query(value = "SELECT u.password FROM user u WHERE u.email =:emailId",nativeQuery = true)
    public String findUserByEmailId(String emailId);
}
