package com.banco.case_contas.application.usecase;

import com.banco.case_contas.application.usecases.DebitUseCase;
import com.banco.case_contas.domain.model.Account;
import com.banco.case_contas.domain.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DebitUseCaseTest {

    private AccountRepository repository;
    private DebitUseCase useCase;
    private UUID accountId;
    private Account account;

    @BeforeEach
    void setUp() {
        repository = mock(AccountRepository.class);
        useCase = new DebitUseCase(repository);
        accountId = UUID.randomUUID();

        account = new Account("Maria", "POUPANCA");
        account.setId(accountId);
        account.setBalance(BigDecimal.valueOf(200));
    }


    @Test
    void shouldDebitAmountSuccessfully() {
        when(repository.findById(accountId)).thenReturn(Optional.of(account));

        useCase.execute(accountId, BigDecimal.valueOf(40));

        assertEquals(BigDecimal.valueOf(60), account.getBalance());
        verify(repository).save(account);
    }

    @Test
    void shouldThrowIfInsufficientBalance() {
        when(repository.findById(accountId)).thenReturn(Optional.of(account));

        assertThrows(IllegalArgumentException.class, () -> {
            useCase.execute(accountId, BigDecimal.valueOf(200));
        });
    }

    @Test
    void shouldThrowIfAccountNotFound() {
        when(repository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            useCase.execute(accountId, BigDecimal.valueOf(40));
        });
    }
}
