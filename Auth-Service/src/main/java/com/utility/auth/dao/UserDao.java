package com.utility.auth.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utility.auth.entity.UserCredentials;

@Repository
public interface UserDao extends JpaRepository<UserCredentials, Long> {

    Optional<UserCredentials> findByEmailId(String email);
}
