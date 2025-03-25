package com.banco.case_contas.domain.repository;

import com.banco.case_contas.domain.model.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {
    Account save(Account account);
    Optional<Account> findById(UUID id);
    boolean existsById(UUID id);
}
