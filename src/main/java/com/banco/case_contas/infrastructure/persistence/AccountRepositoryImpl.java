package com.banco.case_contas.infrastructure.persistence;

import com.banco.case_contas.domain.model.Account;
import com.banco.case_contas.domain.repository.AccountRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public class AccountRepositoryImpl implements AccountRepository {
    private  final AccountJpaRepository jpaRepository;

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
}
