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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return "redirect:/login";
        }

        User user = userOpt.get();
        List<Account> accounts = accountRepository.findByOwner(user.getId());

        model.addAttribute("accounts", accounts);
        return "accounts";
    }

    // Página de movimentações
    @GetMapping("/transactions")
    public String showTransactionsPage(Model model) {
        model.addAttribute("transaction", new TransactionDTO());
        return "transactions";
    }
    // operação de credito
    @PostMapping("/credit")
    public String credit(@ModelAttribute TransactionDTO dto, RedirectAttributes redirectAttributes) {
        try {
            creditUseCase.execute(UUID.fromString(dto.getAccountId()), dto.getAmount());
            redirectAttributes.addFlashAttribute("successMessage", "Crédito realizado com sucesso!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/accounts/transactions";
    }
    // operação de debito
    @PostMapping("/debit")
    public String debit(@ModelAttribute TransactionDTO dto, RedirectAttributes redirectAttributes) {
        try {
            debitUseCase.execute(UUID.fromString(dto.getAccountId()), dto.getAmount());
            redirectAttributes.addFlashAttribute("successMessage", "Débito realizado com sucesso!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/accounts/transactions";
    }


    // Operação de transferencia
    @PostMapping("/transfer")
    public String transfer(@ModelAttribute TransactionDTO dto, Model model) {
        try {
            transferUseCase.execute(
                    UUID.fromString(dto.getFromAccountId()),
                    UUID.fromString(dto.getToAccountId()),
                    dto.getAmount()
            );
            return "redirect:/accounts/dashboard";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("transaction", dto);
            model.addAttribute("errorMessage", ex.getMessage());
            return "transactions";
        }
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

    // formulário para criar conta
    @GetMapping("/createAccount")
    public String showCreateAccountForm(Model model) {
        model.addAttribute("account", new Account());
        return "createAccount";
    }

    // processa o envio do formulário
    @PostMapping("/createAccount")
    public String createAccount(@ModelAttribute Account account) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return "redirect:/login";
        }

        User user = userOpt.get();
        account.setOwner(user.getId());
        account.setBalance(BigDecimal.ZERO); // ponto zero do saldo
        accountRepository.save(account);

        return "redirect:/accounts/dashboard";
    }


}

