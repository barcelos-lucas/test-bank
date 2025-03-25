package com.banco.case_contas.domain;

import java.math.BigDecimal;

public class TransactionDTO {
    private String accountId;
    private String fromAccountId;
    private String toAccountId;
    private BigDecimal amount;

    // Getters e Setters
    public String getAccountId() {
        return accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    public String getFromAccountId() {
        return fromAccountId;
    }
    public void setFromAccountId(String fromAccountId) {
        this.fromAccountId = fromAccountId;
    }
    public String getToAccountId() {
        return toAccountId;
    }
    public void setToAccountId(String toAccountId) {
        this.toAccountId = toAccountId;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
