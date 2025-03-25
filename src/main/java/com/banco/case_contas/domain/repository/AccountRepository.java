package com.banco.case_contas.domain.repository;

import com.banco.case_contas.domain.model.Account;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface AccountRepository {


    Account save(Account account);
    Optional<Account> findById(UUID id);
    boolean existsById(UUID id);
    List<Account> findAll();
    List<Account> findByOwner(String ownerId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM Account a WHERE a.id = :id")
    Optional<Account> findByIdForUpdate(UUID id);

}
