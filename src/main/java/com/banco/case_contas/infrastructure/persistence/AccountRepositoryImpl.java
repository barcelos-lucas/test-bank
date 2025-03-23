package com.banco.case_contas.infrastructure.persistence;

import com.banco.case_contas.domain.repository.AccountRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
    public Optional<Account> findByID (UUID id) {
        return jpaRepository.findByID(id);
    }

    @Override
    public boolean existsById (UUID id){
        return jpaRepository.existsByID(id);
    }
}
