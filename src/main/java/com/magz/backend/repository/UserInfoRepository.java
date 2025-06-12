package com.magz.backend.repository;

import com.magz.backend.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
    public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
        Optional<UserInfo> findByEmail(String email); // Use 'email' if that is the correct field for login
        Optional<UserInfo> findByName(String name);

    // Add a method to search by name or email
    @Query("SELECT u FROM UserInfo u WHERE u.email = :username OR u.name = :username")
    Optional<UserInfo> findByEmailOrName(@Param("username") String username);
    }

