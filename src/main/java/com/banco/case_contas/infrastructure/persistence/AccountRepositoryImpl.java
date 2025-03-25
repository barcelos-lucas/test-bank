package com.banco.case_contas.infrastructure.persistence;

import com.banco.case_contas.domain.model.Account;
import com.banco.case_contas.domain.repository.AccountRepository;
import com.banco.case_contas.domain.repository.AccountRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Repository
public class AccountRepositoryImpl implements AccountRepository, AccountRepositoryCustom {
    private  final AccountJpaRepository jpaRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public AccountRepositoryImpl (AccountJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Account save(Account account){
        return jpaRepository.save(account);
    }

    @Override
    public Optional<Account> findById(UUID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return jpaRepository.existsById(id);
    }
    @Override
    public List<Account> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public List<Account> findByOwner(String ownerId) {
        return jpaRepository.findByOwner(ownerId);
    }

    @Override
    public Optional<Account> findByIdForUpdate(UUID id) {
        Account account = entityManager
                .createQuery("SELECT a FROM Account a WHERE a.id = :id", Account.class)
                .setParameter("id", id)
                .setLockMode(LockModeType.PESSIMISTIC_WRITE) // pessimista
                .getSingleResult();
        return Optional.ofNullable(account);
    }


}
