package com.banco.case_contas.application.usecases;

import com.banco.case_contas.domain.model.Account;
import com.banco.case_contas.domain.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class TransferUseCase {

    private final AccountRepository accountRepository;

    public TransferUseCase(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void execute(UUID fromAccountId, UUID toAccountId, BigDecimal amount) {
        if (fromAccountId.equals(toAccountId)) {
            throw new IllegalArgumentException("A conta de origem e destino devem ser diferentes");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor de transferência deve ser acima de zero");
        }

        Account from = accountRepository.findByIdForUpdate(fromAccountId) //altera para update para o bloqueio pessimista
                .orElseThrow(() -> new IllegalArgumentException("Conta de origem não encontrada"));

        Account to = accountRepository.findByIdForUpdate(toAccountId) //altera para update para o bloqueio pessimista
                .orElseThrow(() -> new IllegalArgumentException("Conta de destino não encontrada"));

        if (from.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para transferência");
        }

        from.setBalance(from.getBalance().subtract(amount));
        to.setBalance(to.getBalance().add(amount));

        accountRepository.save(from);
        accountRepository.save(to);
    }
}
