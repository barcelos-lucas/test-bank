package com.banco.case_contas.domain.repository;

import com.banco.case_contas.domain.model.Account;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface AccountRepository {
    Account save(Account account);
    Optional<Account> findById(UUID id);
    boolean existsById(UUID id);
    List<Account> findAll();

}
