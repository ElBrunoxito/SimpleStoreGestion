package com.yobrunox.webbasic.repository;

import com.yobrunox.webbasic.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query(value = "SELECT U FROM UserEntity U WHERE U.username= :username")
    Optional<UserEntity> findByUser (@Param("username") String username);


}
