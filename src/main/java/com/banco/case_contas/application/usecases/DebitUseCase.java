package com.banco.case_contas.application.usecases;

import com.banco.case_contas.domain.model.Account;
import com.banco.case_contas.domain.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class DebitUseCase {

    private final AccountRepository accountRepository;

    @Autowired
    public DebitUseCase(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void execute(UUID accountId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor deve ser maior que zero");
        }

        Account account = accountRepository.findByIdForUpdate(accountId) //altera para update para o bloqueio pessimista
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));

        if (account.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para débito");
        }

        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
    }

}
