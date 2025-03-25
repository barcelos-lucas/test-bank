package com.banco.case_contas.infrastructure.persistence;

import com.banco.case_contas.domain.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AccountJpaRepository extends JpaRepository<Account, UUID> {
    List<Account> findByOwner(String owner);
}

