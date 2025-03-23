package com.banco.case_contas.presentation.web;

import com.banco.case_contas.application.usecases.CreateAccountUseCase;
import com.banco.case_contas.domain.model.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")

public class AccountController {
    private final CreateAccountUseCase createAccountUseCase;

    public AccountController(final CreateAccountUseCase createAccountUseCase) {
        this.createAccountUseCase = createAccountUseCase;
    }

    @PostMapping
    public ResponseEntity<Account> createAccount (@RequestBody createAccountRequest createAccountRequest) {
        Account created = createAccountUseCase.execute(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}
