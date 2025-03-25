package com.banco.case_contas.domain.model;

import jakarta.persistence.*;
import jakarta.persistence.Version;


import java.math.BigDecimal;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String owner;
    private BigDecimal balance;
    private String accountType;

    @Version
    private Long version; //otimista

    public Account(String owner, String accountType) {
        this.owner = owner;
        this.accountType = accountType;
        this.balance = BigDecimal.ZERO;
    }



    public void credit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    public void debit(BigDecimal amount) {
        this.balance = this.balance.subtract(amount);
    }
}

