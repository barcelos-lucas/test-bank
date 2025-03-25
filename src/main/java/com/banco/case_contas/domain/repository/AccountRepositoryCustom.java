package com.banco.case_contas.domain.repository;

import com.banco.case_contas.domain.model.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepositoryCustom {
    Optional<Account> findByIdForUpdate(UUID id);
}
