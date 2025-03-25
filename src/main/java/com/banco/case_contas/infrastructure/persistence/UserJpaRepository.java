package com.banco.case_contas.infrastructure.persistence;

import com.banco.case_contas.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface UserJpaRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
}