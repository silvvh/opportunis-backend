package com.bcc.projeto.repositories;

import com.bcc.projeto.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByEmailEquals(String email);
}
