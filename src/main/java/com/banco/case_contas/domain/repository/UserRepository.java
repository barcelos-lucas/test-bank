package com.banco.case_contas.domain.repository;

import com.banco.case_contas.domain.model.User;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findByUsername(String username);
    User save(User user);
}
