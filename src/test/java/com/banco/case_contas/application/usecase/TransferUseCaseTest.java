package com.banco.case_contas.application.usecase;

import com.banco.case_contas.application.usecases.TransferUseCase;
import com.banco.case_contas.domain.model.Account;
import com.banco.case_contas.domain.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransferUseCaseTest {

    private AccountRepository repository;
    private TransferUseCase useCase;
    private UUID sourceId;
    private UUID targetId;
    private Account source;
    private Account target;

    @BeforeEach
    void setUp() {
        repository = mock(AccountRepository.class);
        useCase = new TransferUseCase(repository);
        sourceId = UUID.randomUUID();
        targetId = UUID.randomUUID();

        source = new Account(sourceId, "Source", BigDecimal.valueOf(100), "CORRENTE");
        target = new Account(targetId, "Target", BigDecimal.valueOf(50), "CORRENTE");
    }

    @Test
    void shouldTransferSuccessfully() {
        when(repository.findById(sourceId)).thenReturn(Optional.of(source));
        when(repository.findById(targetId)).thenReturn(Optional.of(target));

        useCase.execute(sourceId, targetId, BigDecimal.valueOf(30));

        assertEquals(BigDecimal.valueOf(70), source.getBalance());
        assertEquals(BigDecimal.valueOf(80), target.getBalance());

        verify(repository).save(source);
        verify(repository).save(target);
    }

    @Test
    void shouldThrowIfSameAccount() {
        assertThrows(IllegalArgumentException.class, () -> {
            useCase.execute(sourceId, sourceId, BigDecimal.valueOf(30));
        });
    }

    @Test
    void shouldThrowIfInsufficientBalance() {
        source.setBalance(BigDecimal.valueOf(20));
        when(repository.findById(sourceId)).thenReturn(Optional.of(source));
        when(repository.findById(targetId)).thenReturn(Optional.of(target));

        assertThrows(IllegalArgumentException.class, () -> {
            useCase.execute(sourceId, targetId, BigDecimal.valueOf(50));
        });
    }
}
