package com.zerutis.codingchallenge.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bank_account_statement")
public class BankAccountStatement {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "operation_datetime")
    private LocalDateTime operationDateTime;

    @Column(name = "beneficiary")
    private String beneficiary;

    @Column(name = "comment")
    private String comment;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "currency")
    private String currency;

    public BankAccountStatement(Long id, String accountNumber, LocalDateTime operationDateTime, String beneficiary, String comment, BigDecimal amount, String currency) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.operationDateTime = operationDateTime;
        this.beneficiary = beneficiary;
        this.comment = comment;
        this.amount = amount;
        this.currency = currency;
    }

    public BankAccountStatement() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public LocalDateTime getOperationDateTime() {
        return operationDateTime;
    }

    public void setOperationDateTime(LocalDateTime operationDateTime) {
        this.operationDateTime = operationDateTime;
    }

    public String getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(String beneficiary) {
        this.beneficiary = beneficiary;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "BankAccountStatement{" +
                "accountNumber='" + accountNumber + '\'' +
                ", operationDateTime=" + operationDateTime +
                ", beneficiary='" + beneficiary + '\'' +
                ", comment='" + comment + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }
}
