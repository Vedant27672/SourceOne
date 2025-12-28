package com.SourceOne.repository;

import com.SourceOne.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.OptionalInt;

public interface UserRepository extends JpaRepository<User, String> {

    Optional <User> findByUsername(String username);
}
