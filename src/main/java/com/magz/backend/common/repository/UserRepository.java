package com.magz.backend.common.repository;

import com.magz.backend.common.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
    public interface UserRepository extends CrudRepository<User, Long> {
        Optional<User> findByEmail(String email); // Use 'email' if that is the correct field for login

    }

