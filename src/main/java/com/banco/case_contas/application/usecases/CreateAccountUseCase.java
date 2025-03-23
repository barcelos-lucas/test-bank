package com.banco.case_contas.application.usecases;

import com.banco.case_contas.domain.model.Account;
import com.banco.case_contas.domain.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateAccountUseCase {

    private final AccountRepository accountRepository;

    public CreateAccountUseCase(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account execute (CreateAccountRequest request) {
        Account account = new Account(request.getOwner(), request.accountType());
        return accountRepository.save(account);
    }
}
