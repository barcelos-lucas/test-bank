package com.banco.case_contas.presentation.web;

import com.banco.case_contas.application.usecase.RegisterUserUseCase;
import com.banco.case_contas.domain.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;

    public AuthController(RegisterUserUseCase registerUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
    }

    //login
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    //registro (GET)
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    //formulário de registro (POST)
    @PostMapping("/register")
    public String processRegister(@ModelAttribute User user) {
        registerUserUseCase.execute(user);
        return "redirect:/login";
    }

    // Página inicial depois do login
    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
