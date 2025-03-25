package com.banco.case_contas.presentation.web;

import com.banco.case_contas.application.usecases.CreditUseCase;
import com.banco.case_contas.application.usecases.DebitUseCase;
import com.banco.case_contas.application.usecases.TransferUseCase;
import com.banco.case_contas.domain.model.Account;
import com.banco.case_contas.domain.TransactionDTO;
import com.banco.case_contas.domain.model.User;
import com.banco.case_contas.domain.repository.AccountRepository;
import com.banco.case_contas.domain.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/accounts")
public class AccountController {

    private final AccountRepository accountRepository;
    private final CreditUseCase creditUseCase;
    private final DebitUseCase debitUseCase;
    private final TransferUseCase transferUseCase;
    private final UserRepository userRepository;

    public AccountController(AccountRepository accountRepository,
                             CreditUseCase creditUseCase,
                             DebitUseCase debitUseCase,
                             TransferUseCase transferUseCase,
                             UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.creditUseCase = creditUseCase;
        this.debitUseCase = debitUseCase;
        this.transferUseCase = transferUseCase;
        this.userRepository = userRepository;
    }

    // Página com lista de contas
    @GetMapping
    public String listAccounts(Model model) {
        List<Account> accounts = accountRepository.findAll();
        model.addAttribute("accounts", accounts);
        return "accounts";
    }

    // Página de movimentações
    @GetMapping("/transactions")
    public String showTransactionsPage(Model model) {
        model.addAttribute("transaction", new TransactionDTO());
        return "transactions";
    }

    // Operação de crédito
    @PostMapping("/credit")
    public String credit(@ModelAttribute TransactionDTO dto) {
        creditUseCase.execute(UUID.fromString(dto.getAccountId()), dto.getAmount());
        return "redirect:/accounts";
    }

    // Operação de débito
    @PostMapping("/debit")
    public String debit(@ModelAttribute TransactionDTO dto) {
        debitUseCase.execute(UUID.fromString(dto.getAccountId()), dto.getAmount());
        return "redirect:/accounts";
    }
    // Operação de transferencia
    @PostMapping("/transfer")
    public String transfer(@ModelAttribute TransactionDTO dto) {
        transferUseCase.execute(
                UUID.fromString(dto.getFromAccountId()),
                UUID.fromString(dto.getToAccountId()),
                dto.getAmount()
        );
        return "redirect:/accounts";
    }
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return "redirect:/login";
        }

        User user = userOpt.get();
        List<Account> accounts = accountRepository.findByOwner(user.getId());

        model.addAttribute("accounts", accounts);
        return "dashboard";
    }

}

