package com.banco.case_contas.application.usecases;

public class CreateAccountRequest {
    private String owner;
    private String accountType;

        public CreateAccountRequest(String owner, String accountType) {
            this.owner = owner;
            this.accountType = accountType;
        }

        public String getOwner() {
            return owner;
        }

        public String accountType() {
            return accountType;
    }
}


