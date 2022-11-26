package com.zerutis.codingchallenge.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class BankAccountStatement {

    @Id
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
}
