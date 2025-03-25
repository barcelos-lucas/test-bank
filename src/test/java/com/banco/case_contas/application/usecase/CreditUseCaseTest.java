package com.banco.case_contas.application.usecase;

import com.banco.case_contas.application.usecases.CreditUseCase;
import com.banco.case_contas.domain.model.Account;
import com.banco.case_contas.domain.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreditUseCaseTest {

    private AccountRepository repository;
    private CreditUseCase useCase;
    private UUID accountId;
    private Account account;

    @BeforeEach
    void setUp() {
        repository = mock(AccountRepository.class);
        useCase = new CreditUseCase(repository);
        accountId = UUID.randomUUID();

        account = new Account("John", "CORRENTE");
        account.setId(accountId);
        account.setBalance(BigDecimal.valueOf(100));
    }


    @Test
    void shouldCreditAmountSuccessfully() {
        when(repository.findById(accountId)).thenReturn(Optional.of(account));

        useCase.execute(accountId, BigDecimal.valueOf(50));

        assertEquals(BigDecimal.valueOf(150), account.getBalance());
        verify(repository).save(account);
    }

    @Test
    void shouldThrowIfAccountNotFound() {
        when(repository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            useCase.execute(accountId, BigDecimal.valueOf(50));
        });
    }
}
