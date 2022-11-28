package com.zerutis.codingchallenge.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bank_account_statement")
@ToString
@NoArgsConstructor
@Getter
@Setter
public class BankAccountStatement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(name = "operation_datetime", nullable = false)
    private LocalDateTime operationDateTime;

    @Column(name = "beneficiary", nullable = false)
    private String beneficiary;

    @Column(name = "comment")
    private String comment;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "currency", nullable = false)
    private String currency;

    public BankAccountStatement(
            String accountNumber,
            LocalDateTime operationDateTime,
            String beneficiary, String comment,
            BigDecimal amount,
            String currency
    ) {
        this.accountNumber = accountNumber;
        this.operationDateTime = operationDateTime;
        this.beneficiary = beneficiary;
        this.comment = comment;
        this.amount = amount;
        this.currency = currency;
    }
}
